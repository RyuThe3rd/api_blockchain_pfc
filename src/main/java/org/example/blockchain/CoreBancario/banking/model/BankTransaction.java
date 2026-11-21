package org.example.blockchain.CoreBancario.banking.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Movimento do core normalizado, sem dependência dos DTOs do Fineract. */
public record BankTransaction(long id, LocalDate date, BigDecimal amount,
                              String direction, String description, boolean reversed) { }
