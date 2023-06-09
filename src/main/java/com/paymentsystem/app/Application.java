package com.paymentsystem.app;

import com.paymentsystem.handlers.TransactionEventHandler;
import com.paymentsystem.validation.AuthorizeTransactionValidator;
import com.paymentsystem.validation.ChargeTransactionValidator;
import com.paymentsystem.validation.MerchantDeletionValidator;
import com.paymentsystem.validation.RefundTransactionValidator;
import com.paymentsystem.validation.ReversalTransactionValidator;
import com.paymentsystem.validation.TransactionValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication(scanBasePackages = {"com.paymentsystem", "com.paymentsystem.repository"})
@EnableJpaRepositories("com.paymentsystem.repository")
@EntityScan("com.paymentsystem.*")
public class Application implements RepositoryRestConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configureValidatingRepositoryEventListener(
            ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", new TransactionValidator());
        v.addValidator("beforeCreate", new AuthorizeTransactionValidator());
        v.addValidator("beforeCreate", new ChargeTransactionValidator());
        v.addValidator("beforeCreate", new RefundTransactionValidator());
        v.addValidator("beforeCreate", new ReversalTransactionValidator());

        v.addValidator("beforeDelete", new MerchantDeletionValidator());
    }

    @Bean
    TransactionEventHandler transactionEventHandler() {
        return new TransactionEventHandler();
    }

}
