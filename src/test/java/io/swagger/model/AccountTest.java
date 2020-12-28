package io.swagger.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {
    private Account account;

    @Before
    public void setup() {
        account = new Account();
    }

    @Test
    public void createUsersShouldNotBeNull() {
        assertNotNull(account);
    }

    @Test
    public void settingBalanceBelowAbsolutLimitShouldThrowException() {
        account.setAbsolutlimit(new BigDecimal(-10.00));
        account.setBalance(new BigDecimal(10));
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.setBalance(new BigDecimal(-100)));
        assertEquals("Balance cannot be below the absolut limit", exception.getMessage());
    }

    @Test
    public void settingAbsolutLimitAboveZeroShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.setAbsolutlimit(new BigDecimal(1)));
        assertEquals("Absolut limit cannot be above zero", exception.getMessage());
    }

    @Test
    public void settingDayLimitBelowZeroShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.setDaylimit(-1L));
        assertEquals("DayLimit cannot be below zero", exception.getMessage());
    }

    @Test
    public void settingTransactionLimitBelowZeroShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.setTransactionlimit(new BigDecimal(-1)));
        assertEquals("TransactionLimit cannot be below zero", exception.getMessage());
    }

    @Test
    public void settingNumberOfTransactionBelowZeroShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.setNumberoftransactions(-1L));
        assertEquals("NumberOfTransaction cannot be below zero", exception.getMessage());
    }


}
