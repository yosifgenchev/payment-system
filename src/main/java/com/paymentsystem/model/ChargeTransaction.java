package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CHARGE")
@NoArgsConstructor
public class ChargeTransaction extends Transaction {

    @Override
    public TransactionType getType() {
        return TransactionType.CHARGE;
    }
}
