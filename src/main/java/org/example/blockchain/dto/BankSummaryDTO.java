package org.example.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankSummaryDTO {
    private Long bankId;
    private String name;
    private String operationalKey;
    private boolean isActive;
    private Long expiresAt;
}