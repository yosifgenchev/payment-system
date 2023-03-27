package com.paymentsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private BigDecimal totalTransactionSum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "merchant")
    @ToString.Exclude
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
        BigDecimal result = BigDecimal.ZERO;
        for (Transactable t : transactions) {
            result = result.add(t.getAmount());
        }
        totalTransactionSum = result;
    }

    public enum Status {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchant merchant = (Merchant) o;
        return id == merchant.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}