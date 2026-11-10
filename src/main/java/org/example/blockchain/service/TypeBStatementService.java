
package org.example.blockchain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.blockchain.codec.TypeBCborEncoder;
import org.example.blockchain.codec.ZlibCodec;
import org.example.blockchain.codec.CborEnvelopeEncoder;
import org.example.blockchain.dto.TypeBAnchorRequestDTO;

import org.springframework.stereotype.Service;

import org.web3j.crypto.Credentials;
import org.web3j.utils.Numeric;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeBStatementService {

    private final TypeBCborEncoder typeBCborEncoder;
    private final ZlibCodec zlibCodec;
    private final CborEnvelopeEncoder cborEnvelopeEncoder;

    // Serviços existentes, partilhados com o Tipo A.
    private final CryptoService cryptoService;
    private final StatementRegistryService statementRegistryService;

    /**
     * Emite, assina e ancora um extracto bancário Tipo B.
     *
     * Fluxo:
     * 1. Gerar o payload CBOR.
     * 2. Calcular SHA-256 sobre os bytes CBOR originais.
     * 3. Assinar o hash com ECDSA.
     * 4. Comprimir os bytes CBOR com ZLIB.
     * 5. Construir o envelope CBOR binário.
     * 6. Ancorar o documentHash na blockchain.
     * 7. Devolver o envelope em Base64.
     */
    public Map<String, Object> anchor(
            TypeBAnchorRequestDTO request
    ) throws Exception {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Os dados do extracto são obrigatórios"
            );
        }

        // 1. Credenciais operacionais do banco emissor.
        Credentials bankCredentials = Credentials.create(
                request.getPrivateKey()
        );

        String signerAddress = bankCredentials.getAddress();

        // 2. Gerar timestamp UTC de emissão.
        String issuedAt = Instant.now().toString();

        // 3. Serializar o payload Tipo B em CBOR.
        byte[] payloadCbor = typeBCborEncoder.encode(
                request,
                issuedAt
        );

        // 4. Calcular SHA-256 dos bytes CBOR originais.
        byte[] documentHashBytes = MessageDigest
                .getInstance("SHA-256")
                .digest(payloadCbor);

        String documentHash = Numeric.toHexString(
                documentHashBytes
        );

        // 5. Assinar o hash utilizando o serviço existente.
        // O CryptoService aplica o prefixo Ethereum.
        String assinaturaHex = cryptoService.signDocumentHash(
                documentHash,
                bankCredentials
        );

        // Converter a assinatura hexadecimal para 65 bytes.
        byte[] assinaturaBytes =
                Numeric.hexStringToByteArray(assinaturaHex);

        if (assinaturaBytes.length != 65) {
            throw new IllegalStateException(
                    "A assinatura ECDSA deve conter 65 bytes"
            );
        }

        // 6. Comprimir os bytes CBOR originais.
        byte[] payloadComprimido = zlibCodec.compress(
                payloadCbor
        );

        // 7. Construir o envelope CBOR binário.
        byte[] envelopeCbor = cborEnvelopeEncoder.encode(
                TypeBCborEncoder.VERSAO_PROTOCOLO,
                TypeBCborEncoder.TIPO_PAYLOAD,
                TypeBCborEncoder.VERSAO_ESQUEMA,
                "zlib",
                payloadComprimido,
                assinaturaBytes
        );

        // 8. Codificar o envelope em Base64 para a resposta HTTP.
        // Base64 NÃO será o conteúdo do QR Code.
        String qrCodeDataBase64 = Base64
                .getEncoder()
                .encodeToString(envelopeCbor);

        // 9. Ancorar o hash original na blockchain.
        // Fazemos isto depois de preparar o envelope,
        // evitando ancorar um documento cuja codificação
        // tenha falhado.
        String transactionHash =
                statementRegistryService.anchorStatement(
                        documentHash,
                        bankCredentials
                );

        log.info(
                "Extracto Tipo B ancorado. Hash: {}, TxHash: {}",
                documentHash,
                transactionHash
        );

        // 10. Construir a resposta HTTP.
        Map<String, Object> response = new LinkedHashMap<>();

        response.put(
                "message",
                "Extracto Tipo B emitido e ancorado com sucesso"
        );
        response.put("documentHash", documentHash);
        response.put("transactionHash", transactionHash);
        response.put("signerAddress", signerAddress);
        response.put("assinaturaBanco", assinaturaHex);
        response.put("issuedAt", issuedAt);
        response.put("qrCodeDataBase64", qrCodeDataBase64);
        response.put("qrByteLength", envelopeCbor.length);

        return response;
    }
}
