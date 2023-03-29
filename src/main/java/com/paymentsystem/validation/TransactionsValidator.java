package com.paymentsystem.validation;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.model.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Ensures that transaction will not be created unless the merchant
 * to which it is assigned is in active status.
 */
@Slf4j
public class TransactionsValidator implements Validator {
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

        validateAuthorizeTransaction(transaction, errors);

        validateChargeTransaction(transaction, errors);

        validateRefundTransaction(transaction, errors);

        validateReversalTransaction(transaction, errors);
    }

    private void validateReferencedTransactionStatus(Transaction transaction) {
        if (transaction.getReferencedTransaction() != null) {
            String status = transaction.getReferencedTransaction().getStatus();
            if (!Objects.equals(status, "approved") && !Objects.equals(status, "refunded")) {
                log.warn(String.format("A transaction with status %s is referenced, therefore the status of the submitted transaction is marked as 'error'", status));
                transaction.setStatus("error");
            }
        }
    }

    private void validateAuthorizeTransaction(Transaction transaction, Errors errors) {
        validateTransaction(transaction.getType() == TransactionType.AUTHORIZE && transaction.getAmount().compareTo(BigDecimal.ZERO) < 1,
                String.format("Transaction amount for %s transaction type should not be less than or equal to 0!", transaction.getType()),
                errors,
                "amount",
                "transaction.amount.invalid");
    }

    private void validateChargeTransaction(Transaction transaction, Errors errors) {
        validateTransaction(transaction.getType() == TransactionType.CHARGE && transaction.getAmount().compareTo(BigDecimal.ZERO) < 1,
                String.format("Transaction amount for %s transaction type should not be less than or equal to 0!",
                        transaction.getType()),
                errors,
                "amount",
                "transaction.amount.invalid");
    }

    private void validateRefundTransaction(Transaction transaction, Errors errors) {
        Transaction referencedTransaction = transaction.getReferencedTransaction();

        if (transaction.getType() == TransactionType.REFUND && referencedTransaction != null) {
            validateTransaction(referencedTransaction.getType() != TransactionType.CHARGE,
                    String.format("%s transaction type should not reference transaction from type %s!", transaction.getType(), referencedTransaction.getType()),
                    errors,
                    "referencedTransaction",
                    "transaction.referenced.transaction.type.invalid");
        }
    }

    private void validateReversalTransaction(Transaction transaction, Errors errors) {
        Transaction referencedTransaction = transaction.getReferencedTransaction();

        if (transaction.getType() == TransactionType.REVERSAL) {
            if (referencedTransaction != null) {
                validateTransaction(referencedTransaction.getType() != TransactionType.AUTHORIZE,
                        String.format("%s transaction type should not reference transaction from type %s!", transaction.getType(), referencedTransaction.getType()),
                        errors,
                        "referencedTransaction",
                        "transaction.referenced.transaction.type.invalid");
            }

            validateTransaction(transaction.getAmount() != null,
                    String.format("Transaction from type %s should not have amount set!", transaction.getType()),
                    errors,
                    "amount",
                    "transaction.amount.invalid");
        }
    }

    private void validatePresenceOfReferencedTransaction(Transaction transaction, Errors errors) {
        TransactionType type = transaction.getType();
        Transaction referencedTransaction = transaction.getReferencedTransaction();

        if (referencedTransaction == null && type != TransactionType.AUTHORIZE) {
            String message = String.format("No transaction is referenced for %s transaction type, but it should be!", type.toString());

            log.error(message);
            errors.reject("transaction.referenced.transaction.missing", message);
        }
    }

    private void validateTransaction(boolean isValid, String message, Errors errors, String rejectedField, String errorCode) {
        if (isValid) {
            log.error(message);
            errors.rejectValue(rejectedField, errorCode, message);
        }
    }
    private void validateMerchantStatus(Errors errors, Transaction transaction) {
        if (transaction.getMerchant().getStatus() != Merchant.Status.active) {
            log.error(String.format("Unable to create the following transaction because the related merchant's status is not active:\n %s", transaction));
            errors.rejectValue("merchant", "merchant.status.incorrect", "Merchant status is not 'active'!");
        }
    }
}
