package org.example.blockchain.CoreBancario.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.OpenAccountRequest;
import org.example.blockchain.CoreBancario.banking.model.BankAccount;
import org.example.blockchain.CoreBancario.banking.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banking/accounts")
public class BankingAccountController {
    private final AccountService accounts;

    @GetMapping("/products")
    public JsonNode products() { return accounts.products(); }

    @PostMapping("/products/demo")
    public JsonNode createDemoProduct() { return accounts.createDemoProduct(); }

    @GetMapping("/lookup")
    public Map<String, Object> lookup(@RequestParam String accountNo) {
        BankAccount account = accounts.findByNumber(accountNo);
        return Map.of("accountId", account.id(), "accountNo", account.accountNo(),
            "clientId", account.clientId(), "clientName", account.clientName(),
            "currency", account.currency(), "active", account.active());
    }

    @GetMapping("/{accountId}")
    public BankAccount get(@PathVariable long accountId) {
        return accounts.findById(accountId);
    }

    @PostMapping
    public BankAccount open(@Valid @RequestBody OpenAccountRequest request) {
        return accounts.open(request);
    }
}
