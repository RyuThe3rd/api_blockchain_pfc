package org.example.blockchain.dto;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankSummaryDTO {
    private BigInteger bankId;
    private String name;
    private String operationalKey;
    private boolean isActive;
    private BigInteger expiresAt;
}