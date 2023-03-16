package com.paymentsystem.factory;

import com.paymentsystem.model.RefundTransaction;
import com.paymentsystem.model.Transactable;

public class RefundTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, double amount) {
        return new RefundTransaction(referredTransaction, customerEmail, customerPhone);
    }

}
