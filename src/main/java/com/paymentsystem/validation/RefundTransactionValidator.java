package com.paymentsystem.validation;

import com.paymentsystem.model.RefundTransaction;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.model.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public class RefundTransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RefundTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RefundTransaction transaction = (RefundTransaction) target;
        Transaction referencedTransaction = transaction.getReferencedTransaction();

        if (referencedTransaction != null && referencedTransaction.getType() != TransactionType.CHARGE) {
            String message = String.format("REFUND transaction type should not reference transaction from type %s", referencedTransaction.getType().toString());
            log.error(message);
            errors.rejectValue("referencedTransaction", "transaction.referenced.transaction.type.invalid", message);
        }
    }
}
