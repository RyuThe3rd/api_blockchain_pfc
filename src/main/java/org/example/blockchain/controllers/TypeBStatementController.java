
package org.example.blockchain.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.blockchain.dto.TypeBAnchorRequestDTO;
import org.example.blockchain.service.TypeBStatementService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/documents/anchor")
@RequiredArgsConstructor
public class TypeBStatementController {

    private final TypeBStatementService typeBStatementService;

    /**
     * Emite e ancora um extracto bancário do Tipo B.
     *
     * O Tipo B contém o histórico de transacções
     * e utiliza CBOR + ZLIB para gerar o QR binário.
     *
     * POST /api/v1/documents/anchor/type-b
     */
    @PostMapping("/type-b")
    public ResponseEntity<?> anchorTypeB(
            @Valid @RequestBody TypeBAnchorRequestDTO request
    ) {
        try {
            Map<String, Object> response = typeBStatementService.anchor(request);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Pedido inválido para emissão Tipo B: {}",
                    e.getMessage()
            );

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error", "Dados do extracto inválidos."
                    ));

        } catch (Exception e) {
            log.error("Falha na emissão ou ancoragem do extracto Tipo B", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error",
                            "Não foi possível emitir e ancorar o extracto Tipo B."
                    ));
        }
    }
}
