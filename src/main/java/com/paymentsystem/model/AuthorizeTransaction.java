package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("AUTHORIZE")
@NoArgsConstructor
public class AuthorizeTransaction extends Transaction {
    
    @Override
    public TransactionType getType() {
        return TransactionType.AUTHORIZE;
    }
}
