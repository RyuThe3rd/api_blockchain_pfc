package org.example.blockchain.controllers;

import jakarta.validation.Valid;
import org.example.blockchain.dto.AnchorDocumentRequestDTO;
import org.example.blockchain.dto.VerificationResponseDTO;
import org.example.blockchain.models.DocumentProof;
import org.example.blockchain.services.BlockchainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentVerificationController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentVerificationController.class);

    private final BlockchainService blockchainService;

    @Autowired
    public DocumentVerificationController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @PostMapping("/anchor")
    public ResponseEntity<?> anchorDocument(@Valid @RequestBody AnchorDocumentRequestDTO request) {
        try {
            String txHash = blockchainService.anchorDocument(
                    request.getDocumentHash(),
                    request.getBankId(),
                    request.getBankSignature(),
                    request.getTimestamp()
            );
            return ResponseEntity.ok().body("Documento ancorado com sucesso. Transação: " + txHash);
        } catch (SecurityException e) {
            logger.warn("Falha na autenticação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao ancorar documento", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping("/verify/{documentHash}")
    public ResponseEntity<VerificationResponseDTO> verifyDocument(@PathVariable String documentHash) {
        try {
            DocumentProof proof = blockchainService.verifyDocument(documentHash);
            if (proof != null) {
                VerificationResponseDTO response = new VerificationResponseDTO(
                        true,
                        proof.getIssuerAddress(), // ou bankId, mas temos só endereço; podemos mapear
                        proof.getBlockNumber(),
                        proof.getTransactionHash(),
                        proof.getTimestamp()
                );
                return ResponseEntity.ok(response);
            } else {
                // Retorna válido=false com campos nulos
                return ResponseEntity.ok(new VerificationResponseDTO(false, null, null, null, null));
            }
        } catch (Exception e) {
            logger.error("Erro na verificação", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new VerificationResponseDTO(false, null, null, null, null));
        }
    }
}