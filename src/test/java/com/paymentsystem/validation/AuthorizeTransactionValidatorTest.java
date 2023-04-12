package com.paymentsystem.validation;

import com.paymentsystem.model.AuthorizeTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthorizeTransactionValidatorTest {

    private AuthorizeTransactionValidator validator;

    @Mock
    private AuthorizeTransaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        validator = new AuthorizeTransactionValidator();
    }

    @Test
    void validateWithValidAmount() {
        when(transaction.getAmount()).thenReturn(BigDecimal.valueOf(1));
        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateWithInvalidAmount() {
        when(transaction.getAmount()).thenReturn(BigDecimal.ZERO);
        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);
        assertTrue(errors.hasErrors());
        assertEquals("transaction.amount.invalid", errors.getFieldError("amount").getCode());
    }
}