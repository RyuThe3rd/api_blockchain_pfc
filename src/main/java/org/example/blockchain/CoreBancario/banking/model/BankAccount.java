package org.example.blockchain.CoreBancario.banking.model;

import java.math.BigDecimal;
import java.util.List;

/** Representação de uma conta de poupança independentemente do core. */
public record BankAccount(long id, String accountNo, long clientId, String clientName,
                          String currency, boolean active, BigDecimal currentBalance,
                          List<BankTransaction> transactions) { }
