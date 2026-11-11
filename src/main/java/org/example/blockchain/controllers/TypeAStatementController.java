package org.example.blockchain.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.dto.TypeAAnchorRequestDTO;
import org.example.blockchain.service.TypeAStatementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** Novo endpoint CBOR; não modifica /anchor (JSON legado). */
@Slf4j
@RestController
@RequestMapping("/api/v1/documents/anchor")
@RequiredArgsConstructor
public class TypeAStatementController {

    private final TypeAStatementService typeAStatementService;

    @PostMapping("/type-a")
    public ResponseEntity<?> anchorTypeA(
            @Valid @RequestBody TypeAAnchorRequestDTO request) {
        try {
            return ResponseEntity.ok(typeAStatementService.anchor(request));
        } catch (IllegalArgumentException e) {
            log.warn("Pedido Tipo A CBOR inválido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Dados do extracto Tipo A inválidos."));
        } catch (Exception e) {
            log.error("Falha na emissão ou ancoragem do Tipo A CBOR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Não foi possível emitir e ancorar o extracto Tipo A."));
        }
    }
}
