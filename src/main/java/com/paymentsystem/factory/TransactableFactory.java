package com.paymentsystem.factory;

import com.paymentsystem.model.Transactable;

public abstract class TransactableFactory {

    public abstract Transactable createTransactable(Transactable referredTransaction, String customerEmail, String customerPhone, double amount);

}
