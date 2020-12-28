package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountApiControllerTest {

    @Autowired
    private MockMvc mvc;

    private Account account;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        account = new Account("NL99INHO9999599999", Account.TypeofaccountEnum.DEPOSIT, 100005L);
        headers.setBasicAuth("test", "test");
    }

    @Test
    public void postingAccountShouldReturnCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        this.mvc
                .perform(
                        post("/account")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(account)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAccountByIdShouldReturnOk() throws Exception {
        this.mvc.perform(get("/account/NL99INHO9999999999").headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAccountFromUserShouldReturnOk() throws Exception {
        this.mvc.perform(get("/account/listaccount/100002").headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    void toggleAccountActivityShouldReturnOk() throws Exception {
        this.mvc.perform(put("/account/activity/NL99INHO9999999999").headers(headers))
                .andExpect(status().isOk());
    }
}
