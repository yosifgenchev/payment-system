package com.paymentsystem.model;

import jakarta.persistence.Entity;

@Entity
public class ReversalTransaction extends Transaction {

    public ReversalTransaction(Transactable referredTransaction, String customerEmail, String customerPhone) {
        super((Transaction) referredTransaction, 0, customerEmail, customerPhone, "error");
        if ("approved".equals(referredTransaction.getStatus())) {
            referredTransaction.setStatus("reversed");
            setStatus("approved");
        }
    }

    public ReversalTransaction() {

    }
}
