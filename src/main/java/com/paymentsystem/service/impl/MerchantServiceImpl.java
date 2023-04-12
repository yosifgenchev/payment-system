package com.paymentsystem.service.impl;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.repository.MerchantRepository;
import com.paymentsystem.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    @Override
    public Optional<Merchant> findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    @Override
    public void updateMerchantTransactionsData(Long id) {
        Optional<Merchant> merchantOptional = findById(id);

        if (merchantOptional.isEmpty()) {
            return;
        }

        Merchant merchant = merchantOptional.get();

        BigDecimal transactionBeforeUpdate = merchant.getTotalTransactionSum();

        merchant.updateTotalTransactionSum();

        BigDecimal transactionAfterUpdate = merchant.getTotalTransactionSum();

        save(merchant);

        log.info(String.format("Total transaction for %s changed from %s to %s", merchant.getName(), transactionBeforeUpdate, transactionAfterUpdate));
    }

    @Override
    public void updateMerchant(Long id, Merchant updatedMerchant) {
        Merchant merchant = findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        merchant.setName(updatedMerchant.getName());
        merchant.setEmail(updatedMerchant.getEmail());
        merchant.setDescription(updatedMerchant.getDescription());
        merchant.setStatus(updatedMerchant.getStatus());

        save(merchant);
    }

    @Override
    public void save(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    @Override
    public void delete(Merchant merchant) {
        merchantRepository.delete(merchant);
    }
}
