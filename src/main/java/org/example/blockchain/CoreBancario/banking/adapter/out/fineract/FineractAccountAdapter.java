package org.example.blockchain.CoreBancario.banking.adapter.out.fineract;

import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.model.BankAccount;
import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.banking.port.out.AccountCommandPort;
import org.example.blockchain.CoreBancario.banking.port.out.AccountQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FineractAccountAdapter implements AccountQueryPort, AccountCommandPort {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
    private static final int PAGE_SIZE = 100;
    private final FineractHttpClient http;

    @Override
    public BankAccount findByNumber(String accountNo) {
        if (accountNo == null || accountNo.isBlank()) throw new IllegalArgumentException("Número de conta obrigatório");
        // GET /savingsaccounts é paginado; comparação exacta preserva zeros à esquerda.
        // Pesquisa simples para o laboratório, com limite explícito de 10 000 contas.
        for (int offset = 0; offset < 10_000; offset += PAGE_SIZE) {
            JsonNode page = http.get("/savingsaccounts?offset=" + offset + "&limit=" + PAGE_SIZE);
            JsonNode items = page.path("pageItems");
            if (!items.isArray()) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Formato inesperado de contas do Fineract");
            for (JsonNode item : items) {
                if (accountNo.equals(item.path("accountNo").asText())) {
                    return findById(item.path("id").asLong());
                }
            }
            if (items.size() < PAGE_SIZE) break;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada");
    }

    @Override
    public BankAccount findById(long accountId) {
        JsonNode account = http.get("/savingsaccounts/" + accountId + "?associations=transactions");
        List<BankTransaction> transactions = new ArrayList<>();
        JsonNode nodes = account.path("transactions");
        // Fineract pode omitir o campo quando a conta ainda não tem movimentos.
        if (!nodes.isArray() && !nodes.isMissingNode() && !nodes.isNull()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Formato inválido da lista de movimentos");
        }
        if (!nodes.isArray() && readAmount(account.path("summary").path("accountBalance")).signum() != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "O Fineract omitiu movimentos numa conta com saldo não nulo");
        }
        for (JsonNode tx : nodes.isArray() ? nodes : java.util.List.<JsonNode>of()) {
            JsonNode type = tx.path("transactionType");
            if (type.isMissingNode()) type = tx.path("type");
            String direction = type.path("deposit").asBoolean(false) ? "C"
                : type.path("withdrawal").asBoolean(false) ? "D"
                : "";
            if (direction.isEmpty()) {
                String code = type.path("code").asText("").toLowerCase(Locale.ROOT);
                if (code.endsWith(".deposit") || code.endsWith(".interestposting")) direction = "C";
                else if (code.endsWith(".withdrawal") || code.endsWith(".withdrawalfee") || code.endsWith(".annualfee")) direction = "D";
            }
            String description = type.path("value").asText("");
            if (description.isBlank()) description = type.path("code").asText("Movimento bancário");
            transactions.add(new BankTransaction(tx.path("id").asLong(), readDate(tx.path("date")),
                readAmount(tx.path("amount")), direction, description, tx.path("reversed").asBoolean(false)));
        }
        return new BankAccount(
            account.path("id").asLong(), account.path("accountNo").asText(),
            account.path("clientId").asLong(), account.path("clientName").asText(),
            account.path("currency").path("code").asText(),
            account.path("status").path("active").asBoolean(false),
            readAmount(account.path("summary").path("accountBalance")), transactions);
    }

    @Override
    public JsonNode listSavingsProducts() {
        return http.get("/savingsproducts");
    }

    @Override
    public JsonNode createDemoSavingsProduct() {
        // Produto sem contabilidade financeira e sem juros: estritamente laboratório.
        return http.post("/savingsproducts", Map.ofEntries(
            Map.entry("name", "Poupanca PFC MZN"),
            Map.entry("shortName", "PFCM"),
            Map.entry("description", "Produto de poupanca para simular extratos certificados"),
            Map.entry("currencyCode", "MZN"),
            Map.entry("digitsAfterDecimal", 2),
            Map.entry("inMultiplesOf", 0),
            Map.entry("nominalAnnualInterestRate", 0),
            Map.entry("interestCompoundingPeriodType", 1),
            Map.entry("interestPostingPeriodType", 4),
            Map.entry("interestCalculationType", 1),
            Map.entry("interestCalculationDaysInYearType", 365),
            Map.entry("accountingRule", 1),
            Map.entry("locale", "en")));
    }

    @Override
    public long submit(long clientId, long productId, LocalDate date) {
        JsonNode response = http.post("/savingsaccounts", Map.of(
            "clientId", clientId, "productId", productId,
            "submittedOnDate", date.format(DATE), "locale", "en", "dateFormat", "dd MMMM yyyy"));
        long id = response.path("savingsId").asLong(0);
        if (id == 0) id = response.path("resourceId").asLong(0);
        if (id <= 0) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "O Fineract não devolveu o ID da conta");
        return id;
    }

    @Override
    public void approve(long accountId, LocalDate date) {
        http.post("/savingsaccounts/" + accountId + "?command=approve", Map.of(
            "approvedOnDate", date.format(DATE), "locale", "en", "dateFormat", "dd MMMM yyyy"));
    }

    @Override
    public void activate(long accountId, LocalDate date) {
        http.post("/savingsaccounts/" + accountId + "?command=activate", Map.of(
            "activatedOnDate", date.format(DATE), "locale", "en", "dateFormat", "dd MMMM yyyy"));
    }

    private static BigDecimal readAmount(JsonNode value) {
        if (value.isMissingNode() || value.isNull()) return BigDecimal.ZERO;
        return new BigDecimal(value.asText());
    }

    private static LocalDate readDate(JsonNode value) {
        if (value.isArray() && value.size() >= 3) {
            return LocalDate.of(value.get(0).asInt(), value.get(1).asInt(), value.get(2).asInt());
        }
        if (value.isTextual()) return LocalDate.parse(value.asText());
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Data de movimento inválida no Fineract");
    }
}
