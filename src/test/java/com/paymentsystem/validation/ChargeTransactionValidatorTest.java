package com.paymentsystem.validation;

import com.paymentsystem.model.ChargeTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ChargeTransactionValidatorTest {

    private ChargeTransactionValidator validator;

    @Mock
    private Errors errors;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        validator = new ChargeTransactionValidator();
    }

    @Test
    public void testDoesNotSupports() {
        boolean supports = validator.supports(Object.class);
        assertFalse(supports);
    }

    @Test
    public void testValidAmount() {
        ChargeTransaction chargeTransaction = new ChargeTransaction();
        chargeTransaction.setAmount(new BigDecimal("10.00"));

        validator.validate(chargeTransaction, errors);

        verify(errors, never()).rejectValue(eq("amount"), anyString(), any());
    }

    @Test
    public void testInvalidAmount() {
        ChargeTransaction chargeTransaction = new ChargeTransaction();
        chargeTransaction.setAmount(new BigDecimal("-10.00"));

        validator.validate(chargeTransaction, errors);

        verify(errors, times(1)).rejectValue(eq("amount"), eq("transaction.amount.invalid"), any());
    }
}