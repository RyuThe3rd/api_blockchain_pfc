package org.example.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullDeploymentResponse {
    private DeploymentResponse bankRegistry;
    private DeploymentResponse statementRegistry;
}