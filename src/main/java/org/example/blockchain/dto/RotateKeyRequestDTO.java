package org.example.blockchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RotateKeyRequestDTO {

    @NotNull(message = "O ID do banco e obrigatorio")
    private Long bankId;

    @NotBlank(message = "A nova chave operacional e obrigatoria")
    private String newOperationalKey;

    @NotNull(message = "A duracao da nova validade em segundos e obrigatoria")
    private Long durationInSeconds;
}