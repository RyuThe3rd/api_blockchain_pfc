package org.example.blockchain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blockchain")
public class BlockchainStatusController {

    private final Web3j web3j;

    @Autowired
    public BlockchainStatusController(Web3j web3j) {
        this.web3j = web3j;
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        try {
            EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
            BigInteger currentBlock = blockNumber.getBlockNumber();
            status.put("currentBlock", currentBlock.longValue());
            status.put("network", "Ethereum (simulado)"); // ou obter chainId
            status.put("synced", true); // simplificado
            status.put("contractAddress", "0x..."); // viria da configuração
            status.put("nodeHealthy", true);
        } catch (Exception e) {
            status.put("error", e.getMessage());
            status.put("nodeHealthy", false);
        }
        return status;
    }
}