package org.example.blockchain.CoreBancario.statements;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.IssueStatementRequest;
import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.statements.pdf.StatementPdfService;
import org.example.blockchain.service.TypeAStatementService;
import org.example.blockchain.service.TypeBStatementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/** Reutiliza o protocolo de certificacao e gera o PDF da mesma emissao, sem reancorar. */
@Service
@RequiredArgsConstructor
public class StatementIssuanceService {
    private final StatementDataService dataService;
    private final StatementCertificationMapper mapper;
    private final TypeAStatementService typeAService;
    private final TypeBStatementService typeBService;
    private final StatementPdfService pdfService;

    @Value("${CORE_BANCARIO_BANK_NAME:Banco de Testes}")
    private String bankName;

    @Value("${CORE_BANCARIO_SIGNING_PRIVATE_KEY:}")
    private String signingKey;

    public Map<String, Object> issue(IssueStatementRequest request) throws Exception {
        if (request.type() == null || !("A".equals(request.type()) || "B".equals(request.type()))) {
            throw new IllegalArgumentException("O tipo deve ser A ou B");
        }
        if (signingKey.isBlank()) {
            throw new IllegalStateException("CORE_BANCARIO_SIGNING_PRIVATE_KEY nao configurada no servidor");
        }
        StatementData data = dataService.prepare(request);
        Map<String, Object> certified = "A".equals(request.type())
            ? typeAService.anchor(mapper.toTypeA(data, bankName, signingKey))
            : typeBService.anchor(mapper.toTypeB(data, bankName, signingKey));

        // Somente UMA certificacao. O PDF usa o envelope CBOR assinado devolvido acima.
        byte[] pdf = pdfService.generate(data, request.type(), bankName, certified);
        Map<String, Object> response = new LinkedHashMap<>(certified);
        response.put("accountNo", data.accountNo());
        response.put("titular", data.holder());
        response.put("currency", data.currency());
        response.put("periodo", data.startDate() + " a " + data.endDate());
        response.put("saldoFinal", data.closingBalance().toPlainString());
        response.put("type", request.type());
        response.put("movimentos", data.movements().stream().map(this::movement).toList());
        response.put("pdfBase64", Base64.getEncoder().encodeToString(pdf));
        response.put("pdfFileName", fileName(request.type(), data.accountNo()));
        return response;
    }

    private static String fileName(String type, String accountNo) {
        // Evita caracteres de path/header vindos do numero de conta.
        return "extrato_tipo_" + type.toLowerCase() + "_" +
            accountNo.replaceAll("[^a-zA-Z0-9_-]", "_") + ".pdf";
    }

    private Map<String, Object> movement(BankTransaction tx) {
        return Map.of("id", tx.id(), "data", tx.date().toString(),
            "valor", tx.amount().toPlainString(), "tipo", tx.direction(),
            "descricao", tx.description());
    }
}
