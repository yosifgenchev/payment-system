package com.paymentsystem.service.impl;

import com.paymentsystem.model.Transaction;
import com.paymentsystem.repository.TransactionRepository;
import com.paymentsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

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
    public void changeTransactionStatus(String transactionUUID, String status) {
        Transaction t = findTransactionByUuid(transactionUUID);
        t.setStatus(status);
        save(t);
    }

    @Override
    public void save(Transaction t) {
        transactableRepository.save(t);
    }
}
