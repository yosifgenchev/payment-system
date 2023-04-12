package com.paymentsystem.service;

import com.paymentsystem.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction findTransactionByUuid(String uuid);

    List<Transaction> findAll();

    void changeTransactionStatus(String transactionUUID, String status);

    void save(Transaction t);

    void delete(Transaction t);

    void deleteOldTransactions();

    void modifyReferencedTransactionStatusIfNeeded(Transaction t);

}
