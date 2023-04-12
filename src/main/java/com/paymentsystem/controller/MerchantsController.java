package com.paymentsystem.controller;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MerchantsController {

    private final MerchantService merchantService;

    @GetMapping(value = "/merchants")
    public String index(Model model) {
        model.addAttribute("merchants", merchantService.findAll());
        return "merchants";
    }

    @GetMapping("/merchants/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        Merchant merchant = merchantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid merchant Id:" + id));
        if (merchant.getTransactions().isEmpty()) {
            merchantService.delete(merchant);
        } else {
            //TODO Notify that we are not able to delete this merchant because of related transactions
        }
        return "redirect:/merchants";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Merchant merchant = merchantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("merchant", merchant);
        return "update-merchant";
    }
    @PostMapping("/update/{id}")
    public String updateMerchant(@PathVariable("id") long id, @Valid Merchant updatedMerchant,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            updatedMerchant.setId(id);
            return "update-merchant";
        }

        merchantService.updateMerchant(id, updatedMerchant);

        return "redirect:/merchants";
    }

}
