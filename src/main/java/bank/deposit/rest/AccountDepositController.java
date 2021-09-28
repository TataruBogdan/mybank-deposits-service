package bank.deposit.rest;

import bank.deposit.dto.AccountDepositDTO;
import bank.deposit.service.AccountDepositService;
import banking.commons.dto.IndividualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//TODO campul maturityDate - Date actuala + 1 an ??
// TODO Get all accounts - individual NULL ??? - Select per Iban - merge

@RestController
@RequestMapping("/accounts-deposit")
public class AccountDepositController {

    @Autowired
    private AccountDepositService accountDepositService;

    @Autowired
    private IndividualRestClient individualRestClient;


    @GetMapping
    public List<AccountDepositDTO> retrieveAllAccountsDeposit(){
        return accountDepositService.getAll();
    }

    @GetMapping("/{iban}")
    public ResponseEntity<AccountDepositDTO> retrieveAccountDepositByIban(@PathVariable String iban){

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
    public ResponseEntity<AccountDepositDTO> createAccountDepositForIndividual(@PathVariable("id") int individualId){

        AccountDepositDTO individualAccountDeposit = accountDepositService.createIndividualAccountDeposit(individualId);
        IndividualDTO individualById = individualRestClient.getIndividualById(individualId);
        individualAccountDeposit.setIndividual(individualById);

        return ResponseEntity.ok(individualAccountDeposit);
    }




}
