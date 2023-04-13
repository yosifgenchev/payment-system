package com.paymentsystem.handlers;

import com.paymentsystem.model.AuthorizeTransaction;
import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.service.MerchantService;
import com.paymentsystem.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TransactionEventHandlerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private MerchantService merchantService;

    @InjectMocks
    private TransactionEventHandler transactionEventHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleTransactionAfterCreateWithSuccessStatus() {
        Transaction transaction = new AuthorizeTransaction();
        transaction.setStatus("success");
        Merchant merchant = new Merchant();
        merchant.setId(1L);
        transaction.setMerchant(merchant);

        transactionEventHandler.handleTransactionAfterCreate(transaction);

        verify(merchantService, times(1)).updateMerchantTransactionsData(merchant.getId());
        verify(transactionService, times(1)).modifyReferencedTransactionStatusIfNeeded(transaction);
    }

    @Test
    public void testHandleTransactionAfterCreateWithErrorStatus() {
        Transaction transaction = new AuthorizeTransaction();
        transaction.setStatus("error");

        transactionEventHandler.handleTransactionAfterCreate(transaction);

        verify(merchantService, never()).updateMerchantTransactionsData(anyLong());
        verify(transactionService, never()).modifyReferencedTransactionStatusIfNeeded(any());
    }
}
