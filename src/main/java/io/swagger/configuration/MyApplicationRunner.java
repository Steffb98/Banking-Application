package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public MyApplicationRunner(UserRepository userRepository, AccountRepository accountRepository, AccountService accountService, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        List<User> users = Arrays.asList(
                new User("Kim", "Gelder", "663143@student.inholland.nl", "wachtwoord"),
                new User("Cheyen", "Alberts", "568524@student.inholland.nl", "wachtwoord123")
        );

        users.forEach(userRepository::save);

        userRepository.findAll().forEach(System.out::println);

        List<Account> accounts = Arrays.asList(
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.SAVING, users.get(0).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.DEPOSIT, users.get(0).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.SAVING, users.get(1).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.DEPOSIT, users.get(1).getuserId())
        );

        accounts.forEach(accountRepository::save);

        accountRepository.findAll().forEach(System.out::println);

        List<Transaction> transactions = Arrays.asList(
                new Transaction(accounts.get(0).getIban(), accounts.get(1).getIban(), new BigDecimal(100), users.get(0).getuserId() )
        );

        transactions.forEach(transactionRepository::save);

        transactionRepository.findAll().forEach(System.out::println);
    }
}
