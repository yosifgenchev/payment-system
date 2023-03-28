package com.paymentsystem.service.impl;

import com.paymentsystem.model.Transaction;
import com.paymentsystem.repository.TransactionRepository;
import com.paymentsystem.service.TransactableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactableServiceImpl implements TransactableService {

    private final TransactionRepository transactableRepository;
    @Override
    public Transaction getTransactionByReferredTransactionUUID(String referred_transaction_uuid) {
        return referred_transaction_uuid != null ? findTransactionByUuid(referred_transaction_uuid) : null;
    }

    @Override
    public Transaction findTransactionByUuid(String uuid) {
        return transactableRepository.findTransactionByUuid(uuid).get();
    }

    @Override
    public List<Transaction> findAll() {
        return transactableRepository.findAll();
    }

    @Override
    public void save(Transaction t) {
        transactableRepository.save(t);
    }
}
