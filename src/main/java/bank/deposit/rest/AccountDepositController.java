package bank.deposit.rest;

import bank.deposit.dto.AccountDepositDTO;
import bank.deposit.dto.CreditAccountDepositDTO;
import bank.deposit.dto.DebitAccountDepositDTO;
import bank.deposit.service.AccountDepositService;
import banking.commons.dto.IndividualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AccountDepositController {

    @Autowired
    private AccountDepositService accountDepositService;

    @Autowired
    private IndividualRestClient individualRestClient;


    @GetMapping("/accounts-deposit")
    public List<AccountDepositDTO> retrieveAllAccountsDeposit(){
        return accountDepositService.getAll();
    }

    @GetMapping("/accounts-deposit/{iban}")
    public ResponseEntity<AccountDepositDTO> retrieveAllAccountsDepositByIban(@PathVariable String iban){

        Optional<AccountDepositDTO> accountDepositDTOByIban = accountDepositService.getByIban(iban);

        if (accountDepositDTOByIban.isPresent()) {
            IndividualDTO individualById = individualRestClient.getIndividualById(accountDepositDTOByIban.get().getIndividualId());
            accountDepositDTOByIban.get().setIndividual(individualById);
            return ResponseEntity.ok(accountDepositDTOByIban.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/delete/account-deposit/{iban}")
    public void deleteAccountDeposit(@PathVariable String iban) {
        accountDepositService.deleteAccountDepositByIban(iban);
    }

    @PostMapping(value = "create-account-deposit/individual/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDepositDTO> createAccountDepositForIndividual(@PathVariable("id") int individualId){

        AccountDepositDTO individualAccountDeposit = accountDepositService.createIndividualAccountDeposit(individualId);
        IndividualDTO individualById = individualRestClient.getIndividualById(individualId);
        individualAccountDeposit.setIndividual(individualById);

        return ResponseEntity.ok(individualAccountDeposit);
    }


    @PatchMapping(path = "/account-deposit/credit/{iban}")
    public ResponseEntity<AccountDepositDTO> creditAccountDeposit(@PathVariable("iban") String iban, @RequestBody CreditAccountDepositDTO amount){

        Optional<AccountDepositDTO> accountDepositServiceByIban = accountDepositService.getByIban(iban);
        IndividualDTO individualById = individualRestClient.getIndividualById(accountDepositServiceByIban.get().getIndividualId());

        AccountDepositDTO accountDepositDTO = accountDepositService.creditBalanceAccountDeposit(iban, amount.getCreditAmount());
        accountDepositDTO.setIndividual(individualById);
        return ResponseEntity.ok(accountDepositDTO);

    }

    @PatchMapping(path = "/account-deposit/debit/{iban}")
    public ResponseEntity<AccountDepositDTO> debitAccountDeposit(@PathVariable("iban") String iban,@RequestBody DebitAccountDepositDTO amount){

        Optional<AccountDepositDTO> accountDepositServiceByIban = accountDepositService.getByIban(iban);
        IndividualDTO individualById = individualRestClient.getIndividualById(accountDepositServiceByIban.get().getIndividualId());
        //can a deposit bank account be negative ???
        AccountDepositDTO accountDepositDTO = accountDepositService.debitBalanceAccountDeposit(iban, amount.getDebitAmount());
        accountDepositDTO.setIndividual(individualById);
        return ResponseEntity.ok(accountDepositDTO);


    }



}
