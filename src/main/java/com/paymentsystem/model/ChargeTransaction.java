package com.paymentsystem.model;

import jakarta.persistence.Entity;

@Entity
public class ChargeTransaction extends Transaction {

    public ChargeTransaction(Transaction referredTransaction, double amount, String customerEmail, String customerPhone) {
        super(referredTransaction, amount, customerEmail, customerPhone, "error");
        if ("approved".equals(referredTransaction.getStatus())) {
            setStatus("approved");
        }
    }

    public ChargeTransaction() {

    }
}
