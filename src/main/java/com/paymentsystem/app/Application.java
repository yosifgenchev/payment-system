package com.paymentsystem.app;

import com.paymentsystem.validation.TransactionsValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
        v.addValidator("beforeCreate", new TransactionsValidator());
    }

}
