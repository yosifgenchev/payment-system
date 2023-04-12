package com.paymentsystem.service;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transaction;

import java.util.Optional;

public interface MerchantService {

    Optional<Merchant> findById(Long id);

    void updateMerchantData(Long id);

    void save(Merchant merchant);
}
