package io.swagger.service;

import io.swagger.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AccountService accountService;

    private Account account;

    @BeforeEach
    public void setUp(){
        account = new Account(accountService.generateIban(), Account.TypeofaccountEnum.DEPOSIT, 100004L);
    }
}
