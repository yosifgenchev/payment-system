package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("AUTHORIZE")
public class AuthorizeTransaction extends Transaction {

    public AuthorizeTransaction(BigDecimal amount, String customerEmail, String customerPhone) {
        super(null, amount, customerEmail, customerPhone, "approved");
    }

    public AuthorizeTransaction() {

    }

    @Override
    public TransactionType getType() {
        return TransactionType.AUTHORIZE;
    }
}
