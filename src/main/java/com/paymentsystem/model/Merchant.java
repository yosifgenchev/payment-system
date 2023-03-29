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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private BigDecimal totalTransactionSum = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "merchant")
    @ToString.Exclude
    private Set<Transaction> transactions = new HashSet<>();

    public void updateTotalTransactionSum() {
        totalTransactionSum = getChargeTransactionsAmount().subtract(getRefundTransactionsAmount());
    }

    public BigDecimal getChargeTransactionsAmount() {
        return getTransactionsAmount(getApprovedChargeTransactions());

    }

    public BigDecimal getRefundTransactionsAmount() {
        return getTransactionsAmount(getApprovedRefundTransactions());
    }

    public BigDecimal getTransactionsAmount(Set<Transaction> transactions) {
        BigDecimal result = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            result = result.add(t.getAmount());
        }

        return result;
    }

    Stream<Transaction> getApprovedTransactionsStream() {
        return transactions.stream().filter(t -> t.getStatus().equals("approved"));
    }

    Set<Transaction> getApprovedChargeTransactions() {
        return getTransactionsOfType(TransactionType.CHARGE);
    }

    Set<Transaction> getApprovedRefundTransactions() {
        return getTransactionsOfType(TransactionType.REFUND);
    }

    Set<Transaction> getTransactionsOfType(final TransactionType type) {
        return getApprovedTransactionsStream().filter(t -> t.getType() == type).collect(Collectors.toSet());
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