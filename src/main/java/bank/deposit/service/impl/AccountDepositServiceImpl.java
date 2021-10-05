package bank.deposit.service.impl;


import bank.deposit.dao.DepositRepository;
import banking.commons.dto.AccountDepositDTO;
import bank.deposit.model.AccountDeposit;
import bank.deposit.service.AccountDepositMapper;
import bank.deposit.service.AccountDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static bank.deposit.idGen.IdGenerator.idGen;
import static bank.deposit.model.CurrentStatus.ACTIVE;


@RequiredArgsConstructor
@Service
public class AccountDepositServiceImpl implements AccountDepositService {

    @Autowired
    private final DepositRepository depositRepository;

    @Autowired
    private final AccountDepositMapper accountDepositMapper;


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
    public AccountDepositDTO createIndividualAccountDeposit(int individualId, int months, int amount) {

        AccountDeposit accountDeposit = new AccountDeposit();
        accountDeposit.setIban(idGen("DEP"));
        accountDeposit.setDepositAmount(amount);
        accountDeposit.setBalance(amount);
        accountDeposit.setIndividualId(individualId);
        //TODO interestRate este o constanta in interfata AccountDepositService - value 0.05
        accountDeposit.setInterestRate(interestRate);
        accountDeposit.setSelfCapitalization(true);
        accountDeposit.setMaturityIban(accountDeposit.getIban());
        accountDeposit.setStartDate(new Date());
        accountDeposit.setStatus(ACTIVE);
        accountDeposit.setMaturityMonths(months);
        AccountDeposit savedAccount = depositRepository.save(accountDeposit);
        AccountDepositDTO accountDepositDTO = accountDepositMapper.accountDepositToDTO(savedAccount);
        return accountDepositDTO;
    }


//    @Override
//    public AccountDepositDTO creditAccountDeposit(String iban, Double amount) {
//
//        AccountDeposit accountDeposit = depositRepository.getById(iban);
//        double currentBalance = accountDeposit.getBalance();
//        accountDeposit.setBalance(currentBalance + amount);
//        AccountDeposit savedAccountDeposit = depositRepository.save(accountDeposit);
//
//        return accountDepositMapper.accountDepositToDTO(savedAccountDeposit);
//    }

}
