package com.paymentsystem.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.paymentsystem", "com.paymentsystem.repository"})
@EnableJpaRepositories("com.paymentsystem.repository")
@EntityScan("com.paymentsystem.*")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
