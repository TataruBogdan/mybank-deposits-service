package bank.deposit.dto;


import lombok.Data;

@Data
public class CreditAccountDepositDTO {

    private Double creditAmount;

    public CreditAccountDepositDTO(){
        super();
    }

    public void creditAccountDeposit(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

}
