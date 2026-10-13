package org.example.blockchain.controllers;

import java.math.BigInteger;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.service.BankRegistryService;
import org.example.blockchain.wrappers.BankRegistry.BankSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankRegistryController {

    private final BankRegistryService bankRegistryService;

    // DTOs para requisições de gestão
    @Data
    public static class RegisterBankRequestDTO {
        @NotNull private Long bankId;
        @NotBlank private String name;
        @NotBlank private String managementKey;
        @NotBlank private String operationalKey;
        @NotNull private Long durationInSeconds;
    }

    @Data
    public static class RotateKeyRequestDTO {
        @NotNull private Long bankId;
        @NotBlank private String newOperationalKey;
        @NotNull private Long durationInSeconds;
    }

    /**
     * Retorna todos os bancos cadastrados para popular o Accordion no Frontend.
     * GET /api/v1/banks
     */
    @GetMapping
    public ResponseEntity<List<BankSummaryDTO>> getAllBanks() {
        try {
            List<BankSummary> banks = bankRegistryService.getAllBanks();

            // 2. Mapeia para DTOs limpos (removendo metadados do Web3j)
            List<BankSummaryDTO> banksDto = rawBanks.stream()
                    .map(b -> new BankSummaryDTO(
                            b.bankId,
                            b.name,
                            b.operationalKey,
                            b.isActive,
                            b.expiresAt
                    ))
                    .toList();

            return ResponseEntity.ok(banksDto);
        } catch (Exception e) {
            log.error("Erro ao procurar a lista de bancos", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Regista um novo banco (Admin Consórcio)
     * POST /api/v1/banks/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerBank(@Valid @RequestBody RegisterBankRequestDTO request) {
        try {
            String txHash = bankRegistryService.registerBank(
                    request.getBankId(),
                    request.getName(),
                    request.getManagementKey(),
                    request.getOperationalKey(),
                    request.getDurationInSeconds()
            );
            return ResponseEntity.ok(Map.of("message", "Banco registrado com sucesso", "transactionHash", txHash));
        } catch (Exception e) {
            log.error("Erro ao registrar banco", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Roda a chave operacional do Spring Boot
     * POST /api/v1/banks/rotate-key
     */
    @PostMapping("/rotate-key")
    public ResponseEntity<?> rotateKey(@Valid @RequestBody RotateKeyRequestDTO request) {
        try {
            String txHash = bankRegistryService.rotateOperationalKey(
                    request.getBankId(),
                    request.getNewOperationalKey(),
                    request.getDurationInSeconds()
            );
            return ResponseEntity.ok(Map.of("message", "Chave operacional rodada com sucesso", "transactionHash", txHash));
        } catch (Exception e) {
            log.error("Erro ao rodar chave operacional", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Revoga o acesso de um banco
     * POST /api/v1/banks/revoke/{bankId}
     */
    @PostMapping("/revoke/{bankId}")
    public ResponseEntity<?> revokeBank(@PathVariable Long bankId) {
        try {
            String txHash = bankRegistryService.revokeBank(bankId);
            return ResponseEntity.ok(Map.of("message", "Banco revogado com sucesso", "transactionHash", txHash));
        } catch (Exception e) {
            log.error("Erro ao revogar banco", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint público para a PWA obter a Chave Pública Operacional do banco
     * e validar a assinatura digital do QR Code offline.
     */
    @GetMapping("/{bankId}/operational-key")
    public ResponseEntity<?> getOperationalKey(@PathVariable BigInteger bankId) {
        try {
            String operationalKey = bankRegistryService.getOperationalKey(bankId);

            return ResponseEntity.ok(Map.of(
                    "bankId", bankId,
                    "operationalKey", operationalKey
            ));
        } catch (Exception e) {
            log.error("Erro ao procurar chave operacional do banco {}", bankId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Banco nao encontrado ou inativo na blockchain."));
        }
    }


}