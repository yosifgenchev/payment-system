package com.paymentsystem.service;

import com.paymentsystem.model.Merchant;

import java.util.List;

public interface MerchantService {

    void save(Merchant merchant);

    Merchant findById(long id);
    List<Merchant> findAll();
}
