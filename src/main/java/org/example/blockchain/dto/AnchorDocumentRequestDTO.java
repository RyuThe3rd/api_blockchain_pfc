package org.example.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AnchorDocumentRequestDTO {

    @NotBlank(message = "documentHash é obrigatório")
    @JsonProperty("documentHash")
    private String documentHash;

    @NotBlank(message = "bankId é obrigatório")
    @JsonProperty("bankId")
    private String bankId;

    @NotBlank(message = "bankSignature é obrigatória")
    @JsonProperty("bankSignature")
    private String bankSignature; // assinatura digital (hex)

    @NotNull(message = "timestamp é obrigatório")
    @JsonProperty("timestamp")
    private Long timestamp; // Unix timestamp

    // Getters e Setters
    public String getDocumentHash() {
        return documentHash;
    }

    public void setDocumentHash(String documentHash) {
        this.documentHash = documentHash;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankSignature() {
        return bankSignature;
    }

    public void setBankSignature(String bankSignature) {
        this.bankSignature = bankSignature;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}