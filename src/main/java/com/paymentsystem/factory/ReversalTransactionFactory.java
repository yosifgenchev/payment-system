package com.paymentsystem.factory;

import com.paymentsystem.model.ReversalTransaction;
import com.paymentsystem.model.Transactable;

import java.math.BigDecimal;

public class ReversalTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, BigDecimal amount) {
        return new ReversalTransaction(referredTransaction, customerEmail, customerPhone);
    }

}
