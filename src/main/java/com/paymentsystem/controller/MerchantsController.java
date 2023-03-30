package com.paymentsystem.controller;

import com.paymentsystem.model.Merchant;
import com.paymentsystem.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MerchantsController {

    private final MerchantRepository merchantRepository;

    @RequestMapping(value = "/merchants")
    public String index(Model model) {
        model.addAttribute("merchants", merchantRepository.findAll());
        return "merchants";
    }

    @RequestMapping("/merchants/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid merchant Id:" + id));
        if (merchant.getTransactions().isEmpty()) {
            merchantRepository.delete(merchant);
        } else {
            //TODO Notify that we are not able to delete this merchant because of related transactions
        }
        return "redirect:/merchants";
    }

}
