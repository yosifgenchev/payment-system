package com.paymentsystem.validation;

import com.paymentsystem.model.AuthorizeTransaction;
import com.paymentsystem.model.ReversalTransaction;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.model.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

@Slf4j
public class ReversalTransactionValidator extends AbstractReferrableTransactionValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ReversalTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReversalTransaction transaction = (ReversalTransaction) target;

        validatePresenceOfReferencedTransaction(transaction, errors);

        if (isReferencedTransactionInvalid(transaction)) {
            transaction.setStatus("error");
        }

        AuthorizeTransaction referencedTransaction = transaction.getAuthorizeTransaction();

        if (referencedTransaction != null && referencedTransaction.getType() != TransactionType.AUTHORIZE) {
            String message = String.format("REVERSAL transaction type should not reference transaction from type %s", referencedTransaction.getType().toString());
            log.error(message);
            errors.rejectValue("referencedTransaction", "transaction.referenced.transaction.type.invalid", message);
        }

        if (transaction.getAmount() != null) {
            String message = "Transaction from type REVERSAL should not have amount set";
            log.error(message);
            errors.rejectValue("amount", "transaction.amount.invalid", message);
        }
    }
}
