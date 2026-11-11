package org.example.blockchain.codec;

import org.example.blockchain.dto.TypeAAnchorRequestDTO;
import org.springframework.stereotype.Component;
import tools.jackson.dataformat.cbor.CBORMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Payload A: [1, "A", 1, banco, titular, contaHash, periodo,
 * saldoInteiro, saldoCentavos, issuedAt, transactionsHashOuNull].
 */
@Component
public class TypeACborEncoder {

    public static final int VERSAO_PROTOCOLO = 1;
    public static final String TIPO_PAYLOAD = "A";
    public static final int VERSAO_ESQUEMA = 1;

    private final CBORMapper mapper = new CBORMapper();

    public byte[] encode(TypeAAnchorRequestDTO request, String issuedAt) {
        Objects.requireNonNull(request, "O pedido Tipo A é obrigatório");
        if (issuedAt == null || issuedAt.isBlank()) {
            throw new IllegalArgumentException("issuedAt é obrigatório");
        }

        // ArrayList suporta null na posição 10; List.of(...) não suporta.
        List<Object> campos = new ArrayList<>(11);
        campos.add(VERSAO_PROTOCOLO);                     // [0]
        campos.add(TIPO_PAYLOAD);                          // [1]
        campos.add(VERSAO_ESQUEMA);                        // [2]
        campos.add(request.getBanco());                    // [3]
        campos.add(request.getTitular());                  // [4]
        campos.add(request.getContaHash());                // [5]
        campos.add(request.getPeriodo());                  // [6]
        campos.add(request.getParteInteiraDoSaldoFinal()); // [7] inteiro
        campos.add(request.getParteDeCentavosDoSaldoFinal()); // [8] inteiro
        campos.add(issuedAt);                              // [9]

        String transactionsHash = request.getTransactionsHash();
        campos.add(transactionsHash == null || transactionsHash.isBlank()
                ? null
                : transactionsHash.trim());               // [10]

        return mapper.writeValueAsBytes(campos);
    }
}
