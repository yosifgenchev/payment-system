package com.paymentsystem.controller;

import com.paymentsystem.dto.TransactionDTO;
import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.ReversalTransaction;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.service.MerchantService;
import com.paymentsystem.service.TransactableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionsControllerTest {

    private TransactionsController paymentsController;

    private TransactableService transactableService;

    private MerchantService merchantService;

    @BeforeEach
    void setUp() {
        transactableService = mock(TransactableService.class);
        merchantService = mock(MerchantService.class);
        paymentsController = new TransactionsController(transactableService, merchantService);
    }

    @Test
    void testCreateTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setMerchant_id(1L);
        transactionDTO.setReferred_transaction_uuid("abc123");
        transactionDTO.setDtype("AuthorizeTransaction");

        Merchant merchant = new Merchant();
        merchant.setId(1L);
        merchant.setStatus(Merchant.Status.active);

        ChargeTransaction chargeTransaction = new ChargeTransaction();

        when(merchantService.findById(1L)).thenReturn(merchant);
        when(transactableService.findTransactionByUuid("abc123")).thenReturn(chargeTransaction);
        when(transactableService.convertTransactableDTOtoTransactable(any(TransactionDTO.class),
                any(Transaction.class))).thenReturn(new ReversalTransaction());

        ResponseEntity<HttpStatus> response = paymentsController.createTransaction(transactionDTO);

        assert response.getStatusCode().equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new ChargeTransaction());
        transactions.add(new ReversalTransaction());

        when(transactableService.findAll()).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = paymentsController.getTransactions();

        assert response.getStatusCode().equals(HttpStatus.OK);
        assert Objects.requireNonNull(response.getBody()).size() == 2;
    }
}
