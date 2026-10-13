package org.example.blockchain.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.service.StatementRegistryService;
import org.example.blockchain.service.StatementRegistryService.StatementVerificationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.blockchain.dto.AnchorStatementRequestDTO;
import java.util.HashMap;
import java.util.Map;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentVerificationController {

    private final StatementRegistryService statementRegistryService;



    /**
     * Ancorar o hash SHA-256 de um extrato na blockchain.
     * POST /api/v1/documents/anchor
     */

    @PostMapping("/anchor")
    public ResponseEntity<?> anchorStatement(@Valid @RequestBody AnchorStatementRequestDTO request) {
        try {
            // 1. Instanciar as credenciais a partir da Chave Privada informada pelo Banco
            Credentials bankCredentials = Credentials.create(request.getPrivateKey());
            String signerAddress = bankCredentials.getAddress();

            // 2. Montar o Mapa Ordenado (TreeMap garante ordem determinística das chaves - RFC 8785)
            Map<String, Object> canonicalPayloadMap = new TreeMap<>();
            canonicalPayloadMap.put("banco", request.getBanco());
            canonicalPayloadMap.put("titular", request.getTitular());
            canonicalPayloadMap.put("contaHash", request.getContaHash());
            canonicalPayloadMap.put("periodo", request.getPeriodo());
            canonicalPayloadMap.put("saldoFinal", request.getSaldoFinal());

            // Inclui transactionsHash apenas se fornecido (Payload Tipo A)
            if (request.getTransactionsHash() != null && !request.getTransactionsHash().isBlank()) {
                canonicalPayloadMap.put("transactionsHash", request.getTransactionsHash());
            }

            // Atribui o timestamp exato de emissão ISO-8601 (UTC)
            String issuedAt = Instant.now().toString();
            canonicalPayloadMap.put("issuedAt", issuedAt);

            // 3. Serializar para JSON Canonizado e calcular documentHash = SHA256(JSON)
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.ORDER_MAP_BY_PROPERTIES_SORTED, true);
            String canonicalJson = mapper.writeValueAsString(canonicalPayloadMap);

            String documentHash = Numeric.toHexString(Hash.sha256(canonicalJson.getBytes(StandardCharsets.UTF_8)));

            // 4. Assinar o documentHash via ECDSA usando a Chave Privada do Banco
            String signature = cryptoService.signDocumentHash(documentHash, bankCredentials);

            // 5. Ancorar o documentHash na Blockchain Besu (via Smart Contract StatementRegistry)
            String txHash = statementRegistryService.anchorStatement(documentHash);

            // 6. Adicionar a assinaturaBanco ao Payload Final do QR Code
            canonicalPayloadMap.put("assinaturaBanco", signature);
            String qrCodeData = mapper.writeValueAsString(canonicalPayloadMap);

            // 7. Retornar resposta estruturada para o Core / Frontend
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Extrato bancário ancorado e assinado com sucesso na blockchain");
            response.put("documentHash", documentHash);
            response.put("transactionHash", txHash);
            response.put("signerAddress", signerAddress);
            response.put("assinaturaBanco", signature);
            response.put("issuedAt", issuedAt);
            response.put("payload", canonicalPayloadMap);
            response.put("qrCodeData", qrCodeData);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao ancorar extrato: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Erro ao ancorar extrato na blockchain", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Falha ao processar ancoragem on-chain: " + e.getMessage()));
        }
    }


    /**
     * Verificar a autenticidade de um extrato.
     * GET /api/v1/documents/verify/{documentHash}
     */
    @GetMapping("/verify/{documentHash}")
    public ResponseEntity<?> verifyDocument(@PathVariable String documentHash) {
        try {
            StatementVerificationResult result = statementRegistryService.verifyStatement(documentHash);

            if (!result.isExists()) {
                return ResponseEntity.ok(Map.of(
                        "exists", false,
                        "message", "Extrato nao encontrado ou nao autenticado na blockchain"
                ));
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Erro ao verificar extrato com hash: {}", documentHash, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao consultar a blockchain: " + e.getMessage()));
        }
    }


}