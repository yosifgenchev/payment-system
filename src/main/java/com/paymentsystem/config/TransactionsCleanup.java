package com.paymentsystem.config;

import com.paymentsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableScheduling
public class TransactionsCleanup {

    private final TransactionService transactionService;

    @Scheduled(cron = "@hourly")
    public void hourlyCleanup() {
        log.info("Transactions older than 1 hour are going to be deleted.");

        log.info("Triggering scheduled transactions cleanup...");

        transactionService.deleteOldTransactions();

        log.info("Scheduled transactions cleanup completed.");
    }
}
