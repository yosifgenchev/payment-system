package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("REVERSAL")
@NoArgsConstructor
public class ReversalTransaction extends Transaction {
    @Override
    public TransactionType getType() {
        return TransactionType.REVERSAL;
    }
}
