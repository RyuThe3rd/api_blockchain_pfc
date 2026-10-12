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
public class RegisterBankRequestDTO {

    @NotNull(message = "O ID do banco e obrigatorio")
    private Long bankId;

    @NotBlank(message = "O nome da instituicao e obrigatorio")
    private String name;

    @NotBlank(message = "A chave Mestre (Management Key) e obrigatoria")
    private String managementKey;

    @NotBlank(message = "A chave Operacional (Operational Key) e obrigatoria")
    private String operationalKey;

    @NotNull(message = "A duracao da validade em segundos e obrigatoria")
    private Long durationInSeconds;
}