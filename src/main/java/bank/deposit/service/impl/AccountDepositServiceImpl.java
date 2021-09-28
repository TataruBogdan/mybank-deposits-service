package bank.deposit.service.impl;


import bank.deposit.dao.DepositRepository;
import bank.deposit.dto.AccountDepositDTO;
import bank.deposit.model.AccountDeposit;
import bank.deposit.service.AccountDepositMapper;
import bank.deposit.service.AccountDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static bank.deposit.model.Status.ACTIVE;

@RequiredArgsConstructor
@Service
public class AccountDepositServiceImpl implements AccountDepositService {

    @Autowired
    private final DepositRepository depositRepository;

    @Autowired
    private final AccountDepositMapper accountDepositMapper;

    //private final long MILLS_IN_YEAR = 1000L * 60 * 60 * 24 * 365;
    //long millisInYear = ChronoUnit.MILLIS.between(LocalDate.now(), LocalDate.now().plusYears(1));

    @Override
    public List<AccountDepositDTO> getAll() {

         return depositRepository.findAll()
                 .stream()
                 .map(accountDeposit -> accountDepositMapper.accountDepositToDTO(accountDeposit))
                 .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountDepositDTO> getByIban(String iban) {

        AccountDeposit accountDepositByIban = depositRepository.getById(iban);
        AccountDepositDTO accountDepositDTO = accountDepositMapper.accountDepositToDTO(accountDepositByIban);

        return Optional.of(accountDepositDTO);
    }

    @Override
    public List<AccountDepositDTO> getByIndividual(int individualId) {
        return depositRepository.findByIndividualId(individualId)
                .stream()
                .map(accountDeposit -> accountDepositMapper.accountDepositToDTO(accountDeposit))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccountDepositByIban(String iban) {

        AccountDeposit accountDeposit = depositRepository.getById(iban);
        depositRepository.delete(accountDeposit);


    }

    @Override
    public AccountDepositDTO createIndividualAccountDeposit(int individualId) {

        AccountDeposit accountDeposit = new AccountDeposit();
        accountDeposit.setIban(UUID.randomUUID().toString());
        accountDeposit.setDepositAmount(0.00);
        accountDeposit.setBalance(0.00);
        accountDeposit.setIndividualId(individualId);
        // set maturity date after one year
        //(LocalDate.now().plusYears(1)
        accountDeposit.setMaturityDate(new Date());
        // set interest rate 1 an = 0.01
        accountDeposit.setInterestRate(0.00);
        accountDeposit.setSelfCapitalization(true);
        //is ok? maturity Iban is the same as the account Deposit
        accountDeposit.setMaturityIban(accountDeposit.getIban());
        accountDeposit.setStartDate(new Date());
        accountDeposit.setAccountDepositStatus(ACTIVE);
        AccountDeposit savedAccount = depositRepository.save(accountDeposit);
        AccountDepositDTO accountDepositDTO = accountDepositMapper.accountDepositToDTO(savedAccount);
        return accountDepositDTO;
    }

    @Override
    public AccountDepositDTO creditBalanceAccountDeposit(String iban, Double balance) {

        AccountDeposit accountDeposit = depositRepository.getById(iban);

        double currentBalance = accountDeposit.getBalance();
        accountDeposit.setBalance(currentBalance + balance);
        AccountDeposit savedAccountDeposit = depositRepository.save(accountDeposit);

        return accountDepositMapper.accountDepositToDTO(savedAccountDeposit);
    }

    @Override
    public AccountDepositDTO debitBalanceAccountDeposit(String iban, Double balance) {
        AccountDeposit accountDeposit = depositRepository.getById(iban);

        double currentBalance = accountDeposit.getBalance();
        accountDeposit.setBalance(currentBalance - balance);
        depositRepository.save(accountDeposit);

        return accountDepositMapper.accountDepositToDTO(accountDeposit);
    }
}
