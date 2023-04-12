package com.paymentsystem.validation;

import com.paymentsystem.model.ChargeTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Slf4j
public class ChargeTransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ChargeTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChargeTransaction chargeTransaction = (ChargeTransaction) target;

        if (chargeTransaction.getAmount().compareTo(BigDecimal.ZERO) < 1) {
            String message = "Transaction amount for CHARGE transaction type should not be less than or equal to 0!";
            log.error(message);
            errors.rejectValue("amount", "transaction.amount.invalid", message);
        }
    }
}
