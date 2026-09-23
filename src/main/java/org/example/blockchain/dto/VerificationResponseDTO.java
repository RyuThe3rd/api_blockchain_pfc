package org.example.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerificationResponseDTO {

    @JsonProperty("isValid")
    private boolean isValid;

    @JsonProperty("bankId")
    private String bankId;

    @JsonProperty("blockNumber")
    private Long blockNumber;

    @JsonProperty("transactionHash")
    private String transactionHash;

    @JsonProperty("blockTimestamp")
    private Long blockTimestamp;

    // Construtor vazio
    public VerificationResponseDTO() {}

    public VerificationResponseDTO(boolean isValid, String bankId, Long blockNumber,
                                   String transactionHash, Long blockTimestamp) {
        this.isValid = isValid;
        this.bankId = bankId;
        this.blockNumber = blockNumber;
        this.transactionHash = transactionHash;
        this.blockTimestamp = blockTimestamp;
    }

    // Getters e Setters
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Long getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(Long blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }
}