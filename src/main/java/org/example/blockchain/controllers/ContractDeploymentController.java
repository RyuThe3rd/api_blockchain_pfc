package org.example.blockchain.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.dto.DeploymentResponse;
import org.example.blockchain.dto.FullDeploymentResponse;
import org.example.blockchain.service.ContractDeploymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/contracts")
@RequiredArgsConstructor
public class ContractDeploymentController {

    private final ContractDeploymentService deploymentService;

    /**
     * Deploy individual do BankRegistry.sol
     * POST /api/v1/admin/contracts/deploy/bank-registry
     */
    @PostMapping("/deploy/bank-registry")
    public ResponseEntity<?> deployBankRegistry() {
        try {
            DeploymentResponse response = deploymentService.deployBankRegistry();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao efetuar deploy do BankRegistry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Falha no deploy do BankRegistry: " + e.getMessage()));
        }
    }

    /**
     * Deploy individual do StatementRegistry.sol
     * POST /api/v1/admin/contracts/deploy/statement-registry?bankRegistryAddress=0x...
     */
    @PostMapping("/deploy/statement-registry")
    public ResponseEntity<?> deployStatementRegistry(@RequestParam String bankRegistryAddress) {
        try {
            DeploymentResponse response = deploymentService.deployStatementRegistry(bankRegistryAddress);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao efetuar deploy do StatementRegistry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Falha no deploy do StatementRegistry: " + e.getMessage()));
        }
    }

    /**
     * Deploy orquestrado em cadeia (BankRegistry -> StatementRegistry)
     * POST /api/v1/admin/contracts/deploy/all
     */
    @PostMapping("/deploy/all")
    public ResponseEntity<?> deployAll() {
        try {
            FullDeploymentResponse response = deploymentService.deployAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao efetuar deploy em cadeia dos contratos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Falha no deploy orquestrado: " + e.getMessage()));
        }
    }
}