package org.example.blockchain.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blockchain")
@RequiredArgsConstructor
public class BlockchainStatusController {

    private final Web3j web3j;

    @Value("${blockchain.contracts.bank-registry-address:N/A}")
    private String bankRegistryAddress;

    @Value("${blockchain.contracts.statement-registry-address:N/A}")
    private String statementRegistryAddress;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        try {
            EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
            Web3ClientVersion clientVersion = web3j.web3ClientVersion().send();

            BigInteger currentBlock = blockNumber.getBlockNumber();

            status.put("status", "UP");
            status.put("currentBlock", currentBlock);
            status.put("clientVersion", clientVersion.getWeb3ClientVersion()); // Ex: Hyperledger Besu/v24.x
            status.put("nodeHealthy", true);

            Map<String, String> contracts = new HashMap<>();
            contracts.put("bankRegistry", bankRegistryAddress);
            contracts.put("statementRegistry", statementRegistryAddress);
            status.put("activeContracts", contracts);

            return ResponseEntity.ok(status);
        } catch (Exception e) {
            status.put("status", "DOWN");
            status.put("nodeHealthy", false);
            status.put("error", e.getMessage());
            return ResponseEntity.status(503).body(status);
        }
    }
}