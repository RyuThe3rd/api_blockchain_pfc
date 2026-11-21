package org.example.blockchain.CoreBancario.banking.port.out;

import tools.jackson.databind.JsonNode;
import java.time.LocalDate;

/** Operações sobre clientes exigidas pela aplicação. */
public interface ClientManagementPort {
    JsonNode createClient(String firstname, String lastname, LocalDate activationDate);
    JsonNode listClients();
}
