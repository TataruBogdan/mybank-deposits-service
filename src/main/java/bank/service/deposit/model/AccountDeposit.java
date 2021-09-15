package bank.service.deposit.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountDeposit {

    @Id
    private String iban;

    private double depositAmount;
    private double balance;
    private int individualId;
    private Date maturityDate;
    private double interestRate;
    private boolean selfCapitalization;
    private String maturityIban;
    private Date startDate;

    @Enumerated(EnumType.STRING)
    private Status accountDepositStatus;

}
