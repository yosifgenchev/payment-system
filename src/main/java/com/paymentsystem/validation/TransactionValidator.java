package com.paymentsystem.validation;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

    }

    void validateMerchantStatus(Errors errors, Transaction transaction) {
        if (transaction.getMerchant().getStatus() != Merchant.Status.active) {
            log.error(String.format("Unable to create the following transaction because the related merchant's status is not active:\n %s", transaction));
            errors.rejectValue("merchant", "merchant.status.incorrect", "Merchant status is not 'active'!");
        }
    }
}
