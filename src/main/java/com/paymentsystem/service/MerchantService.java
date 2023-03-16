package com.paymentsystem.service;

import com.paymentsystem.dto.MerchantDTO;
import com.paymentsystem.model.Merchant;

import java.util.List;

public interface MerchantService {

    Merchant convertMerchantDTOtoMerchant(MerchantDTO merchantDTO);

    void save(Merchant merchant);

    Merchant findById(long id);
    List<Merchant> findAll();
}
