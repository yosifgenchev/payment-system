package com.paymentsystem.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class AuthorizeTransaction extends Transaction {

    public AuthorizeTransaction(BigDecimal amount, String customerEmail, String customerPhone) {
        super(null, amount, customerEmail, customerPhone, "approved");
    }

    public AuthorizeTransaction() {

    }
}
