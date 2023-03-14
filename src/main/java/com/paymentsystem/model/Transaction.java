package com.paymentsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "uuid")
    private final String uuid = UUID.randomUUID().toString();

    @Min(1)
    @Column(name = "amount", nullable = false)
    private double amount;

    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotNull
    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "customer_phone")
    private String customerPhone;

    @NotNull
    @Column(name = "status", nullable = false)
    private Status status;

    public Transaction() {

    }

    public Transaction(double amount, String customerEmail, String customerPhone, Status status) {
        this.amount = amount;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.status = status;
    }

    enum Status {
        approved,
        reversed,
        refunded,
        error;
    }
}