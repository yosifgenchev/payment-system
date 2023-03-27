package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REFUND")
public class RefundTransaction extends Transaction {

    public RefundTransaction(Transactable referredTransaction, String customerEmail, String customerPhone) {
        super((Transaction) referredTransaction, referredTransaction.getAmount(), customerEmail, customerPhone, "error");
        if ("approved".equals(referredTransaction.getStatus())) {
            referredTransaction.setStatus("refunded");
            setStatus("approved");
        }
    }

    public RefundTransaction() {

    }

    @Override
    public TransactionType getType() {
        return TransactionType.REFUND;
    }
}
