package com.paymentsystem.validation;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transaction;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransactionsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Transaction.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Transaction transaction = (Transaction) obj;

        if (transaction.getMerchant().getStatus() != Merchant.Status.active) {
            errors.rejectValue("merchant", "merchant.status.incorrect", "Merchant status is not 'active'!");
        }
    }
}
