package com.paymentsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class MerchantTest {

    @Test
    public void testUpdateTotalTransactionSum() {
        Merchant merchant = new Merchant();

        Transaction chargeTransaction = new ChargeTransaction();
        chargeTransaction.setAmount(BigDecimal.valueOf(100));
        chargeTransaction.setStatus("approved");

        Transaction refundTransaction = new RefundTransaction();
        refundTransaction.setAmount(BigDecimal.valueOf(50));
        refundTransaction.setStatus("approved");

        Set<Transaction> transactions = new HashSet<>();
        transactions.add(chargeTransaction);
        transactions.add(refundTransaction);

        merchant.setTransactions(transactions);

        merchant.updateTotalTransactionSum();

        Assertions.assertEquals(BigDecimal.valueOf(50), merchant.getTotalTransactionSum());
    }

    @Test
    public void testGetChargeTransactionsAmount() {
        Merchant merchant = new Merchant();

        Transaction approvedChargeTransaction = new ChargeTransaction();
        approvedChargeTransaction.setAmount(BigDecimal.valueOf(100));
        approvedChargeTransaction.setStatus("approved");

        Transaction errorChargeTransaction = new ChargeTransaction();
        errorChargeTransaction.setAmount(BigDecimal.valueOf(200));
        errorChargeTransaction.setStatus("error");

        Set<Transaction> transactions = new HashSet<>();

        transactions.add(approvedChargeTransaction);
        transactions.add(errorChargeTransaction);

        merchant.setTransactions(transactions);

        BigDecimal chargeTransactionsAmount = merchant.getChargeTransactionsAmount();

        Assertions.assertEquals(BigDecimal.valueOf(100), chargeTransactionsAmount);
    }

    @Test
    public void testGetRefundTransactionsAmount() {
        Merchant merchant = new Merchant();

        Transaction approvedRefundTransaction = new RefundTransaction();
        approvedRefundTransaction.setAmount(BigDecimal.valueOf(50));
        approvedRefundTransaction.setStatus("approved");

        Transaction errorRefundTransaction = new RefundTransaction();
        errorRefundTransaction.setAmount(BigDecimal.valueOf(75));
        errorRefundTransaction.setStatus("error");

        Set<Transaction> transactions = new HashSet<>();
        transactions.add(approvedRefundTransaction);
        transactions.add(errorRefundTransaction);

        merchant.setTransactions(transactions);

        BigDecimal refundTransactionsAmount = merchant.getRefundTransactionsAmount();

        Assertions.assertEquals(BigDecimal.valueOf(50), refundTransactionsAmount);
    }

    @Test
    public void testGetTransactionsAmount() {
        Merchant merchant = new Merchant();

        Transaction transaction1 = new ChargeTransaction();
        transaction1.setAmount(BigDecimal.valueOf(50));

        Transaction transaction2 = new ChargeTransaction();
        transaction2.setAmount(BigDecimal.valueOf(75));

        Set<Transaction> transactions = new HashSet<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        BigDecimal transactionsAmount = merchant.getTransactionsAmount(transactions);

        Assertions.assertEquals(BigDecimal.valueOf(125), transactionsAmount);
    }
}


