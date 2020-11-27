package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private UserRepository userRepository;

    public MyApplicationRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        List<User> users = Arrays.asList(
                new User("Kim", "Gelder", "663143@student.inholland.nl", "wachtwoord", true, User.TypeofuserEnum.EMPLOYEE),
                new User("Cheyen", "Alberts", "568524@student.inholland.nl", "wachtwoord123", true, User.TypeofuserEnum.EMPLOYEE)
        );

        users.forEach(userRepository::save);

        userRepository.findAll().forEach(System.out::println);

    }
}
