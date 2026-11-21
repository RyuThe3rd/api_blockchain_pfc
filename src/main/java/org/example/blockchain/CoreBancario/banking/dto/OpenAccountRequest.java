package org.example.blockchain.CoreBancario.banking.dto;

import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record OpenAccountRequest(@Positive long clientId, @Positive long productId, LocalDate openedOnDate) { }
