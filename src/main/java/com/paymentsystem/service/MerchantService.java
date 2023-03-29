package com.paymentsystem.service;

import com.paymentsystem.model.Merchant;

import java.util.Optional;

public interface MerchantService {

    Optional<Merchant> findById(Long id);

    void save(Merchant merchant);
}
