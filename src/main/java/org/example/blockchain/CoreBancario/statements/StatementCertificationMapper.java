package org.example.blockchain.CoreBancario.statements;

import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.banking.model.MoneyParts;
import org.example.blockchain.dto.TypeAAnchorRequestDTO;
import org.example.blockchain.dto.TypeBAnchorRequestDTO;
import org.example.blockchain.dto.TypeBTransactionDTO;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

/** Traduz um snapshot bancário para os contratos CBOR existentes. */
@Component
public class StatementCertificationMapper {
    public TypeAAnchorRequestDTO toTypeA(StatementData data, String bank, String signingKey) {
        MoneyParts balance = MoneyParts.from(data.closingBalance());
        TypeAAnchorRequestDTO result = new TypeAAnchorRequestDTO();
        result.setPrivateKey(signingKey);
        result.setBanco(bank);
        result.setTitular(data.holder());
        result.setContaHash(hash(bank + ":" + data.accountNo()));
        result.setPeriodo(period(data));
        result.setParteInteiraDoSaldoFinal(balance.units());
        result.setParteDeCentavosDoSaldoFinal(balance.cents());
        // Opcional em CBOR v1; o Tipo A não inclui movimentos individuais.
        result.setTransactionsHash(null);
        return result;
    }

    public TypeBAnchorRequestDTO toTypeB(StatementData data, String bank, String signingKey) {
        MoneyParts balance = MoneyParts.from(data.closingBalance());
        TypeBAnchorRequestDTO result = new TypeBAnchorRequestDTO();
        result.setPrivateKey(signingKey);
        result.setBanco(bank);
        result.setTitular(data.holder());
        result.setPeriodo(period(data));
        result.setParteInteiraDoSaldoFinal(balance.units());
        result.setParteDeCentavosDoSaldoFinal(balance.cents());
        List<TypeBTransactionDTO> transactions = data.movements().stream()
            .map(this::toTypeBMovement).toList();
        result.setTransactions(transactions); // [] é um extracto válido.
        return result;
    }

    private TypeBTransactionDTO toTypeBMovement(BankTransaction tx) {
        MoneyParts amount = MoneyParts.from(tx.amount());
        TypeBTransactionDTO out = new TypeBTransactionDTO();
        out.setData(tx.date().toString());
        out.setParteInteiraDoValor(amount.units());
        out.setParteDeCentavosDoValor(amount.cents());
        out.setTipoMovimento(tx.direction());
        String description = tx.description();
        out.setDescricao(description.length() <= 250 ? description : description.substring(0, 250));
        return out;
    }

    private String period(StatementData data) {
        return data.startDate() + " a " + data.endDate();
    }

    private String hash(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Algoritmo SHA-256 indisponível", ex);
        }
    }
}
