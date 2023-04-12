package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@DiscriminatorValue("REFUND")
@NoArgsConstructor
@Getter
public class RefundTransaction extends Transaction implements Referrable {

    @OneToOne(targetEntity = Transaction.class)
    @JoinColumn(name = "reference_id", nullable = false)
    private ChargeTransaction chargeTransaction;
    @Override
    public TransactionType getType() {
        return TransactionType.REFUND;
    }

    @Override
    public Transaction getReferencedTransaction() {
        return chargeTransaction;
    }

    @Override
    public boolean isModifyingStatus() {
        return true;
    }

    @Override
    public Optional<Transaction> getTransactionToBeModified() {
        return Optional.of(chargeTransaction);
    }
}
