package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.TypeofuserEnum;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                new User("Kim", "Gelder", "kim", new BCryptPasswordEncoder().encode("test"), TypeofuserEnum.CUSTOMER),
                new User("Cheyen", "Alberts", "cheyen", new BCryptPasswordEncoder().encode("test"), TypeofuserEnum.CUSTOMER),
                new User("Sam", "Kuik", "sam", new BCryptPasswordEncoder().encode("test"), TypeofuserEnum.CUSTOMER),
                new User("admin", "emplyee", "test", new BCryptPasswordEncoder().encode("test"), TypeofuserEnum.EMPLOYEE),
                new User("Unit", "Testing", "jUnit", new BCryptPasswordEncoder().encode("test"), TypeofuserEnum.EMPLOYEE)// User For Testing
        );

        users.forEach(userRepository::save);

        userRepository.findAll().forEach(System.out::println);

        List<Account> accounts = Arrays.asList(
                new Account("NL01INHO0000000001", Account.TypeofaccountEnum.BANK, 100000L),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.SAVING, users.get(0).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.DEPOSIT, users.get(0).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.SAVING, users.get(1).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.DEPOSIT, users.get(1).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.SAVING, users.get(2).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.DEPOSIT, users.get(2).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.SAVING, users.get(3).getuserId()),
                new Account(accountService.generateIban(), Account.TypeofaccountEnum.DEPOSIT, users.get(3).getuserId()),
                new Account("NL99INHO9999999999", Account.TypeofaccountEnum.SAVING, users.get(4).getuserId()), // Account for testing
                new Account("NL09INHO0999999999", Account.TypeofaccountEnum.DEPOSIT, users.get(4).getuserId()) // Account for testing
        );

        accounts.forEach(accountRepository::save);

        accountRepository.findAll().forEach(System.out::println);

        List<Transaction> transactions = Arrays.asList(
                new Transaction(accounts.get(2).getIban(), accounts.get(4).getIban(), new BigDecimal(100), users.get(0).getuserId(), users.get(1).getuserId()),
                new Transaction(accounts.get(4).getIban(), accounts.get(6).getIban(), new BigDecimal(100), users.get(1).getuserId(), users.get(2).getuserId()),
                new Transaction(accounts.get(1).getIban(), accounts.get(2).getIban(), new BigDecimal(100), users.get(0).getuserId(), users.get(0).getuserId()),
                //Deposit to bank from User Sam
                new Transaction(accounts.get(0).getIban(), accounts.get(6).getIban(), new BigDecimal(500), 100000L, users.get(2).getuserId()),
                //Withdrawal from bank to user Kim
                new Transaction(accounts.get(2).getIban(), accounts.get(0).getIban(), new BigDecimal(5), users.get(0).getuserId(), 100000L)
        );

        transactions.forEach(transactionRepository::save);

        transactionRepository.findAll().forEach(System.out::println);
    }
}
