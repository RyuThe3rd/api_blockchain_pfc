package org.example.blockchain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
public class HealthCheck {

    private final Web3j web3j;

    @Autowired
    public HealthCheck(Web3j web3j) {
        this.web3j = web3j;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> check() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Servidor Ativo :)");
        try {
            // Verifica conexão com o nó
            web3j.ethBlockNumber().send();
            response.put("blockchain", "connected");
        } catch (Exception e) {
            response.put("blockchain", "disconnected");
            response.put("blockchainError", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}