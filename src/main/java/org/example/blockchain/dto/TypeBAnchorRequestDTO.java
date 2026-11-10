package org.example.blockchain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TypeBAnchorRequestDTO {

    // Credenciais do banco emissor
    @NotBlank(message = "A chave privada do banco é obrigatória")
    private String privateKey;

    // Dados essenciais do extracto
    @NotBlank(message = "O nome do banco é obrigatório")
    private String banco;

    @NotBlank(message = "O nome do titular é obrigatório")
    private String titular;

    // Saldo representado sem números de ponto flutuante
    @NotNull(message = "A parte inteira do saldo é obrigatória")
    private Long parteInteiraDoSaldoFinal;

    @NotNull(message = "A parte decimal do saldo é obrigatória")
    @Min(value = 0, message = "Os centavos não podem ser negativos")
    @Max(value = 99, message = "Os centavos devem estar entre 0 e 99")
    private Integer parteDeCentavosDoSaldoFinal;

    @NotBlank(message = "O período do extracto é obrigatório")
    private String periodo;

    // Histórico completo das transacções
    @Valid
    @NotEmpty(message = "O extracto deve conter transacções")
    private List<@NotNull TypeBTransactionDTO> transactions;
}
