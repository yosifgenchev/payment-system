package com.paymentsystem.service;

import com.paymentsystem.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction getTransactionByReferredTransactionUUID(String referred_transaction_uuid);

    Transaction findTransactionByUuid(String uuid);

    List<Transaction> findAll();

    void changeTransactionStatus(String transactionUUID, String status);

    void save(Transaction t);

}
