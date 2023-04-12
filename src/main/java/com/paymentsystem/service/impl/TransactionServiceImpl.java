package com.paymentsystem.service.impl;

import com.paymentsystem.model.Transaction;
import com.paymentsystem.repository.TransactionRepository;
import com.paymentsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction findTransactionByUuid(String uuid) {
        return transactionRepository.findTransactionByUuid(uuid).get();
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public void changeTransactionStatus(String transactionUUID, String status) {
        Transaction t = findTransactionByUuid(transactionUUID);
        t.setStatus(status);
        save(t);
    }

    @Override
    public void save(Transaction t) {
        transactionRepository.save(t);
    }

    @Override
    public void delete(Transaction t) {
        transactionRepository.delete(t);
    }

    @Override
    public void deleteOldTransactions() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(1);
        List<Transaction> oldTransactions = transactionRepository.findOlderThan(threshold);

        oldTransactions.forEach(t -> log.info(String.format("%s transaction is going to be deleted.", t)));

        oldTransactions.forEach(this::delete);
    }

    @Override
    public void modifyReferencedTransactionStatusIfNeeded(Transaction t) {
        if (t.isModifyingStatus()) {
            Optional<Transaction> referencedTransaction = t.getTransactionToBeModified();

            if (referencedTransaction.isPresent()) {
                Transaction transactionToBeModified = referencedTransaction.get();
                transactionToBeModified.makeStatusTransition();
                save(transactionToBeModified);
            }
        }
    }
}
