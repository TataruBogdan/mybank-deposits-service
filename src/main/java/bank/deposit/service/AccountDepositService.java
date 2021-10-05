package bank.deposit.service;


import banking.commons.dto.AccountDepositDTO;

import java.util.List;
import java.util.Optional;

public interface AccountDepositService {

    // ??? este ok sa punem aici un camp - ??? de ce ???
    double interestRate = 0.05;

    List<AccountDepositDTO> getAll();
    Optional<AccountDepositDTO> getByIban(String iban);
    List<AccountDepositDTO> getAllByIndividual(int individualId);
    void deleteAccountDepositByIban(String iban);
    AccountDepositDTO createIndividualAccountDeposit(int individualId, int months, int amount);
    //AccountDepositDTO creditAccountDeposit(String iban, Double amount);



}
