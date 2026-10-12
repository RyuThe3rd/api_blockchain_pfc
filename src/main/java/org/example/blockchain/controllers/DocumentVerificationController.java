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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentVerificationController {

    private final StatementRegistryService statementRegistryService;

    /**
     * DTO simplificado para ancoragem de extrato
     */
    @Data
    public static class AnchorStatementRequestDTO {
        @NotBlank(message = "O hash do documento e obrigatorio")
        @Size(min = 64, max = 66, message = "O hash SHA-256 deve ter 64 caracteres hexadecimais")
        private String documentHash;
    }

    /**
     * Ancorar o hash SHA-256 de um extrato na blockchain.
     * POST /api/v1/documents/anchor
     */
    @PostMapping("/anchor")
    public ResponseEntity<?> anchorDocument(@Valid @RequestBody AnchorStatementRequestDTO request) {
        try {
            String txHash = statementRegistryService.anchorStatement(request.getDocumentHash());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Extrato bancario ancorado com sucesso na blockchain");
            response.put("documentHash", request.getDocumentHash());
            response.put("transactionHash", txHash);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validacao ao ancorar: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Erro ao ancorar extrato na blockchain", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Falha ao processar transacao on-chain: " + e.getMessage()));
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