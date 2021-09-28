package bank.deposit.service;


import bank.deposit.dto.AccountDepositDTO;

import java.util.List;
import java.util.Optional;

public interface AccountDepositService {

    List<AccountDepositDTO> getAll();
    Optional<AccountDepositDTO> getByIban(String iban);
    List<AccountDepositDTO> getByIndividual(int individualId);
    void deleteAccountDepositByIban(String iban);
    AccountDepositDTO createIndividualAccountDeposit(int individualId);
    AccountDepositDTO creditBalanceAccountDeposit(String iban, Double balance);
    AccountDepositDTO debitBalanceAccountDeposit(String iban, Double balance);


}
