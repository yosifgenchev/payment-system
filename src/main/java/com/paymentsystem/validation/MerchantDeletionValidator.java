package com.paymentsystem.validation;

import com.paymentsystem.model.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Ensures that merchant will not be deleted unless there are no
 * related payment transactions.
 */
@Slf4j
public class MerchantDeletionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Merchant.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Merchant merchant = (Merchant) target;

        if (!merchant.getTransactions().isEmpty()) {
            log.error(String.format("Unable to delete the following merchant because of related transactions:\n %s", merchant));
            errors.reject("merchant.related.transactions", merchant.getName() + " has related transactions!");
        }

    }
}
