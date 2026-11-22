package org.example.blockchain.CoreBancario.statements;

import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.banking.model.MoneyParts;
import org.example.blockchain.dto.TypeAAnchorRequestDTO;
import org.example.blockchain.dto.TypeBAnchorRequestDTO;
import org.example.blockchain.dto.TypeBTransactionDTO;
import org.springframework.stereotype.Component;
import tools.jackson.dataformat.cbor.CBORMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

/** Traduz um snapshot bancário para os contratos CBOR existentes. */
@Component
public class StatementCertificationMapper {
    private final CBORMapper cborMapper = new CBORMapper();

    public TypeAAnchorRequestDTO toTypeA(StatementData data, String bank, String signingKey) {
        MoneyParts balance = MoneyParts.from(data.closingBalance());
        TypeAAnchorRequestDTO result = new TypeAAnchorRequestDTO();
        result.setPrivateKey(signingKey);
        result.setBanco(bank);
        result.setTitular(data.holder());
        result.setNumeroConta(data.accountNo());
        result.setPeriodo(period(data));
        result.setParteInteiraDoSaldoFinal(balance.units());
        result.setParteDeCentavosDoSaldoFinal(balance.cents());

        // Compromisso criptográfico com os movimentos do período.
        // A estrutura CBOR é a mesma lista de arrays posicionais do Tipo B.
        // O StatementDataService já entrega os movimentos ordenados por data e ID.
        result.setTransactionsHash(transactionsHash(data));
        return result;
    }

    public TypeBAnchorRequestDTO toTypeB(StatementData data, String bank, String signingKey) {
        MoneyParts balance = MoneyParts.from(data.closingBalance());
        TypeBAnchorRequestDTO result = new TypeBAnchorRequestDTO();
        result.setPrivateKey(signingKey);
        result.setBanco(bank);
        result.setTitular(data.holder());
        result.setNumeroConta(data.accountNo());
        result.setPeriodo(period(data));
        result.setParteInteiraDoSaldoFinal(balance.units());
        result.setParteDeCentavosDoSaldoFinal(balance.cents());
        List<TypeBTransactionDTO> transactions = data.movements().stream()
            .map(this::toTypeBMovement).toList();
        result.setTransactions(transactions); // [] é um extracto válido.
        return result;
    }

    private String transactionsHash(StatementData data) {
        // Não converter em JSON nem serializar os DTOs como objectos.
        // Reproduzir exactamente a representação CBOR posicional do Tipo B.
        List<List<Object>> movimentosCbor = data.movements().stream()
            .map(this::toTypeBMovement)
            .map(tx -> List.<Object>of(
                tx.getData(),
                tx.getParteInteiraDoValor(),
                tx.getParteDeCentavosDoValor(),
                tx.getTipoMovimento(),
                tx.getDescricao()
            ))
            .toList();

        byte[] bytesCbor = cborMapper.writeValueAsBytes(movimentosCbor);
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(bytesCbor);
            return HexFormat.of().formatHex(hash); // 64 caracteres, sem prefixo 0x.
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Algoritmo SHA-256 indisponível", ex);
        }
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
}
