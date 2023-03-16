package com.paymentsystem.model;

import jakarta.persistence.Entity;

@Entity
public class AuthorizeTransaction extends Transaction {

    public AuthorizeTransaction(double amount, String customerEmail, String customerPhone) {
        super(null, amount, customerEmail, customerPhone, "approved");
    }

    public AuthorizeTransaction() {

    }
}
