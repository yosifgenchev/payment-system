package com.paymentsystem.validation;

import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.RefundTransaction;
import com.paymentsystem.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RefundTransactionValidatorTest {

    private final RefundTransactionValidator validator = new RefundTransactionValidator();

    @Test
    void validateValidTransaction() {
        RefundTransaction transaction = new RefundTransaction();
        Transaction referencedTransaction = new ChargeTransaction();
        transaction.setReferencedTransaction(referencedTransaction);
        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateTransactionWithNullReferencedTransaction() {
        RefundTransaction transaction = new RefundTransaction();
        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");
        validator.validate(transaction, errors);
        assertFalse(errors.hasErrors());
    }
}
