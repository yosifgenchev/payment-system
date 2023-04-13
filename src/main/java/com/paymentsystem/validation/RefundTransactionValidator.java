package com.paymentsystem.validation;

import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.RefundTransaction;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.model.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

@Slf4j
public class RefundTransactionValidator extends AbstractReferrableTransactionValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RefundTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RefundTransaction transaction = (RefundTransaction) target;

        validatePresenceOfReferencedTransaction(transaction, errors);

        if (isReferencedTransactionInvalid(transaction)) {
            transaction.setStatus("error");
        }

        ChargeTransaction referencedTransaction = transaction.getChargeTransaction();

        if (referencedTransaction != null && referencedTransaction.getType() != TransactionType.CHARGE) {
            String message = String.format("REFUND transaction type should not reference transaction from type %s", referencedTransaction.getType().toString());
            log.error(message);
            errors.rejectValue("referencedTransaction", "transaction.referenced.transaction.type.invalid", message);
        }
    }
}
