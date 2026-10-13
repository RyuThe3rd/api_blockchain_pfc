package org.example.blockchain.service;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.util.HashMap;
import java.util.Map;

@Service
public class CryptoService {

    private final Credentials defaultCredentials; // Injetado via BlockchainConfig

    public CryptoService(Credentials defaultCredentials) {
        this.defaultCredentials = defaultCredentials;
    }

    /**
     * Assina o hash SHA-256 do documento usando a Chave Privada Operacional.
     * Retorna a assinatura em formato hexadecimal (65 bytes: R + S + V).
     */
    public String signDocumentHash(String documentHashHex) {
        byte[] hashBytes = Numeric.hexStringToByteArray(documentHashHex);

        // Assina a mensagem com prefixo Ethereum (\x19Ethereum Signed Message:\n32...)
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(hashBytes, defaultCredentials.getEcKeyPair());

        // Concatena R (32 bytes) + S (32 bytes) + V (1 byte)
        byte[] retval = new byte[65];
        System.arraycopy(signatureData.getR(), 0, retval, 0, 32);
        System.arraycopy(signatureData.getS(), 0, retval, 32, 32);
        retval[64] = signatureData.getV()[0];

        return Numeric.toHexString(retval);
    }

    public String signDocumentHash(String documentHashHex, Credentials customCredentials) {
        byte[] hashBytes = Numeric.hexStringToByteArray(documentHashHex);

        // Assina a mensagem com prefixo Ethereum (\x19Ethereum Signed Message:\n32...)
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(hashBytes, customCredentials.getEcKeyPair());

        // Concatena R (32 bytes) + S (32 bytes) + V (1 byte)
        byte[] retval = new byte[65];
        System.arraycopy(signatureData.getR(), 0, retval, 0, 32);
        System.arraycopy(signatureData.getS(), 0, retval, 32, 32);
        retval[64] = signatureData.getV()[0];

        return Numeric.toHexString(retval);
    }

    public String getOperationalPublicKey() {
        return defaultCredentials.getAddress(); // Endereço/Chave pública da conta operacional
    }

    /**
     * Retorna o endereço público derivado de um conjunto específico de credenciais.
     */
    public String getOperationalPublicKey(Credentials customCredentials) {
        return customCredentials.getAddress();
    }
}