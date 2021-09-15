package bank.service.deposit.dto;


import bank.service.deposit.model.Status;
import lombok.Data;

import java.util.Date;

@Data
public class DepositDTO {

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
}
