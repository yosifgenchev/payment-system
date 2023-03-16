package com.paymentsystem.service.impl;

import com.paymentsystem.dto.TransactionDTO;
import com.paymentsystem.factory.AuthorizeTransactionFactory;
import com.paymentsystem.factory.ChargeTransactionFactory;
import com.paymentsystem.factory.RefundTransactionFactory;
import com.paymentsystem.factory.ReversalTransactionFactory;
import com.paymentsystem.model.Transactable;
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
    public Transactable convertTransactableDTOtoTransactable(TransactionDTO transactionDTO, Transaction t) {

        Transactable transactable = null;

        if (transactionDTO.getDtype() != null) {
            switch (transactionDTO.getDtype()) {
                case "AuthorizeTransaction" -> {
                    AuthorizeTransactionFactory authorizeTransactionFactory = new AuthorizeTransactionFactory();
                    transactable = authorizeTransactionFactory.createTransactable(t, transactionDTO.getCustomerEmail(), transactionDTO.getCustomerPhone(), transactionDTO.getAmount());
                }
                case "ChargeTransaction" -> {
                    ChargeTransactionFactory chargeTransactionFactory = new ChargeTransactionFactory();
                    transactable = chargeTransactionFactory.createTransactable(t, transactionDTO.getCustomerEmail(), transactionDTO.getCustomerPhone(), transactionDTO.getAmount());
                }
                case "RefundTransaction" -> {
                    RefundTransactionFactory refundTransactionFactory = new RefundTransactionFactory();
                    transactable = refundTransactionFactory.createTransactable(t, transactionDTO.getCustomerEmail(), transactionDTO.getCustomerPhone(), transactionDTO.getAmount());
                }
                case "ReversalTransaction" -> {
                    ReversalTransactionFactory reversalTransactionFactory = new ReversalTransactionFactory();
                    transactable = reversalTransactionFactory.createTransactable(t, transactionDTO.getCustomerEmail(), transactionDTO.getCustomerPhone(), transactionDTO.getAmount());
                }
            }
        }
        return transactable;
    }

    @Override
    public Transaction findTransactionByUuid(String uuid) {
        return transactableRepository.findTransactionByUuid(uuid).get();
    }

    @Override
    public List<Transaction> findAll() {
        return transactableRepository.findAll();
    }
}
