package org.example.blockchain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.wrappers.BankRegistry;
import org.example.blockchain.wrappers.BankRegistry.BankSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankRegistryService {

    private final Web3j web3j;
    private final Credentials credentials; // Pode ser Admin ou ManagementKey do Banco
    private final ContractGasProvider gasProvider;

    @Value("${blockchain.contracts.bank-registry-address}")
    private String bankRegistryAddress;

    private BankRegistry loadContract() {
        return BankRegistry.load(bankRegistryAddress, web3j, credentials, gasProvider);
    }

    /**
     * Regista um novo banco no consórcio (Apenas Admin)
     */
    public String registerBank(Long bankId, String name, String managementKey, String operationalKey, Long durationInSeconds) throws Exception {
        log.info("A registrar banco id: {} - {} on-chain...", bankId, name);
        BankRegistry contract = loadContract();

        TransactionReceipt receipt = contract.registerBank(
                BigInteger.valueOf(bankId),
                name,
                managementKey,
                operationalKey,
                BigInteger.valueOf(durationInSeconds)
        ).send();

        log.info("Banco {} registrado na Tx: {}", bankId, receipt.getTransactionHash());
        return receipt.getTransactionHash();
    }

    /**
     * Roda a Chave Operacional de um banco
     */
    public String rotateOperationalKey(Long bankId, String newOperationalKey, Long durationInSeconds) throws Exception {
        log.info("A rodar chave operacional do banco id: {}...", bankId);
        BankRegistry contract = loadContract();

        TransactionReceipt receipt = contract.rotateOperationalKey(
                BigInteger.valueOf(bankId),
                newOperationalKey,
                BigInteger.valueOf(durationInSeconds)
        ).send();

        return receipt.getTransactionHash();
    }

    /**
     * Revoga o acesso de um banco
     */
    public String revokeBank(Long bankId) throws Exception {
        log.info("A revogar o banco id: {}...", bankId);
        BankRegistry contract = loadContract();

        TransactionReceipt receipt = contract.revokeBank(BigInteger.valueOf(bankId)).send();
        return receipt.getTransactionHash();
    }

    /**
     * Retorna a lista completa de bancos para popular o Accordion no Frontend
     */
    public List<BankSummary> getAllBanks() throws Exception {
        BankRegistry contract = loadContract();
        return contract.getAllBanks().send();
    }

    /**
     * Retorna o nome do banco pelo ID
     */
    public String getBankName(Long bankId) throws Exception {
        BankRegistry contract = loadContract();
        return contract.getBankName(BigInteger.valueOf(bankId)).send();
    }

    public String getOperationalKey(BigInteger bankId) {
        try {
            BankRegistry bankRegistryContract = loadContract();
            // Invoca a função getOperationalKey no wrapper Web3j do Smart Contract BankRegistry
            return bankRegistryContract.getOperationalKey(bankId).send();
        } catch (Exception e) {
            log.error("Erro ao consultar chave operacional para o bankId {}: {}", bankId, e.getMessage());
            throw new RuntimeException("Falha ao consultar a blockchain: " + e.getMessage(), e);
        }
    }
}