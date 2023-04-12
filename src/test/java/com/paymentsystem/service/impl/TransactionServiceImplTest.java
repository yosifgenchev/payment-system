package com.paymentsystem.service.impl;

import com.paymentsystem.model.AuthorizeTransaction;
import com.paymentsystem.model.ChargeTransaction;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.repository.TransactionRepository;
import com.paymentsystem.service.TransactableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TransactableServiceImplTest {

    TransactableService transactableService;

    @Mock
    TransactionRepository transactionRepositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactableService = new TransactableServiceImpl(transactionRepositoryMock);
    }

    @Test
    void testFindTransactionByUuid() {
        String uuid = "1234";
        Transaction transactionMock = new AuthorizeTransaction();
        when(transactionRepositoryMock.findTransactionByUuid(uuid)).thenReturn(Optional.of(transactionMock));

        Transaction transaction = transactableService.findTransactionByUuid(uuid);

        assertNotNull(transaction);
        assertEquals(transactionMock, transaction);
    }

    @Test
    void testFindAll() {
        Transaction transaction1 = new AuthorizeTransaction();
        Transaction transaction2 = new ChargeTransaction();
        when(transactionRepositoryMock.findAll()).thenReturn(List.of(transaction1, transaction2));

        List<Transaction> transactions = transactableService.findAll();

        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(transaction1));
        assertTrue(transactions.contains(transaction2));
    }
}
