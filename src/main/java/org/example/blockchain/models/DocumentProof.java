package org.example.blockchain.models;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "document_proofs")
public class DocumentProof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 66)
    private String documentHash; // hash do documento (hex)

    @Column(nullable = false, length = 42)
    private String issuerAddress; // endereço do banco na blockchain

    @Column(nullable = false)
    private Long timestamp;

    @Column(nullable = false, length = 66)
    private String transactionHash; // tx hash da blockchain

    @Column(nullable = false)
    private Long blockNumber;

    // Construtor padrão
    public DocumentProof() {}

    public DocumentProof(String documentHash, String issuerAddress, Long timestamp,
                         String transactionHash, Long blockNumber) {
        this.documentHash = documentHash;
        this.issuerAddress = issuerAddress;
        this.timestamp = timestamp;
        this.transactionHash = transactionHash;
        this.blockNumber = blockNumber;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDocumentHash() { return documentHash; }
    public void setDocumentHash(String documentHash) { this.documentHash = documentHash; }
    public String getIssuerAddress() { return issuerAddress; }
    public void setIssuerAddress(String issuerAddress) { this.issuerAddress = issuerAddress; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
    public String getTransactionHash() { return transactionHash; }
    public void setTransactionHash(String transactionHash) { this.transactionHash = transactionHash; }
    public Long getBlockNumber() { return blockNumber; }
    public void setBlockNumber(Long blockNumber) { this.blockNumber = blockNumber; }
}