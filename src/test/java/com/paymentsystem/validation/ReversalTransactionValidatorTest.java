package com.paymentsystem.validation;

import com.paymentsystem.model.AuthorizeTransaction;
import com.paymentsystem.model.ReversalTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReversalTransactionValidatorTest {

    private ReversalTransactionValidator validator;

    @BeforeEach
    public void setup() {
        validator = new ReversalTransactionValidator();
    }

    @Test
    public void validateAmountNotNullRejectsTransaction() {
        ReversalTransaction transaction = new ReversalTransaction();
        transaction.setAmount(new BigDecimal("10.0"));

        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");

        validator.validate(transaction, errors);

        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("amount"));
        assertEquals("Transaction from type REVERSAL should not have amount set", errors.getFieldError("amount").getDefaultMessage());
    }

    @Test
    public void validateValidTransactionDoesNotRejectTransaction() {
        ReversalTransaction transaction = new ReversalTransaction();
        AuthorizeTransaction referencedTransaction = new AuthorizeTransaction();
        referencedTransaction.setStatus("approved");
        transaction.setAuthorizeTransaction(referencedTransaction);

        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");

        validator.validate(transaction, errors);

        assertFalse(errors.hasErrors());
        assertNull(transaction.getStatus());
    }
}
