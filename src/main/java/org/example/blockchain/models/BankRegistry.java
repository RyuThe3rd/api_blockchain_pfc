package org.example.blockchain.models;

import jakarta.persistence.*;
import java.security.PublicKey;

@Entity
@Table(name = "bank_registry")
public class BankRegistry {

    @Id
    private String bankId; // identificador do banco (ex: "BANK01")

    @Column(nullable = false, length = 42)
    private String blockchainAddress; // endereço na blockchain

    @Column(nullable = false, columnDefinition = "TEXT")
    private String publicKeyPem; // chave pública em formato PEM (ou hex)

    @Column(nullable = false)
    private String name;

    // Construtor padrão
    public BankRegistry() {}

    public BankRegistry(String bankId, String blockchainAddress, String publicKeyPem, String name) {
        this.bankId = bankId;
        this.blockchainAddress = blockchainAddress;
        this.publicKeyPem = publicKeyPem;
        this.name = name;
    }

    // Getters e Setters
    public String getBankId() { return bankId; }
    public void setBankId(String bankId) { this.bankId = bankId; }
    public String getBlockchainAddress() { return blockchainAddress; }
    public void setBlockchainAddress(String blockchainAddress) { this.blockchainAddress = blockchainAddress; }
    public String getPublicKeyPem() { return publicKeyPem; }
    public void setPublicKeyPem(String publicKeyPem) { this.publicKeyPem = publicKeyPem; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}