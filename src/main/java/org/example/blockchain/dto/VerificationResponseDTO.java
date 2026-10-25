package org.example.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resultado da consulta on-chain. A assinatura ECDSA do QR e verificada pela PWA.
 * ANCHOR_VALID nao significa documento inteiramente verificado, só significa que um extrato foi ancorado.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationResponseDTO {
    private boolean exists;
    private Long bankId;
    private String chaveOperacional;
    private Long timestamp;
    private boolean historicalKeyValid;
    private boolean affectedByCompromise;
    private String status;  // NOT_FOUND | INVALID_KEY | UNDER_REVIEW | ANCHOR_VALID
    private String message;
}
