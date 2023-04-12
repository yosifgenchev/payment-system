package com.paymentsystem.validation;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.model.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Ensures that transaction will not be created unless the merchant
 * to which it is assigned is in active status.
 */
@Slf4j
public class TransactionValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Transaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Transaction transaction = (Transaction) obj;

        validateMerchantStatus(errors, transaction);

        validatePresenceOfReferencedTransaction(transaction, errors);

        validateReferencedTransactionStatus(transaction);

    }

    void validateReferencedTransactionStatus(Transaction transaction) {
        if (transaction.getReferencedTransaction() != null) {
            String status = transaction.getReferencedTransaction().getStatus();
            if (!Objects.equals(status, "approved") && !Objects.equals(status, "refunded")) {
                log.warn(String.format("A transaction with status %s is referenced, therefore the status of the submitted transaction is marked as 'error'", status));
                transaction.setStatus("error");
            }
        }
    }


    void validatePresenceOfReferencedTransaction(Transaction transaction, Errors errors) {
        TransactionType type = transaction.getType();
        Transaction referencedTransaction = transaction.getReferencedTransaction();

        if (referencedTransaction == null && type != TransactionType.AUTHORIZE) {
            String message = String.format("No transaction is referenced for %s transaction type, but it should be!", type.toString());

            log.error(message);
            errors.reject("transaction.referenced.transaction.missing", message);
        }
    }
    void validateMerchantStatus(Errors errors, Transaction transaction) {
        if (transaction.getMerchant().getStatus() != Merchant.Status.active) {
            log.error(String.format("Unable to create the following transaction because the related merchant's status is not active:\n %s", transaction));
            errors.rejectValue("merchant", "merchant.status.incorrect", "Merchant status is not 'active'!");
        }
    }
}
