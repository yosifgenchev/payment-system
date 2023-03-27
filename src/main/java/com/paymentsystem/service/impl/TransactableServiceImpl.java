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

//    @Override
//    public Transactable convertTransactableDTOtoTransactable(TransactionDTO transactionDTO, Transaction t) {
//
//        Transactable transactable = null;
//
//        TransactableFactory factory = null;
//
//        if (transactionDTO.getDtype() != null) {
//            switch (transactionDTO.getDtype()) {
//                case "AuthorizeTransaction" -> {
//                    factory = new AuthorizeTransactionFactory();
//                }
//                case "ChargeTransaction" -> {
//                    factory = new ChargeTransactionFactory();
//                }
//                case "RefundTransaction" -> {
//                    factory = new RefundTransactionFactory();
//                }
//                case "ReversalTransaction" -> {
//                    factory = new ReversalTransactionFactory();
//                }
//            }
//
//            if (factory != null) {
//                transactable = factory.createTransactable(t, transactionDTO.getCustomerEmail(), transactionDTO.getCustomerPhone(), transactionDTO.getAmount());
//            }
//
//        }
//        return transactable;
//    }

    @Override
    public Transaction findTransactionByUuid(String uuid) {
        return transactableRepository.findTransactionByUuid(uuid).get();
    }

    @Override
    public List<Transaction> findAll() {
        return transactableRepository.findAll();
    }
}
