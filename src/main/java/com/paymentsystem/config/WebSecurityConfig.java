package com.paymentsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO Manage security based on roles
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/transactions").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic()
                .and()
                .csrf().disable();
        return http.build();
    }

}
