package org.example.blockchain.CoreBancario.banking.port.out;

import tools.jackson.databind.JsonNode;
import java.time.LocalDate;

/** Operações necessárias à abertura de uma conta. */
public interface AccountCommandPort {
    JsonNode listSavingsProducts();
    JsonNode createDemoSavingsProduct();
    long submit(long clientId, long productId, LocalDate date);
    void approve(long accountId, LocalDate date);
    void activate(long accountId, LocalDate date);
}
