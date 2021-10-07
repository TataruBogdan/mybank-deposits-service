package bank.deposit.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


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

    private double interestRate;
    private boolean selfCapitalization;
    private String maturityIban;
    private LocalDateTime startDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_deposit_status")
    private CurrentStatus status;
    private int maturityMonths;

}
