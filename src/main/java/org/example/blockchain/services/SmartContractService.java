package org.example.blockchain.services;

import org.example.blockchain.models.DocumentProof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class SmartContractService {

    private static final Logger logger = LoggerFactory.getLogger(SmartContractService.class);

    private final Web3j web3j;
    private final Credentials credentials;
    private final String contractAddress;
    private final ContractGasProvider gasProvider;

    // Supondo que temos um wrapper do contrato gerado pelo Web3j (ex: DocumentProofContract)
    // Caso contrário, usamos chamadas raw.
    // Aqui vamos simular com um mapa em memória, mas com chamadas reais comentadas.

    public SmartContractService(Web3j web3j, Credentials credentials,
                                @Value("${blockchain.contract.address}") String contractAddress,
                                @Value("${blockchain.gas.price}") String gasPrice,
                                @Value("${blockchain.gas.limit}") String gasLimit) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.contractAddress = contractAddress;
        this.gasProvider = new StaticGasProvider(
                new BigInteger(gasPrice),
                new BigInteger(gasLimit)
        );
    }

    /**
     * Invoca o contrato para armazenar a prova (hash do documento, endereço do emissor, timestamp).
     * Retorna o hash da transação.
     */
    public String storeProof(String documentHash, String issuerAddress, Long timestamp) throws Exception {
        // Implementação real com Web3j:
        // DocumentProofContract contract = DocumentProofContract.load(contractAddress, web3j, credentials, gasProvider);
        // TransactionReceipt receipt = contract.storeProof(hexToBytes32(documentHash), issuerAddress, BigInteger.valueOf(timestamp)).send();
        // return receipt.getTransactionHash();

        // Simulação para testes:
        logger.info("Simulando storeProof para hash {} no contrato {}", documentHash, contractAddress);
        // Em produção, chamar o contrato real.
        return "0x" + Long.toHexString(System.currentTimeMillis()); // tx hash fake
    }

    /**
     * Consulta o contrato para verificar se o hash foi ancorado.
     * Retorna um DocumentProof com os dados, ou null se não existir.
     */
    public DocumentProof verifyProof(String documentHash) throws Exception {
        // Implementação real:
        // DocumentProofContract contract = DocumentProofContract.load(contractAddress, web3j, credentials, gasProvider);
        // Optional<DocumentProof> proof = contract.getProof(hexToBytes32(documentHash)).send();
        // if (proof.isPresent()) { ... }

        // Simulação: busca em um mapa local (apenas para demonstração)
        // Na prática, você consultaria o estado do contrato.
        // Aqui, para não perder a funcionalidade, vamos retornar um mock se o hash for conhecido.
        if (fakeStorage.containsKey(documentHash)) {
            return fakeStorage.get(documentHash);
        }
        return null;
    }

    // Mapa fake para simulação (remova em produção)
    private static final java.util.Map<String, DocumentProof> fakeStorage = new java.util.concurrent.ConcurrentHashMap<>();

    // Método auxiliar para simular armazenamento (seria chamado pelo storeProof real)
    public void simulateStore(DocumentProof proof) {
        fakeStorage.put(proof.getDocumentHash(), proof);
    }

    // Conversão de hex para bytes32 (32 bytes) - necessário para contrato Solidity
    private byte[] hexToBytes32(String hex) {
        // Implementação para converter hex string em array de 32 bytes
        byte[] raw = new org.bouncycastle.util.encoders.Hex().decode(hex);
        if (raw.length > 32) {
            throw new IllegalArgumentException("Hash muito longo");
        }
        byte[] result = new byte[32];
        System.arraycopy(raw, 0, result, 0, raw.length);
        return result;
    }
}