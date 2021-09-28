package bank.deposit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "account_deposit")
public class AccountDeposit {

    @Id
    @Column(name = "iban")
    private String iban;

    private double depositAmount;
    private double balance;
    private int individualId;
    private int maturityMonths;
    private double interestRate;
    private boolean selfCapitalization;
    private String maturityIban;
    private Date startDate;

    @Enumerated(EnumType.STRING)
    private Status accountDepositStatus;

}
