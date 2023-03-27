package com.paymentsystem.factory;

import com.paymentsystem.model.RefundTransaction;
import com.paymentsystem.model.Transactable;

import java.math.BigDecimal;

public class RefundTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, BigDecimal amount) {
        return new RefundTransaction(referredTransaction, customerEmail, customerPhone);
    }

}
