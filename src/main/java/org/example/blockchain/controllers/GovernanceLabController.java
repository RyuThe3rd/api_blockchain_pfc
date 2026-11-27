package org.example.blockchain.controllers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import org.example.blockchain.wrappers.BankRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;


@Profile("governanca")
@RestController
@RequestMapping("/api/v1/governance-lab")
public class GovernanceLabController {
    private final Web3j web3j;
    private final Credentials credentials;
    private final ContractGasProvider gasProvider;
    private final String registryAddress;
    private final byte[] token;

    public GovernanceLabController(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider,
        @Value("${blockchain.contracts.bank-registry-address}") String registryAddress,
        @Value("${governance.lab.token:}") String token) {
        if (token.length() < 32) throw new IllegalStateException("Configure GOVERNANCE_LAB_TOKEN with at least 32 characters");
        this.web3j = web3j; this.credentials = credentials; this.gasProvider = gasProvider;
        this.registryAddress = registryAddress; this.token = token.getBytes(StandardCharsets.UTF_8);
    }
    public record Operation(Long bankId, String newOperationalKey, Long durationInSeconds, Long suspectedFrom, String key) {}
    private void authorize(String supplied) {
        if (supplied == null || !MessageDigest.isEqual(token, supplied.getBytes(StandardCharsets.UTF_8)))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token de laboratório inválido");
    }
    private BigInteger positive(Long n, String field) {
        if (n == null || n <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " inválido");
        return BigInteger.valueOf(n);
    }
    private String address(String a) {
        if (a == null || !a.matches("(?i)^0x[0-9a-f]{40}$") || a.matches("(?i)^0x0{40}$"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço inválido");
        return a;
    }
    @PostMapping("/{action}")
    public Map<String, Object> execute(@PathVariable String action,
        @RequestHeader(value="X-Lab-Token", required=false) String supplied,
        @RequestBody Operation data) {
        authorize(supplied);
        BigInteger id = positive(data.bankId(), "bankId");
        BankRegistry contract = BankRegistry.load(registryAddress, web3j, credentials, gasProvider);
        try {
            TransactionReceipt receipt = switch (action) {
                case "revoke" -> contract.revokeBank(id).send();
                case "rotate" -> contract.rotateOperationalKey(id, address(data.newOperationalKey()), positive(data.durationInSeconds(), "durationInSeconds")).send();
                case "reactivate" -> contract.reactivateBank(id, address(data.newOperationalKey()), positive(data.durationInSeconds(), "durationInSeconds")).send();
                case "rotate-compromise" -> contract.rotateAndReportCompromise(id, address(data.newOperationalKey()), positive(data.durationInSeconds(), "durationInSeconds"), positive(data.suspectedFrom(), "suspectedFrom")).send();
                case "revoke-compromise" -> contract.revokeBankAndReportCompromise(id, positive(data.suspectedFrom(), "suspectedFrom")).send();
                case "report-compromise" -> contract.reportKeyCompromise(id, address(data.key()), positive(data.suspectedFrom(), "suspectedFrom")).send();
                default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operação desconhecida");
            };
            if (!receipt.isStatusOK()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Transacção revertida");
            return Map.of("success", true, "message", "Operação confirmada", "transactionHash", receipt.getTransactionHash());
        } catch (ResponseStatusException e) { throw e; }
        catch (Exception e) { throw new ResponseStatusException(HttpStatus.CONFLICT, "Operação não confirmada. Consulte os logs do servidor e o estado on-chain."); }
    }
}
