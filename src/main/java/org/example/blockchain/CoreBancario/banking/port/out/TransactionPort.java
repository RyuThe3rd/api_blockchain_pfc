package org.example.blockchain.CoreBancario.banking.port.out;

import tools.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Registo de depósitos e levantamentos no core. */
public interface TransactionPort {
    JsonNode post(long accountId, String operation, BigDecimal amount, LocalDate date);
}
