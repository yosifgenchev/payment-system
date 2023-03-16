package com.paymentsystem.service.impl;

import com.paymentsystem.dto.MerchantDTO;
import com.paymentsystem.model.Merchant;
import com.paymentsystem.repository.MerchantRepository;
import com.paymentsystem.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    @Override
    public Merchant convertMerchantDTOtoMerchant(MerchantDTO merchantDTO) {
        Merchant m = new Merchant();
        //TODO validation
        m.setStatus(Merchant.Status.valueOf(merchantDTO.getStatus()));
        m.setDescription(merchantDTO.getDescription());
        m.setName(merchantDTO.getName());
        m.setEmail(merchantDTO.getEmail());
        return m;
    }

    @Override
    public void save(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    @Override
    public Merchant findById(long id) {
        return merchantRepository.findById(id).get();
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }
}
