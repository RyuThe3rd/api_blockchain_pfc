package org.example.blockchain.CoreBancario.banking.service;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.PostMovementRequest;
import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.banking.port.out.AccountQueryPort;
import org.example.blockchain.CoreBancario.banking.port.out.TransactionPort;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountQueryPort accounts;
    private final TransactionPort transactions;

    public JsonNode post(long accountId, PostMovementRequest request) {
        if (!accounts.findById(accountId).active()) {
            throw new IllegalArgumentException("A conta deve estar activa para registar movimentos");
        }
        return transactions.post(accountId, request.operation(), request.amount(), request.date());
    }

    public List<BankTransaction> list(long accountId) {
        return accounts.findById(accountId).transactions();
    }
}
