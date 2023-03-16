package com.paymentsystem.controller;

import com.paymentsystem.dto.MerchantDTO;
import com.paymentsystem.dto.TransactionDTO;
import com.paymentsystem.model.Merchant;
import com.paymentsystem.model.Transactable;
import com.paymentsystem.model.Transaction;
import com.paymentsystem.service.MerchantService;
import com.paymentsystem.service.TransactableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactableService transactableService;

    private final MerchantService merchantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        Merchant merchant = merchantService.findById(transactionDTO.getMerchant_id());

        if (merchant.getStatus() != Merchant.Status.active || transactionDTO.getDtype() == null) {
            return ResponseEntity.badRequest().build();
        }

        Transaction t = transactableService.getTransactionByReferredTransactionUUID(transactionDTO.getReferred_transaction_uuid());

        if (t == null && !transactionDTO.getDtype().equals("AuthorizeTransaction")) {
            return ResponseEntity.badRequest().build();
        }

        Transactable transactable = transactableService.convertTransactableDTOtoTransactable(transactionDTO, t);

        if (transactable == null) {
            return ResponseEntity.badRequest().build();
        }

        merchant.addTransaction((Transaction) transactable);

        merchantService.save(merchant);

        return ResponseEntity.ok().build();
    }

    @RequestMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(transactableService.findAll());
    }
}
