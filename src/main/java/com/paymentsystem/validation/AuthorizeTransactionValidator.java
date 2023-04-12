package com.paymentsystem.validation;

import com.paymentsystem.model.AuthorizeTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Slf4j
public class AuthorizeTransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthorizeTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        AuthorizeTransaction authorizeTransaction = (AuthorizeTransaction) target;

        if (authorizeTransaction.getAmount().compareTo(BigDecimal.ZERO) < 1) {
            String message = "Transaction amount for AUTHORIZE transaction type should not be less than or equal to 0!";
            log.error(message);
            errors.rejectValue("amount", "transaction.amount.invalid", message);
        }
    }
}
