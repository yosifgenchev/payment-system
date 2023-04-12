package com.paymentsystem.controller;

import com.paymentsystem.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public String index(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions";
    }
}
