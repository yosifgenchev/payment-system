package com.paymentsystem.factory;

import com.paymentsystem.model.AuthorizeTransaction;
import com.paymentsystem.model.Transactable;

public class AuthorizeTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, double amount) {
        return new AuthorizeTransaction(amount, customerEmail, customerPhone);
    }
}
