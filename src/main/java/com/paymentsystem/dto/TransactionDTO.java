package com.paymentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private double amount;

    private String customerEmail;

    private String customerPhone;

    private String status;

    private String referred_transaction_uuid;

    private long merchant_id;

    private String dtype;

}
