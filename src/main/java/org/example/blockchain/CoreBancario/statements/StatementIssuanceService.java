package org.example.blockchain.CoreBancario.statements;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.IssueStatementRequest;
import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.service.TypeAStatementService;
import org.example.blockchain.service.TypeBStatementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

/** Orquestra a emissão integrada sem duplicar o protocolo Tipo A/B. */
@Service
@RequiredArgsConstructor
public class StatementIssuanceService {
    private final StatementDataService dataService;
    private final StatementCertificationMapper mapper;
    private final TypeAStatementService typeAService;
    private final TypeBStatementService typeBService;

    @Value("${CORE_BANCARIO_BANK_NAME:Banco de Testes}")
    private String bankName;

    @Value("${CORE_BANCARIO_SIGNING_PRIVATE_KEY:}")
    private String signingKey;

    public Map<String, Object> issue(IssueStatementRequest request) throws Exception {
        if (request.type() == null || !("A".equals(request.type()) || "B".equals(request.type()))) {
            throw new IllegalArgumentException("O tipo deve ser A ou B");
        }
        if (signingKey.isBlank()) {
            throw new IllegalStateException("CORE_BANCARIO_SIGNING_PRIVATE_KEY não configurada no servidor");
        }
        StatementData data = dataService.prepare(request);
        Map<String, Object> certified = "A".equals(request.type())
            ? typeAService.anchor(mapper.toTypeA(data, bankName, signingKey))
            : typeBService.anchor(mapper.toTypeB(data, bankName, signingKey));
        Map<String, Object> response = new LinkedHashMap<>(certified);
        response.put("accountNo", data.accountNo());
        response.put("titular", data.holder());
        response.put("currency", data.currency());
        response.put("periodo", data.startDate() + " a " + data.endDate());
        response.put("saldoFinal", data.closingBalance().toPlainString());
        response.put("type", request.type());
        response.put("movimentos", data.movements().stream().map(this::movement).toList());
        return response;
    }

    private Map<String, Object> movement(BankTransaction tx) {
        return Map.of("id", tx.id(), "data", tx.date().toString(),
            "valor", tx.amount().toPlainString(), "tipo", tx.direction(),
            "descricao", tx.description());
    }
}
