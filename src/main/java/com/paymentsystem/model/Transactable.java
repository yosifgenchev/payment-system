package com.paymentsystem.model;

import java.math.BigDecimal;

public interface Transactable {

    BigDecimal getAmount();

    String getStatus();

    void setStatus(String status);

    String getCustomerEmail();

    void setCustomerEmail(String customerEmail);

    TransactionType getType();

}
