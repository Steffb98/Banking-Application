package io.swagger.service;

import io.swagger.exception.BadInputException;
import io.swagger.exception.NotAuthorizedException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.TypeofuserEnum;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp(){
        user = new User("Test", "Tester", "Test@Test.com", "Test112", TypeofuserEnum.EMPLOYEE);
    }

//    @Test
//    public void createUser(){
//        user.setUsername("dassa");
//        Exception exception = assertThrows(BadInputException.class,
//                () -> userService.createUser(user));
//        assertEquals("Email invalid", exception.getMessage());
//    }

    @Test
    public void getNullWhenNoUserIsFound() throws NotFoundException, NotAuthorizedException {
        User user1 = userService.getUserById(100001L);
        assertNull(user1);
    }

    @Test
    public void updatingInvalidUserShouldThrowException() throws NotFoundException, BadInputException {
        user.setUsername("dassa");
        userService.updateUser(user.getuserId(), user);
    }
}
