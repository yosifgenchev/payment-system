package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@DiscriminatorValue("REVERSAL")
@NoArgsConstructor
@Setter
@Getter
public class ReversalTransaction extends Transaction implements Referrable {

    @OneToOne(targetEntity = Transaction.class)
    @JoinColumn(name = "reference_id", nullable = false)
    private AuthorizeTransaction authorizeTransaction;
    @Override
    public TransactionType getType() {
        return TransactionType.REVERSAL;
    }

    @Override
    public Transaction getReferencedTransaction() {
        return authorizeTransaction;
    }

    @Override
    public boolean isModifyingStatus() {
        return true;
    }

    @Override
    public Optional<Transaction> getTransactionToBeModified() {
        return Optional.of(authorizeTransaction);
    }
}
