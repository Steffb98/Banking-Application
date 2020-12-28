package io.swagger.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction();
    }

    @Test
    public void createTransactionShouldNotBeNull() {
        assertNotNull(transaction);
    }

    @Test
    public void settingAmountBelowZeroShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> transaction.setAmount(new BigDecimal(-1)));
        assertEquals("Amount cannot be below zero", exception.getMessage());
    }

    @Test
    public void settingEmptySenderShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> transaction.setSender(""));
        assertEquals("Sender cannot be empty", exception.getMessage());
    }

    @Test
    public void settingEmptyReceiverShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> transaction.setReceiver(""));
        assertEquals("Receiver cannot be empty", exception.getMessage());
    }

    @Test
    public void settingEmptyPerformingUserShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> transaction.setPerforminguser(null));
        assertEquals("Performing user cannot be empty", exception.getMessage());
    }

    @Test
    public void settingEmptyReceivingUserShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> transaction.setReceivinguser(null));
        assertEquals("Receiving user cannot be empty", exception.getMessage());
    }

    @Test
    public void settingEmptyDateShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> transaction.setDate(null));
        assertEquals("Date cannot be empty", exception.getMessage());
    }

}