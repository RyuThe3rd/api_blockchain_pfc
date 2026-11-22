package org.example.blockchain.CoreBancario.banking.adapter.out.fineract;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.JsonNode;

/** HTTP, Basic Auth e cabeçalho de tenant do Fineract; sem regras bancárias. */
@Component
public class FineractHttpClient {
    private final RestClient http;

    public FineractHttpClient(RestClient.Builder builder,
            @Value("${core.bancario.url:}") String baseUrl,
            @Value("${CORE_BANCARIO_USERNAME:mifos}") String username,
            @Value("${CORE_BANCARIO_PASSWORD:password}") String password,
            @Value("${CORE_BANCARIO_TENANT:default}") String tenant) {
        if (baseUrl == null || baseUrl.isBlank()) {
            // Não impedir que as rotas legadas de certificação iniciem sem Fineract.
            baseUrl = "https://localhost:8443";
        }
        String host = baseUrl.replaceAll("/+$", "");
        this.http = builder.baseUrl(host + "/fineract-provider/api/v1")
            .defaultHeaders(headers -> {
                // Credenciais são mantidas exclusivamente no servidor.
                if (!password.isBlank()) headers.setBasicAuth(username, password);
                headers.set("Fineract-Platform-TenantId", tenant);
            })
            .build();
    }

    public JsonNode get(String path) {
        try {
            JsonNode result = http.get().uri(path).retrieve().body(JsonNode.class);
            if (result == null) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Resposta vazia do Fineract");
            return result;
        } catch (RestClientResponseException ex) {
            throw upstreamError(ex);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Falha de ligação ao Fineract", ex);
        }
    }

    public JsonNode post(String path, Object request) {
        try {
            JsonNode result = http.post().uri(path).contentType(MediaType.APPLICATION_JSON)
                .body(request).retrieve().body(JsonNode.class);
            if (result == null) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Resposta vazia do Fineract");
            return result;
        } catch (RestClientResponseException ex) {
            throw upstreamError(ex);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Falha de ligação ao Fineract", ex);
        }
    }

    private ResponseStatusException upstreamError(RestClientResponseException ex) {
        if (ex.getStatusCode().value() == 404) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado no Fineract", ex);
        }
        return new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "O Fineract recusou a operação (HTTP " + ex.getStatusCode().value() + ")", ex);
    }
}
