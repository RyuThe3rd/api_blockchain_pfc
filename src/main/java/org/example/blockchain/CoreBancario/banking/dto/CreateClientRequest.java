package org.example.blockchain.CoreBancario.banking.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record CreateClientRequest(@NotBlank String firstname, @NotBlank String lastname, LocalDate activationDate) { }
