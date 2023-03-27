package com.paymentsystem.factory;

import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.Transactable;
import com.paymentsystem.model.Transaction;

import java.math.BigDecimal;

public class ChargeTransactionFactory extends TransactableFactory {

    @Override
    public Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, BigDecimal amount) {
        return new ChargeTransaction((Transaction) referredTransaction, amount, customerEmail, customerPhone);
    }

}
