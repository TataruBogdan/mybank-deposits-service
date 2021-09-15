package bank.service.deposit.dao;

import bank.service.deposit.model.AccountDeposit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepositRepository extends JpaRepository<AccountDeposit, String> {
}
