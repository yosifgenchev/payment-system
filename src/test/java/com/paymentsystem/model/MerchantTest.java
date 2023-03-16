package com.paymentsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class MerchantTest {

    private Merchant merchant;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        merchant = new Merchant();
        merchant.setName("Test Merchant");
        merchant.setDescription("Test description");
        merchant.setEmail("test@example.com");
        merchant.setStatus(Merchant.Status.active);

        transaction = new Transaction();
        transaction.setAmount(100.0);

        Set<Transaction> transactions = new HashSet<>();
        transactions.add(transaction);
        merchant.setTransactions(transactions);
    }

    @Test
    void testAddTransaction() {
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(50.0);

        merchant.addTransaction(newTransaction);

        Assertions.assertTrue(merchant.getTransactions().contains(newTransaction));
        Assertions.assertEquals(150.0, merchant.getTotalTransactionSum());
    }

    @Test
    void testRemoveTransaction() {
        merchant.removeTransaction(transaction);

        Assertions.assertFalse(merchant.getTransactions().contains(transaction));
        Assertions.assertEquals(0.0, merchant.getTotalTransactionSum());
    }

    @Test
    void testChangeTotalTransactionSum() {
        double initialSum = merchant.getTotalTransactionSum();

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(20.0);

        merchant.addTransaction(newTransaction);

        Assertions.assertNotEquals(initialSum, merchant.getTotalTransactionSum());
    }

}

