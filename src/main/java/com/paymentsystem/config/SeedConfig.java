package com.paymentsystem.config;

import com.opencsv.bean.CsvToBeanBuilder;
import com.paymentsystem.csv.AdminCsvBean;
import com.paymentsystem.csv.MerchantCsvBean;
import com.paymentsystem.csv.UsernameCsvBean;
import com.paymentsystem.model.Merchant;
import com.paymentsystem.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SeedConfig {

    private final MerchantService merchantService;

    @Bean
    public void initMerchants() {
        List<MerchantCsvBean> merchantCsvBeans = getMerchantCsvBeans();

        List<Merchant> merchants = merchantCsvBeans.stream().map(this::convertMerchantCsvBeanToMerchant).toList();

        merchants.forEach(merchantService::save);
    }

    @Bean
    public UserDetailsService initUserDetails() {
        List<MerchantCsvBean> merchantCsvBeans = getMerchantCsvBeans();
        List<AdminCsvBean> adminCsvBeans = getAdminCsvBeans();

        List<UserDetails> merchantUsers = merchantCsvBeans.stream().map(this::convertMerchantCsvBeanToUserDetails).toList();
        List<UserDetails> adminUsers = adminCsvBeans.stream().map(this::convertAdminCsvBeanToUserDetails).toList();

        List<UserDetails> allUsers = new ArrayList<>();
        allUsers.addAll(merchantUsers);
        allUsers.addAll(adminUsers);

        return new InMemoryUserDetailsManager(allUsers);
    }

    private Merchant convertMerchantCsvBeanToMerchant(MerchantCsvBean merchantCsvBean) {
        Merchant merchant = new Merchant();
        merchant.setName(merchantCsvBean.getUsername());
        merchant.setDescription(merchantCsvBean.getDescription());
        merchant.setEmail(merchantCsvBean.getEmail());
        merchant.setStatus(Merchant.Status.valueOf(merchantCsvBean.getStatus()));
        return merchant;
    }

    private UserDetails convertMerchantCsvBeanToUserDetails(UsernameCsvBean usernameCsvBean) {
        return convertUsernameCsvBeanToUserDetails(usernameCsvBean, "MERCHANT");

    }

    private UserDetails convertAdminCsvBeanToUserDetails(AdminCsvBean adminCsvBean) {
        return convertUsernameCsvBeanToUserDetails(adminCsvBean, "ADMIN");
    }

    private UserDetails convertUsernameCsvBeanToUserDetails(UsernameCsvBean usernameCsvBean, String role) {
        return User.withDefaultPasswordEncoder()
                .username(usernameCsvBean.getUsername())
                .password(usernameCsvBean.getPassword())
                .roles(role)
                .build();
    }

    private List<MerchantCsvBean> getMerchantCsvBeans() {
        InputStream in = getClass().getResourceAsStream("/merchants.csv");
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        return new CsvToBeanBuilder(reader)
                .withType(MerchantCsvBean.class).build().parse();
    }

    private List<AdminCsvBean> getAdminCsvBeans() {
        InputStream in = getClass().getResourceAsStream("/admins.csv");
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        return new CsvToBeanBuilder(reader)
                .withType(AdminCsvBean.class).build().parse();
    }
}
