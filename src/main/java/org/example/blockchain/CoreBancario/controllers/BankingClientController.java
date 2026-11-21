package org.example.blockchain.CoreBancario.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.CreateClientRequest;
import org.example.blockchain.CoreBancario.banking.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banking/clients")
public class BankingClientController {
    private final ClientService clients;

    @GetMapping
    public JsonNode list() { return clients.list(); }

    @PostMapping
    public JsonNode create(@Valid @RequestBody CreateClientRequest request) {
        return clients.create(request);
    }
}
