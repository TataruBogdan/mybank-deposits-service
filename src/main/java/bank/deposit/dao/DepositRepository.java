package bank.deposit.dao;


import bank.deposit.model.AccountDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//without JPARepository -
@Repository
public interface DepositRepository extends JpaRepository<AccountDeposit,String> {

    List<AccountDeposit> findByIndividualId(int individualId);


}
