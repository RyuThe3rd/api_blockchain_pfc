package org.example.blockchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnchorStatementRequestDTO {

    @NotBlank(message = "O hash do documento e obrigatorio")
    @Size(min = 64, max = 66, message = "O hash SHA-256 deve ter 64 caracteres hexadecimais")
    private String documentHash;
}