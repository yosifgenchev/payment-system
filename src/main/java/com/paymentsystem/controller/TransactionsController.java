package com.paymentsystem.controller;

import com.paymentsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionService transactionService;

    @GetMapping("/transactions")
    public String index(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        return "transactions";
    }
}
