package bank.deposit.dto;


import bank.deposit.model.Status;
import banking.commons.dto.IndividualDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AccountDepositDTO {

    @NotNull
    private String iban;
    @NotNull
    private double depositAmount;
    private double balance;
    private int individualId;

    private double interestRate;
    private boolean selfCapitalization;
    private String maturityIban;
    private Date startDate;
    private Status accountDepositStatus;

    private IndividualDTO individual;
    @NotNull
    private int maturityMonths;
}
