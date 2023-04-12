package com.paymentsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CHARGE")
@NoArgsConstructor
@Getter
public class ChargeTransaction extends Transaction implements Referrable {

    @OneToOne(targetEntity = Transaction.class)
    @JoinColumn(name = "reference_id", nullable = false)
    private AuthorizeTransaction authorizeTransaction;

    @Override
    public TransactionType getType() {
        return TransactionType.CHARGE;
    }

    @Override
    public Transaction getReferencedTransaction() {
        return authorizeTransaction;
    }

    @Override
    public void makeStatusTransition() {
        setStatus("refunded");
    }
}
