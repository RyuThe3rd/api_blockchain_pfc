package org.example.blockchain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.codec.CborEnvelopeEncoder;
import org.example.blockchain.codec.TypeACborEncoder;
import org.example.blockchain.dto.TypeAAnchorRequestDTO;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Numeric;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/** Emite o Tipo A CBOR, reutilizando assinatura, envelope e ancoragem. */
@Slf4j
@Service
@RequiredArgsConstructor
public class TypeAStatementService {

    // Máximo de bytes de dados para QR Code versão 40, ECC M, byte mode.
    private static final int MAX_QR_BYTES_NIVEL_M = 2331;
    private static final String COMPRESSAO = "none";

    private final TypeACborEncoder typeACborEncoder;
    private final CborEnvelopeEncoder cborEnvelopeEncoder;
    private final CryptoService cryptoService;
    private final StatementRegistryService statementRegistryService;

    public Map<String, Object> anchor(TypeAAnchorRequestDTO request) throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("O pedido Tipo A é obrigatório");
        }

        // Segue o protótipo Tipo B; produção: assinador com chave protegida.
        Credentials credentials = Credentials.create(request.getPrivateKey());
        String issuedAt = Instant.now().toString();

        // SHA-256 dos bytes CBOR originais, jamais do envelope.
        byte[] payloadCbor = typeACborEncoder.encode(request, issuedAt);
        byte[] documentHashBytes = MessageDigest.getInstance("SHA-256")
                .digest(payloadCbor);
        String documentHash = Numeric.toHexString(documentHashBytes);

        // CryptoService assina o SHA-256 com prefixo Ethereum.
        String assinaturaHex = cryptoService.signDocumentHash(documentHash, credentials);
        byte[] assinaturaBytes = Numeric.hexStringToByteArray(assinaturaHex);
        if (assinaturaBytes.length != 65) {
            throw new IllegalStateException("A assinatura ECDSA deve conter 65 bytes");
        }

        // Tipo A: CBOR sem compressão, algoritmo "none" no envelope.
        byte[] envelopeCbor = cborEnvelopeEncoder.encode(
                TypeACborEncoder.VERSAO_PROTOCOLO,
                TypeACborEncoder.TIPO_PAYLOAD,
                TypeACborEncoder.VERSAO_ESQUEMA,
                COMPRESSAO,
                payloadCbor,
                assinaturaBytes
        );

        // Não ancorar algo que não caiba num QR em modo Byte ECC M.
        if (envelopeCbor.length > MAX_QR_BYTES_NIVEL_M) {
            throw new IllegalArgumentException(
                    "Envelope excede a capacidade do QR nível M: "
                            + envelopeCbor.length + " bytes");
        }

        // Mesmo smart contract do Tipo B; hash opaco de 32 bytes.
        String transactionHash = statementRegistryService.anchorStatement(
                documentHash, credentials);

        log.info("Extracto Tipo A CBOR ancorado. Hash: {}, Tx: {}",
                documentHash, transactionHash);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Extracto Tipo A CBOR emitido e ancorado com sucesso");
        response.put("documentHash", documentHash);
        response.put("transactionHash", transactionHash);
        response.put("signerAddress", credentials.getAddress());
        response.put("assinaturaBanco", assinaturaHex);
        response.put("issuedAt", issuedAt);
        response.put("qrCodeDataBase64", Base64.getEncoder().encodeToString(envelopeCbor));
        response.put("qrByteLength", envelopeCbor.length);
        return response;
    }
}
