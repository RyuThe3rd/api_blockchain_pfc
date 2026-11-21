package org.example.blockchain.CoreBancario.statements;

import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Snapshot normalizado do extracto a certificar, sem dependência do Fineract. */
public record StatementData(String accountNo, String holder, String currency,
                            LocalDate startDate, LocalDate endDate,
                            BigDecimal closingBalance, List<BankTransaction> movements) { }
