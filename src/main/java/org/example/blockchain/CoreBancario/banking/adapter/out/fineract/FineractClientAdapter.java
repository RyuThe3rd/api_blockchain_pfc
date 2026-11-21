package org.example.blockchain.CoreBancario.banking.adapter.out.fineract;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.port.out.ClientManagementPort;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FineractClientAdapter implements ClientManagementPort {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
    private final FineractHttpClient http;

    @Override
    public JsonNode createClient(String firstname, String lastname, LocalDate activationDate) {
        return http.post("/clients", Map.of(
            "officeId", 1, "firstname", firstname, "lastname", lastname,
            "active", true, "activationDate", activationDate.format(DATE),
            "locale", "en", "dateFormat", "dd MMMM yyyy"));
    }

    @Override
    public JsonNode listClients() {
        return http.get("/clients?offset=0&limit=100");
    }
}
