package org.example.blockchain.CoreBancario.banking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record IssueStatementRequest(
    @NotBlank String accountNo,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate,
    @NotBlank @Pattern(regexp = "A|B", message = "O tipo deve ser A ou B") String type) { }
