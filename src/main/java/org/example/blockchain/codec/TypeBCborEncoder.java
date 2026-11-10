
package org.example.blockchain.codec;

import org.example.blockchain.dto.TypeBAnchorRequestDTO;
import org.example.blockchain.dto.TypeBTransactionDTO;

import org.springframework.stereotype.Component;
import tools.jackson.dataformat.cbor.CBORMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TypeBCborEncoder {

    public static final int VERSAO_PROTOCOLO = 1;
    public static final String TIPO_PAYLOAD = "B";
    public static final int VERSAO_ESQUEMA = 1;

    private final CBORMapper cborMapper = new CBORMapper();

    /**
     * Converte um extracto Tipo B num array CBOR posicional.
     *
     * A ordem dos campos é fixa e deve corresponder
     * ao descodificador da Flutter PWA.
     *
     * @param request Dados do extracto e movimentos bancários.
     * @param issuedAt Timestamp de emissão ISO-8601 em UTC.
     * @return Bytes CBOR originais, ainda não comprimidos.
     */
    public byte[] encode(
            TypeBAnchorRequestDTO request,
            String issuedAt
    ) {

        Objects.requireNonNull(request, "O pedido é obrigatório");

        if (issuedAt == null || issuedAt.isBlank()) {
            throw new IllegalArgumentException(
                    "A data de emissão é obrigatória"
            );
        }

        if (request.getTransactions() == null ||
                request.getTransactions().isEmpty()) {
            throw new IllegalArgumentException(
                    "O extracto deve conter transacções"
            );
        }

        // 1. Construir os arrays posicionais das transacções.
        List<List<Object>> transacoes = new ArrayList<>();

        for (TypeBTransactionDTO tx : request.getTransactions()) {

            if (tx == null) {
                throw new IllegalArgumentException(
                        "A lista contém uma transacção nula"
                );
            }

            List<Object> transacao = List.of(
                    tx.getData(),                      // [0]
                    tx.getParteInteiraDoValor(),       // [1]
                    tx.getParteDeCentavosDoValor(),    // [2]
                    tx.getTipoMovimento(),             // [3]
                    tx.getDescricao()                  // [4]
            );

            transacoes.add(transacao);
        }

        // 2. Construir o payload Tipo B.
        List<Object> payload = List.of(
                VERSAO_PROTOCOLO,                         // [0]
                TIPO_PAYLOAD,                              // [1]
                VERSAO_ESQUEMA,                            // [2]
                request.getBanco(),                        // [3]
                request.getTitular(),                      // [4]
                request.getParteInteiraDoSaldoFinal(),     // [5]
                request.getParteDeCentavosDoSaldoFinal(),  // [6]
                issuedAt,                                  // [7]
                request.getPeriodo(),                      // [8]
                transacoes                                 // [9]
        );

        // 3. Serializar directamente para CBOR binário.
        return cborMapper.writeValueAsBytes(payload);
    }
}
