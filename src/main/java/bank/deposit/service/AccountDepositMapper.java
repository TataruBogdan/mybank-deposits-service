package bank.deposit.service;

import banking.commons.dto.AccountDepositDTO;
import bank.deposit.model.AccountDeposit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountDepositMapper {

    AccountDepositDTO accountDepositToDTO(AccountDeposit accountDeposit);

    AccountDeposit toAccountDeposit(AccountDepositDTO accountDepositDTO);

    List<AccountDepositDTO> listAccountDepositDTO(List<AccountDeposit> accountDepositList);


}
