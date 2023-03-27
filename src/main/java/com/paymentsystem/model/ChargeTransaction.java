package com.paymentsystem.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class ChargeTransaction extends Transaction {

    public ChargeTransaction(Transaction referredTransaction, BigDecimal amount, String customerEmail, String customerPhone) {
        super(referredTransaction, amount, customerEmail, customerPhone, "error");
        if ("approved".equals(referredTransaction.getStatus())) {
            setStatus("approved");
        }
    }

    public ChargeTransaction() {

    }
}
