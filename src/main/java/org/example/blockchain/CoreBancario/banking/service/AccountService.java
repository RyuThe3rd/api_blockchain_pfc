package org.example.blockchain.CoreBancario.banking.service;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.OpenAccountRequest;
import org.example.blockchain.CoreBancario.banking.model.BankAccount;
import org.example.blockchain.CoreBancario.banking.port.out.AccountCommandPort;
import org.example.blockchain.CoreBancario.banking.port.out.AccountQueryPort;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import java.time.LocalDate;

/** Coordena a abertura; não executa HTTP. */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountCommandPort commands;
    private final AccountQueryPort queries;

    public JsonNode products() {
        return commands.listSavingsProducts();
    }

    public JsonNode createDemoProduct() {
        return commands.createDemoSavingsProduct();
    }

    public BankAccount findByNumber(String number) {
        return queries.findByNumber(number);
    }

    public BankAccount findById(long accountId) {
        return queries.findById(accountId);
    }

    public BankAccount open(OpenAccountRequest request) {
        LocalDate date = request.openedOnDate() == null ? LocalDate.now() : request.openedOnDate();
        long accountId = commands.submit(request.clientId(), request.productId(), date);
        // Estas operações não são atómicas: se uma falhar, a conta já existe no Fineract.
        // Em laboratório devolvemos o erro ao chamador para diagnosticar o estado.
        commands.approve(accountId, date);
        commands.activate(accountId, date);
        return queries.findById(accountId);
    }
}
