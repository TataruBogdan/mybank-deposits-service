package bank.deposit.dto;

import lombok.Data;

@Data
public class DebitAccountDepositDTO {

    private Double debitAmount;

    public DebitAccountDepositDTO(){
        super();
    }

    public void debitAccountDeposit(Double debitAmount) {
        this.debitAmount = debitAmount;
    }
}
