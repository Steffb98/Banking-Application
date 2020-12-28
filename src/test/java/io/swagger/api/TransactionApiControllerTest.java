package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Transaction;
import io.swagger.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    private Transaction transaction;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        transaction = new Transaction("NL99INHO9999999999", "NL09INHO0999999999", new BigDecimal(10), 100005L, 100005L);
        headers.setBasicAuth("test", "test");
    }

    @Test
    public void creatingTransactionShouldReturnCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper(); // Junit werkt niet samen LocalDateTime daarom krijgen we een error

        this.mvc
                .perform(
                        post("/transaction")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getTransactionByIdShouldReturnOk() throws Exception {
        this.mvc.perform(get("/transaction/1").headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void getTransactionByIbanShouldReturnOk() throws Exception {
        this.mvc.perform(get("/transaction/account/" + transaction.getSender()).headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void getTransactionFromUserShouldReturnOk() throws Exception {
        this.mvc.perform(get("/transaction/user/100001").headers(headers))
                .andExpect(status().isOk());
    }

}
