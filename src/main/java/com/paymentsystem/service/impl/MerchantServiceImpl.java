package com.paymentsystem.service.impl;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.repository.MerchantRepository;
import com.paymentsystem.service.MerchantService;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    @Override
    public Optional<Merchant> findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public void save(Merchant merchant) {
        merchantRepository.save(merchant);
    }
}
