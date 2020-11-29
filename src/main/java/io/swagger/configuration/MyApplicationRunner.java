package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
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

    public MyApplicationRunner(UserRepository userRepository, AccountRepository accountRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountRepository= accountRepository;
        this.accountService = accountService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        List<User> users = Arrays.asList(
                new User("Kim", "Gelder", "663143@student.inholland.nl", "wachtwoord", true, User.TypeofuserEnum.EMPLOYEE),
                new User("Cheyen", "Alberts", "568524@student.inholland.nl", "wachtwoord123", true, User.TypeofuserEnum.EMPLOYEE)
        );

        users.forEach(userRepository::save);

        userRepository.findAll().forEach(System.out::println);

        List<Account> accounts = Arrays.asList(
                new Account(accountService.generateIban(), BigDecimal.valueOf(0.00), Account.TypeofaccountEnum.SAVING, BigDecimal.valueOf(-10.00), true, 100001L, 5L, new BigDecimal(20000), 5L),
                new Account(accountService.generateIban(), BigDecimal.valueOf(0.00), Account.TypeofaccountEnum.DEPOSIT, BigDecimal.valueOf(-10.00), true, 100001L, 5L, new BigDecimal(20000), 5L)
        );

        accounts.forEach(accountRepository::save);

        accountRepository.findAll().forEach(System.out::println);
    }
}
