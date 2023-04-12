package com.paymentsystem.validation;

import com.paymentsystem.model.Referrable;
import com.paymentsystem.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Slf4j
public abstract class AbstractReferrableTransactionValidator implements Validator {

    boolean isReferencedTransactionInvalid(Referrable referrable) {
        if (referrable.getReferencedTransaction() != null) {
            String status = referrable.getReferencedTransaction().getStatus();
            if (!Objects.equals(status, "approved") && !Objects.equals(status, "refunded")) {
                log.warn(String.format("A transaction with status %s is referenced, therefore the status of the submitted transaction is marked as 'error'", status));
                return true;
            }
        }
        return false;
    }

    void validatePresenceOfReferencedTransaction(Referrable referrable, Errors errors) {
        Transaction referencedTransaction = referrable.getReferencedTransaction();

        if (referencedTransaction == null) {
            String message = "No transaction is referenced, but it should be!";

            log.error(message);
            errors.reject("transaction.referenced.transaction.missing", message);
        }
    }
}
