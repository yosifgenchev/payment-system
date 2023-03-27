package com.paymentsystem.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class ReversalTransaction extends Transaction {

    public ReversalTransaction(Transactable referredTransaction, String customerEmail, String customerPhone) {
        super((Transaction) referredTransaction, BigDecimal.ZERO, customerEmail, customerPhone, "error");
        if ("approved".equals(referredTransaction.getStatus())) {
            referredTransaction.setStatus("reversed");
            setStatus("approved");
        }
    }

    public ReversalTransaction() {

    }
}
