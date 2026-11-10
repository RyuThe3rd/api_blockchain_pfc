package org.example.blockchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TypeBTransactionDTO {

    @NotBlank(message = "A data da transacção é obrigatória")
    private String data;

    @NotNull(message = "A parte inteira é obrigatória")
    @Min(value = 0)
    private Long parteInteiraDoValor;

    @NotNull(message = "A parte decimal é obrigatória")
    @Min(value = 0)
    @Max(value = 99)
    private Integer parteDeCentavosDoValor;

    // D = Débito; C = Crédito
    @NotBlank
    @Pattern(regexp = "D|C")
    private String tipoMovimento;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 250)
    private String descricao;
}
