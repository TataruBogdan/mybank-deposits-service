package bank.service.deposit;


import bank.service.deposit.dao.DepositRepository;
import bank.service.deposit.model.AccountDeposit;
import bank.service.deposit.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class DepositApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DepositRepository depositRepository;

    public static void main(String[] args) {
        SpringApplication.run(DepositApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Insert deposit -> {}", depositRepository.save(
                new AccountDeposit("ABC123",100.00,0.00, 10001, new Date(), 0.4,
                        true, "BCD123", new Date(), Status.ACTIVE)));

        logger.info("Deposits -> {}", depositRepository.findAll());

    }
}
