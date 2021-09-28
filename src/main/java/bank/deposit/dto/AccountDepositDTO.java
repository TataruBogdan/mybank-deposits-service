package bank.deposit.dto;


import bank.deposit.model.Status;
import banking.commons.dto.IndividualDTO;
import lombok.Data;

import java.util.Date;

@Data
public class AccountDepositDTO {


    private String iban;

    private double depositAmount;
    private double balance;
    private int individualId;
    private Date maturityDate;
    private double interestRate;
    private boolean selfCapitalization;
    private String maturityIban;
    private Date startDate;
    private Status accountDepositStatus;

    private IndividualDTO individual;
}
