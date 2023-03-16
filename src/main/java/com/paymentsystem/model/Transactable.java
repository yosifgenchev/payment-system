package com.paymentsystem.model;

public interface Transactable {

    double getAmount();

    String getStatus();

    void setStatus(String status);

    String getCustomerEmail();

    void setCustomerEmail(String customerEmail);

}
