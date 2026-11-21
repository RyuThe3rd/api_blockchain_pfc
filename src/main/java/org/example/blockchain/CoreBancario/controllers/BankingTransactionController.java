package org.example.blockchain.CoreBancario.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.PostMovementRequest;
import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.banking.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banking/accounts/{accountId}/transactions")
public class BankingTransactionController {
    private final TransactionService transactions;

    @GetMapping
    public List<BankTransaction> list(@PathVariable long accountId) {
        return transactions.list(accountId);
    }

    @PostMapping
    public JsonNode post(@PathVariable long accountId,
                         @Valid @RequestBody PostMovementRequest request) {
        return transactions.post(accountId, request);
    }
}
