
package org.example.blockchain.codec;

import org.springframework.stereotype.Component;
import tools.jackson.dataformat.cbor.CBORMapper;

import java.util.List;
import java.util.Objects;

@Component
public class CborEnvelopeEncoder {

    private final CBORMapper cborMapper = new CBORMapper();

    /**
     * Constrói um envelope CBOR binário com seis posições.
     *
     * Estrutura:
     *
     * [0] versaoProtocolo
     * [1] tipoPayload
     * [2] versaoEsquemaPayload
     * [3] algoritmoCompressao
     * [4] payloadComprimido
     * [5] assinaturaECDSA
     *
     * @return Bytes do envelope CBOR para codificação no QR.
     */
    public byte[] encode(
            int versaoProtocolo,
            String tipoPayload,
            int versaoEsquemaPayload,
            String algoritmoCompressao,
            byte[] payloadComprimido,
            byte[] assinaturaECDSA
    ) {

        Objects.requireNonNull(
                payloadComprimido,
                "O payload comprimido não pode ser nulo"
        );

        Objects.requireNonNull(
                assinaturaECDSA,
                "A assinatura ECDSA não pode ser nula"
        );

        if (versaoProtocolo < 1 || versaoEsquemaPayload < 1) {
            throw new IllegalArgumentException(
                    "Versões do protocolo e do esquema inválidas"
            );
        }

        if (tipoPayload == null || tipoPayload.isBlank()) {
            throw new IllegalArgumentException(
                    "O tipo de payload é obrigatório"
            );
        }

        if (algoritmoCompressao == null ||
                algoritmoCompressao.isBlank()) {
            throw new IllegalArgumentException(
                    "O algoritmo de compressão é obrigatório"
            );
        }

        if (payloadComprimido.length == 0) {
            throw new IllegalArgumentException(
                    "O payload comprimido está vazio"
            );
        }

        if (assinaturaECDSA.length != 65) {
            throw new IllegalArgumentException(
                    "A assinatura ECDSA deve conter 65 bytes"
            );
        }

        // Construir o envelope posicional.
        List<Object> envelope = List.of(
                versaoProtocolo,        // [0]
                tipoPayload,           // [1]
                versaoEsquemaPayload,   // [2]
                algoritmoCompressao,   // [3]
                payloadComprimido,      // [4]
                assinaturaECDSA        // [5]
        );

        // Serializar o envelope para CBOR.
        return cborMapper.writeValueAsBytes(envelope);
    }
}
