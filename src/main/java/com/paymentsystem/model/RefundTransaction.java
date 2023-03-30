package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("REFUND")
@NoArgsConstructor
public class RefundTransaction extends Transaction {


    @Override
    public TransactionType getType() {
        return TransactionType.REFUND;
    }
}
