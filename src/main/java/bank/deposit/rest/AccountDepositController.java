package bank.deposit.rest;

import banking.commons.dto.AccountDepositDTO;
import bank.deposit.dto.ArgsDTO;
import bank.deposit.service.AccountDepositService;
import banking.commons.dto.IndividualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/accounts-deposit")
public class AccountDepositController {

    @Autowired
    private AccountDepositService accountDepositService;

    @Autowired
    private IndividualRestClient individualRestClient;


    @GetMapping("/")
    public ResponseEntity<List<AccountDepositDTO>> retrieveAllAccountsDeposit(){
        List<AccountDepositDTO> allAccountsDeposit = accountDepositService.getAll();
        if(allAccountsDeposit.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<AccountDepositDTO> newAccountsDeposits = new LinkedList<>();
        for (AccountDepositDTO accountDTO : allAccountsDeposit) {
            IndividualDTO individualId = individualRestClient.getIndividualById(accountDTO.getIndividualId());
            accountDTO.setIndividual(individualId);
            newAccountsDeposits.add(accountDTO);
        }
        return ResponseEntity.ok(newAccountsDeposits);
    }

    @GetMapping("/{iban}")
    public ResponseEntity<AccountDepositDTO> retrieveAccountDepositByIban(@PathVariable("iban") String iban){

        Optional<AccountDepositDTO> accountDepositDTOByIban = accountDepositService.getByIban(iban);

        if (accountDepositDTOByIban.isPresent()) {
            IndividualDTO individualById = individualRestClient.getIndividualById(accountDepositDTOByIban.get().getIndividualId());
            accountDepositDTOByIban.get().setIndividual(individualById);
            return ResponseEntity.ok(accountDepositDTOByIban.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(value = "/individual/{individualId}")
    public ResponseEntity<List<AccountDepositDTO>> retrieveAccountDepositIndividualId(@PathVariable("individualId") int individualId){

        IndividualDTO individualById = individualRestClient.getIndividualById(individualId);
        List<AccountDepositDTO> accountDepositServiceByIndividual = accountDepositService.getAllByIndividual(individualId);
        if (accountDepositServiceByIndividual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for (AccountDepositDTO accountDeposit:accountDepositServiceByIndividual) {
            accountDeposit.setIndividual(individualById);
        }
        return ResponseEntity.ok(accountDepositServiceByIndividual);

    }

    @DeleteMapping("/delete/{iban}")
    public void deleteAccountDeposit(@PathVariable String iban) {
        accountDepositService.deleteAccountDepositByIban(iban);
    }

    //TODO - DE ADAUGAT MONTHS  si deposit amount-
    @PostMapping(value = "/create/individual/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDepositDTO> createAccountDepositForIndividual(@PathVariable("id") int individualId, @RequestBody ArgsDTO args ){
        AccountDepositDTO individualAccountDeposit = accountDepositService.createIndividualAccountDeposit(individualId, args.getMonths(), args.getAmount());
        IndividualDTO individualById = individualRestClient.getIndividualById(individualId);
        individualAccountDeposit.setIndividual(individualById);
        return ResponseEntity.ok(individualAccountDeposit);
    }

//    @PatchMapping("credit/{iban}")
//    public ResponseEntity<AccountDepositDTO> creditAccountDepositForIban (@PathVariable("iban") String iban ,@RequestBody CreditAmountDepositDTO amount){
//
//        Optional<AccountDepositDTO> accountDepositDTO = accountDepositService.getByIban(iban);
//        IndividualDTO individualById = individualRestClient.getIndividualById(accountDepositDTO.get().getIndividualId());
//        AccountDepositDTO creditAccountDepositDTO = accountDepositService.creditAccountDeposit(iban, amount.getAmount());
//        creditAccountDepositDTO.setIndividual(individualById);
//
//        return ResponseEntity.ok(creditAccountDepositDTO);
//    }




}
