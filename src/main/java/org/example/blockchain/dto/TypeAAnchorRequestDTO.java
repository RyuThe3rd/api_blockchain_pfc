package org.example.blockchain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** Pedido para novos extractos Tipo A (CBOR, não JSON legado). */
@Data
public class TypeAAnchorRequestDTO {

    @NotBlank(message = "A chave operacional do banco é obrigatória")
    private String privateKey;

    @NotBlank(message = "O banco é obrigatório")
    @Size(max = 120)
    private String banco;

    @NotBlank(message = "O titular é obrigatório")
    @Size(max = 200)
    private String titular;

    @NotBlank(message = "O hash da conta é obrigatório")
    @Size(max = 130)
    private String contaHash;

    @NotBlank(message = "O período é obrigatório")
    @Size(max = 100)
    private String periodo;

    // Igual ao Tipo B / Flutter PWA: duas partes inteiras.
    @NotNull(message = "A parte inteira do saldo é obrigatória")
    private Long parteInteiraDoSaldoFinal;

    @NotNull(message = "A parte de centavos do saldo é obrigatória")
    @Min(value = 0)
    @Max(value = 99)
    private Integer parteDeCentavosDoSaldoFinal;

    // Campo opcional; ausência ou string vazia tornam-se CBOR null.
    @Size(max = 130)
    private String transactionsHash;
}
