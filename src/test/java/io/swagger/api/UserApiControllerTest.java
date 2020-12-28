package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.TypeofuserEnum;
import io.swagger.model.User;
import io.swagger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User user;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        user = new User("Test", "Tester", "Test", "Test112", TypeofuserEnum.EMPLOYEE);
        headers.setBasicAuth("test", "test");
    }

    @Test
    public void CreatingUserShouldReturnCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        this.mvc
                .perform(
                        post("/user")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void UpdatingUserShouldReturnOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        this.mvc
                .perform(
                        put("/user/100005?password=update")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void ToggleUserShouldReturnOk() throws Exception {
        this.mvc.perform(put("/user/activity/100001").headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void GettingUserShouldReturnOk() throws Exception {
        this.mvc.perform(get("/user/search/100001").headers(headers))
                .andExpect(status().isOk());
    }
}
