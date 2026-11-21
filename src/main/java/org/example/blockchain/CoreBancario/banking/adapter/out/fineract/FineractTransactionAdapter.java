package org.example.blockchain.CoreBancario.banking.adapter.out.fineract;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.port.out.TransactionPort;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FineractTransactionAdapter implements TransactionPort {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
    private final FineractHttpClient http;

    @Override
    public JsonNode post(long accountId, String operation, BigDecimal amount, LocalDate date) {
        if (!"deposit".equals(operation) && !"withdrawal".equals(operation)) {
            throw new IllegalArgumentException("A operação deve ser deposit ou withdrawal");
        }
        return http.post("/savingsaccounts/" + accountId + "/transactions?command=" + operation,
            Map.of("transactionDate", date.format(DATE), "transactionAmount", amount,
                   "dateFormat", "dd MMMM yyyy", "locale", "en"));
    }
}
