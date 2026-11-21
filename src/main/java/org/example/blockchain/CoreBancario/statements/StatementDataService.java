package org.example.blockchain.CoreBancario.statements;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.IssueStatementRequest;
import org.example.blockchain.CoreBancario.banking.model.BankAccount;
import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.banking.port.out.AccountQueryPort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/** Obtém movimentos e determina o saldo contábil à data final, não o saldo actual. */
@Service
@RequiredArgsConstructor
public class StatementDataService {
    private final AccountQueryPort accounts;

    public StatementData prepare(IssueStatementRequest request) {
        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à final");
        }
        BankAccount account = accounts.findByNumber(request.accountNo());
        if (!"MZN".equals(account.currency())) {
            throw new IllegalArgumentException("O protocolo de demonstração suporta apenas contas em MZN");
        }
        List<BankTransaction> ordered = account.transactions().stream()
            .filter(tx -> !tx.reversed())
            .sorted(Comparator.comparing(BankTransaction::date).thenComparingLong(BankTransaction::id))
            .toList();

        BigDecimal closingBalance = BigDecimal.ZERO;
        for (BankTransaction tx : ordered) {
            if (tx.date().isAfter(request.endDate())) break;
            closingBalance = switch (tx.direction()) {
                case "C" -> closingBalance.add(tx.amount());
                case "D" -> closingBalance.subtract(tx.amount());
                default -> throw new IllegalArgumentException(
                    "Tipo de movimento ainda não suportado: " + tx.description());
            };
        }

        List<BankTransaction> periodMovements = ordered.stream()
            .filter(tx -> !tx.date().isBefore(request.startDate()) && !tx.date().isAfter(request.endDate()))
            .toList();

        return new StatementData(account.accountNo(), account.clientName(), account.currency(),
            request.startDate(), request.endDate(), closingBalance, periodMovements);
    }
}
