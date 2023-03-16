package com.paymentsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "total_transaction_sum", nullable = false)
    private double totalTransactionSum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "merchant_id")
    private Set<Transaction> transactions = new HashSet<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setMerchant(this);
        changeTotalTransactionSum();
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setMerchant(null);
        changeTotalTransactionSum();
    }

    private void changeTotalTransactionSum() {
        double result = 0;
        for (Transactable t : transactions) {
            result += t.getAmount();
        }
        totalTransactionSum = result;
    }

    enum Status {
        active,
        inactive
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", totalTransactionSum=" + totalTransactionSum +
                ", transactions=" + transactions +
                '}';
    }


}