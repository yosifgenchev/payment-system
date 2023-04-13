package com.paymentsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = AuthorizeTransaction.class, name = "AUTHORIZE"),
        @JsonSubTypes.Type(value = ChargeTransaction.class, name = "CHARGE"),
        @JsonSubTypes.Type(value = RefundTransaction.class, name = "REFUND"),
        @JsonSubTypes.Type(value = ReversalTransaction.class, name = "REVERSAL")

})
public abstract class Transaction {

    @Id
    @Column(name = "uuid")
    @GeneratedValue
    @UuidGenerator
    private String uuid;

    @CreationTimestamp
    @Column(name="created_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotNull
    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    @ToString.Exclude
    private Merchant merchant;

    public abstract TransactionType getType();

    @JsonIgnore
    public boolean isModifyingStatus() {
        return false;
    }

    @JsonIgnore
    public Optional<Transaction> getTransactionToBeModified() {
        return Optional.empty();
    }

    @JsonIgnore
    public void makeStatusTransition() {
    }

}