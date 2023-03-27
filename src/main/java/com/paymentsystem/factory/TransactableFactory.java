package com.paymentsystem.factory;

import com.paymentsystem.model.Transactable;

import java.math.BigDecimal;

public abstract class TransactableFactory {

    public abstract Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, BigDecimal amount);

}
