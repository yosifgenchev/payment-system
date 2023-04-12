package com.paymentsystem.handlers;

import com.paymentsystem.model.Transaction;
import com.paymentsystem.service.MerchantService;
import com.paymentsystem.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Slf4j
@RepositoryEventHandler
public class TransactionEventHandler {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantService merchantService;

    @HandleAfterCreate
    public void handleTransactionAfterCreate(Transaction transaction) {
        if (transaction.getStatus().equals("error")) {
            return;
        }

        merchantService.updateMerchantTransactionsData(transaction.getMerchant().getId());

        transactionService.modifyReferencedTransactionStatusIfNeeded(transaction);

    }
}
