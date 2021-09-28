package bank.deposit.service.impl;


import bank.deposit.dao.DepositRepository;
import bank.deposit.dto.AccountDepositDTO;
import bank.deposit.model.AccountDeposit;
import bank.deposit.service.AccountDepositMapper;
import bank.deposit.service.AccountDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<AccountDepositDTO> getAllByIndividual(int individualId) {
        return depositRepository.findByIndividualId(individualId)
                .stream()
                .map(accountDeposit -> accountDepositMapper.accountDepositToDTO(accountDeposit))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccountDepositByIban(String iban) {

        depositRepository.deleteById(iban);
    }

    @Override
    public AccountDepositDTO createIndividualAccountDeposit(int individualId) {

        AccountDeposit accountDeposit = new AccountDeposit();
        accountDeposit.setIban(UUID.randomUUID().toString());
        accountDeposit.setDepositAmount(0.00);
        accountDeposit.setBalance(0.00);
        accountDeposit.setIndividualId(individualId);
        // set interest rate 1 an = 0.01
        //TODO interestRate este o constanta in interfata AccountDeposit service - value 0.05
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

    // Periodic Rate = Interest Rate * Time Period (12% *12 months)
//    public double setMaturityAmount(String iban){
//
//        AccountDeposit depositRepositoryByIban = depositRepository.getById(iban);
//        double depositAmount = depositRepositoryByIban.getDepositAmount();
//
//
//
//    }

    @Override
    public AccountDepositDTO creditBalanceAccountDeposit(String iban, Double amount) {


//        double currentBalance = accountDeposit.getBalance();
//        //TODO DATE IN DAYS/MONTHS/YEAR ???
//        Date date = new Date();
//        long currentDate = date.getTime();
//        long startDate = accountDeposit.getStartDate().getTime();
//        long maturityDate = accountDeposit.getMaturityDate().getTime();
//        double interestRate = accountDeposit.getInterestRate();
        // if account is opened the same DAY/MONTH/YEAR set balance amount - the amount that was deposed
        //
//        if(startDate != currentDate && currentDate != maturityDate) {
//            accountDeposit.setBalance(currentBalance + interestRate);
//        } else {
//            accountDeposit.setBalance(accountDeposit.getDepositAmount());
//        }
        AccountDeposit accountDeposit = depositRepository.getById(iban);
        double currentBalance = accountDeposit.getBalance();

        accountDeposit.setBalance(currentBalance + amount);
        AccountDeposit savedAccountDeposit = depositRepository.save(accountDeposit);

        return accountDepositMapper.accountDepositToDTO(savedAccountDeposit);
    }

    @Override
    public AccountDepositDTO debitBalanceAccountDeposit(String iban, Double amount) {

        AccountDeposit accountDeposit = depositRepository.getById(iban);

        double currentBalance = accountDeposit.getBalance();
        accountDeposit.setBalance(currentBalance - amount);
        depositRepository.save(accountDeposit);

        return accountDepositMapper.accountDepositToDTO(accountDeposit);
    }

    @Override
    public AccountDepositDTO updateDepositAmount(String iban, Double deposit) {

        AccountDeposit depositRepositoryById = depositRepository.getById(iban);
        double depositAmount = depositRepositoryById.getDepositAmount();
        depositRepositoryById.setDepositAmount(depositAmount + deposit);

        return accountDepositMapper.accountDepositToDTO(depositRepositoryById);

    }
}
