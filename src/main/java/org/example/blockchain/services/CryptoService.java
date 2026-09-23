package org.example.blockchain.services;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.io.pem.PemReader;
import org.example.blockchain.models.BankRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;

@Service
public class CryptoService {

    private static final Logger logger = LoggerFactory.getLogger(CryptoService.class);
    private final BankRegistryService bankRegistryService; // serviço para buscar chave pública

    @Autowired
    public CryptoService(BankRegistryService bankRegistryService) {
        this.bankRegistryService = bankRegistryService;
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Verifica a assinatura digital usando a chave pública do banco.
     * Espera-se que a assinatura esteja em formato DER hex (ou base64) e o hash em hex.
     * Usa algoritmo ECDSA com curva secp256k1 (comum em blockchains).
     */
    public boolean verifySignature(String documentHash, String bankId, String signatureHex) {
        try {
            BankRegistry bank = bankRegistryService.findByBankId(bankId);
            if (bank == null) {
                logger.warn("Banco não encontrado: {}", bankId);
                return false;
            }

            PublicKey publicKey = loadPublicKeyFromPem(bank.getPublicKeyPem());
            Signature sig = Signature.getInstance("SHA256withECDSA", BouncyCastleProvider.PROVIDER_NAME);
            sig.initVerify(publicKey);

            byte[] hashBytes = hexStringToByteArray(documentHash);
            sig.update(hashBytes);

            byte[] signatureBytes = hexStringToByteArray(signatureHex);
            return sig.verify(signatureBytes);
        } catch (Exception e) {
            logger.error("Erro na verificação da assinatura", e);
            return false;
        }
    }

    private PublicKey loadPublicKeyFromPem(String pem) throws Exception {
        // Remove cabeçalhos e rodapés, converte para bytes
        String cleaned = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = java.util.Base64.getDecoder().decode(cleaned);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME);
        return kf.generatePublic(spec);
    }

    private byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
}