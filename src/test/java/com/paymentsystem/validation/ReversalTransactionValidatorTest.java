package com.paymentsystem.validation;

import com.paymentsystem.model.AuthorizeTransaction;
import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.ReversalTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

public class ReversalTransactionValidatorTest {

    private ReversalTransactionValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new ReversalTransactionValidator();
    }

    @Test
    public void validateReferencedTransactionIsNotAuthorize() {
        ChargeTransaction referencedTransaction = new ChargeTransaction();

        ReversalTransaction transaction = new ReversalTransaction();
        transaction.setReferencedTransaction(referencedTransaction);

        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);

        Assertions.assertTrue(errors.hasErrors());
        Assertions.assertEquals(1, errors.getFieldErrorCount("referencedTransaction"));
        Assertions.assertEquals(
                "REVERSAL transaction type should not reference transaction from type CHARGE",
                errors.getFieldError("referencedTransaction").getDefaultMessage());
    }

    @Test
    public void validateReferencedTransactionIsNull() {
        ReversalTransaction transaction = new ReversalTransaction();

        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @Test
    public void validateAmountIsSet() {
        AuthorizeTransaction referencedTransaction = new AuthorizeTransaction();
        referencedTransaction.setAmount(new BigDecimal(100));

        ReversalTransaction transaction = new ReversalTransaction();
        transaction.setReferencedTransaction(referencedTransaction);
        transaction.setAmount(new BigDecimal(50));

        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);

        Assertions.assertTrue(errors.hasErrors());
        Assertions.assertEquals(1, errors.getFieldErrorCount("amount"));
        Assertions.assertEquals(
                "Transaction from type REVERSAL should not have amount set",
                errors.getFieldError("amount").getDefaultMessage());
    }

    @Test
    public void validateValidReversalTransaction() {
        AuthorizeTransaction referencedTransaction = new AuthorizeTransaction();
        referencedTransaction.setAmount(new BigDecimal(100));

        ReversalTransaction transaction = new ReversalTransaction();
        transaction.setReferencedTransaction(referencedTransaction);

        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);

        Assertions.assertFalse(errors.hasErrors());
    }
}
