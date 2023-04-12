package com.paymentsystem.service;

import com.paymentsystem.model.Merchant;

import java.util.List;
import java.util.Optional;

public interface MerchantService {

    Optional<Merchant> findById(Long id);

    List<Merchant> findAll();

    void updateMerchantTransactionsData(Long id);

    void updateMerchant(Long id, Merchant updatedMerchant);

    void save(Merchant merchant);

    void delete(Merchant merchant);
}
