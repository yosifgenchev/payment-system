package com.paymentsystem.service;

import com.paymentsystem.dto.TransactionDTO;
import com.paymentsystem.model.Transactable;
import com.paymentsystem.model.Transaction;

import java.util.List;

public interface TransactableService {

    Transactable convertTransactableDTOtoTransactable(TransactionDTO transactionDTO, Transaction t);
    Transaction findTransactionByUuid(String uuid);

    List<Transaction> findAll();

}
