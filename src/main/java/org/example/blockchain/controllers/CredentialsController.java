package org.example.blockchain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/credentials")
public class CredentialsController {

    /**
     * Gera um novo par de chaves ECDSA (secp256k1) para uso operacional do Banco
     */
    @GetMapping("/generate")
    public ResponseEntity<?> generateCredentials() {
        try {
            ECKeyPair keyPair = Keys.createEcKeyPair();
            String privateKeyHex = "0x" + keyPair.getPrivateKey().toString(16);
            String addressHex = "0x" + Keys.getAddress(keyPair);

            return ResponseEntity.ok(Map.of(
                    "address", addressHex,
                    "privateKey", privateKeyHex
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao gerar credenciais: " + e.getMessage()));
        }
    }
}