package org.example.blockchain.CoreBancario.banking.service;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.CreateClientRequest;
import org.example.blockchain.CoreBancario.banking.port.out.ClientManagementPort;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientManagementPort clients;

    public JsonNode create(CreateClientRequest request) {
        return clients.createClient(request.firstname().trim(), request.lastname().trim(),
            request.activationDate() == null ? LocalDate.now() : request.activationDate());
    }

    public JsonNode list() {
        return clients.listClients();
    }
}
