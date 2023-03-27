package com.paymentsystem.factory;

import com.paymentsystem.model.AuthorizeTransaction;
import com.paymentsystem.model.Transactable;

import java.math.BigDecimal;

public class AuthorizeTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, BigDecimal amount) {
        return new AuthorizeTransaction(amount, customerEmail, customerPhone);
    }
}
