package org.example.blockchain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnchorStatementRequestDTO {

    @NotBlank(message = "A chave privada do banco é obrigatória para assinatura")
    private String privateKey;

    @NotBlank(message = "O nome do banco é obrigatório")
    private String banco; // ex: "BCI"

    @NotBlank(message = "O nome do titular é obrigatório")
    private String titular; // ex: "Rui Manuel de Noronha Matomate"

    @NotBlank(message = "O hash da conta é obrigatório")
    private String contaHash; // ex: SHA-256 do NIB/Conta

    @NotBlank(message = "O período é obrigatório")
    private String periodo; // ex: "2026-01-01 a 2026-06-30"

    @NotBlank(message = "O saldo final é obrigatório")
    private String saldoFinal; // ex: "500000.00 MZN"

    private String transactionsHash; // Opcional (para Extratos Detalhados - Tipo A)
}