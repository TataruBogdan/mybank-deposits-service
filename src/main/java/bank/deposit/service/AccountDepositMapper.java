package bank.deposit.service;

import bank.deposit.dto.AccountDepositDTO;
import bank.deposit.model.AccountDeposit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountDepositMapper {

    AccountDepositDTO accountDepositToDTO(AccountDeposit accountDeposit);

    AccountDeposit toAccountDeposit(AccountDepositDTO accountDepositDTO);

    List<AccountDepositDTO> ListAccountDepositDTO(List<AccountDeposit> accountDepositList);


}
