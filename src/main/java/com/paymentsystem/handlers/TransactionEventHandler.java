package com.paymentsystem.handlers;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.model.TransactionType;
import com.paymentsystem.service.MerchantService;
import com.paymentsystem.service.TransactableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@RepositoryEventHandler
public class TransactionEventHandler {

    @Autowired
    private TransactableService transactableService;

    @Autowired
    private MerchantService merchantService;

    @HandleAfterCreate
    public void handleTransactionAfterCreate(Transaction transaction) {
        if (transaction.getStatus().equals("error")) {
            return;
        }

        if (transaction.getType() == TransactionType.CHARGE || transaction.getType() == TransactionType.REFUND) {
            Optional<Merchant> merchantOptional = merchantService.findById(transaction.getMerchant().getId());

            merchantOptional.ifPresent(this::updateMerchantTotalTransactionSum);
        }

        if (transaction.getType() == TransactionType.REFUND) {
            Transaction t = transactableService.findTransactionByUuid(transaction.getReferencedTransaction().getUuid());
            t.setStatus("refunded");
            transactableService.save(t);
        }

        if (transaction.getType() == TransactionType.REVERSAL) {
            Transaction t = transactableService.findTransactionByUuid(transaction.getReferencedTransaction().getUuid());
            t.setStatus("reversed");
            transactableService.save(t);
        }
    }

    private void updateMerchantTotalTransactionSum(Merchant merchant) {
        BigDecimal transactionBeforeUpdate = merchant.getTotalTransactionSum();
        merchant.updateTotalTransactionSum();
        BigDecimal transactionAfterUpdate = merchant.getTotalTransactionSum();
        merchantService.save(merchant);
        log.info(String.format("Total transaction for %s changed from %s to %s", merchant.getName(), transactionBeforeUpdate, transactionAfterUpdate));
    }
}
