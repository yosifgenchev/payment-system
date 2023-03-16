package com.paymentsystem.factory;

import com.paymentsystem.model.ReversalTransaction;
import com.paymentsystem.model.Transactable;

public class ReversalTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, double amount) {
        return new ReversalTransaction(referredTransaction, customerEmail, customerPhone);
    }

}
