package com.paymentsystem.validation;

import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.RefundTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RefundTransactionValidatorTest {

    @Mock
    private Errors errors;

    @InjectMocks
    private RefundTransactionValidator refundTransactionValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidateWithValidTransaction() {
        RefundTransaction transaction = new RefundTransaction();
        ChargeTransaction chargeTransaction = new ChargeTransaction();
        chargeTransaction.setStatus("approved");
        transaction.setChargeTransaction(chargeTransaction);

        refundTransactionValidator.validate(transaction, errors);

        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }
    
}
