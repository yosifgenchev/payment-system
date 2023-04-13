package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("AUTHORIZE")
@NoArgsConstructor
@Setter
@Getter
public class AuthorizeTransaction extends Transaction {
    
    @Override
    public TransactionType getType() {
        return TransactionType.AUTHORIZE;
    }


    @Override
    public void makeStatusTransition() {
        setStatus("reversed");
    }

}
