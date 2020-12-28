package io.swagger.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsersTest {

    private User user;

    @Before
    public void setup() {
        user = new User();
    }

    @Test
    public void createUsersShouldNotBeNull() {
        assertNotNull(user);
    }

    @Test
    public void settingEmptyFirstNameShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> user.setFirstname(""));
        assertEquals("FirstName cannot be empty", exception.getMessage());
    }

    @Test
    public void settingEmptyLastNameShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> user.setLastname(""));
        assertEquals("LastName cannot be empty", exception.getMessage());
    }

    @Test
    public void settingEmptyUsernameShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> user.setUsername(""));
        assertEquals("UserName cannot be empty", exception.getMessage());
    }

    @Test
    public void settingEmptyPasswordShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> user.setPassword(""));
        assertEquals("Password cannot be empty", exception.getMessage());
    }
}
