package com.paymentsystem.factory;

import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.Transactable;
import com.paymentsystem.model.Transaction;

public class ChargeTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, double amount) {
        return new ChargeTransaction((Transaction) referredTransaction, amount, customerEmail, customerPhone);
    }

}
