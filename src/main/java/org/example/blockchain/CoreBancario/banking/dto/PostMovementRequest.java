package org.example.blockchain.CoreBancario.banking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PostMovementRequest(
    @NotBlank @Pattern(regexp = "deposit|withdrawal", message = "Usar deposit ou withdrawal") String operation,
    @NotNull @DecimalMin("0.01") BigDecimal amount,
    @NotNull LocalDate date) { }
