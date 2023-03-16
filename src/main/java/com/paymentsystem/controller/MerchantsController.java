package com.paymentsystem.controller;

import com.paymentsystem.dto.MerchantDTO;
import com.paymentsystem.model.Merchant;
import com.paymentsystem.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantsController {

    private final MerchantService merchantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> create(@RequestBody MerchantDTO merchantDTO) {

        Merchant merchant = merchantService.convertMerchantDTOtoMerchant(merchantDTO);

        merchantService.save(merchant);

        return ResponseEntity.ok().build();
    }

    @RequestMapping
    public ResponseEntity<List<Merchant>> getMerchants() {
        List<Merchant> merchants = merchantService.findAll();

        return ResponseEntity.ok(merchants);
    }
}
