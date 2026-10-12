package org.example.blockchain.service;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.wrappers.StatementRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementRegistryService {

    private final Web3j web3j;
    private final Credentials credentials; // Chave Operacional do Banco que assina
    private final ContractGasProvider gasProvider;

    @Value("${blockchain.contracts.statement-registry-address}")
    private String statementRegistryAddress;

    private StatementRegistry loadContract() {
        return StatementRegistry.load(statementRegistryAddress, web3j, credentials, gasProvider);
    }

    /**
     * Ancora o Hash SHA-256 de um extrato bancário na blockchain.
     * @param documentHashHex Hash em formato Hexadecimal de 64 carateres (ex: SHA-256 do extrato)
     */
    public String anchorStatement(String documentHashHex) throws Exception {
        log.info("A ancorar extrato com hash: {}...", documentHashHex);

        byte[] documentHashBytes32 = convertHexToBytes32(documentHashHex);
        StatementRegistry contract = loadContract();

        TransactionReceipt receipt = contract.registerStatement(documentHashBytes32).send();

        log.info("Extrato ancorado com sucesso. TxHash: {}", receipt.getTransactionHash());
        return receipt.getTransactionHash();
    }

    /**
     * Verifica a autenticidade de um extrato na blockchain.
     */
    public StatementVerificationResult verifyStatement(String documentHashHex) throws Exception {
        byte[] documentHashBytes32 = convertHexToBytes32(documentHashHex);
        StatementRegistry contract = loadContract();

        Tuple4<Boolean, BigInteger, String, BigInteger> result = contract.verifyStatement(documentHashBytes32).send();

        boolean exists = result.component1();
        if (!exists) {
            return StatementVerificationResult.builder()
                    .exists(false)
                    .build();
        }

        return StatementVerificationResult.builder()
                .exists(true)
                .bankId(result.component2().longValue())
                .chaveOperacional(result.component3())
                .timestamp(result.component4().longValue())
                .build();
    }

    /**
     * Converte uma String Hexadecimal (SHA-256) num array de 32 bytes exigido pelo Solidity (bytes32)
     */
    private byte[] convertHexToBytes32(String hex) {
        String cleanHex = Numeric.cleanHexPrefix(hex);
        byte[] rawBytes = Numeric.hexStringToByteArray(cleanHex);
        if (rawBytes.length != 32) {
            throw new IllegalArgumentException("O Hash SHA-256 deve ter exatamente 32 bytes (64 caracteres hex).");
        }
        return rawBytes;
    }

    @Data
    @Builder
    public static class StatementVerificationResult {
        private boolean exists;
        private Long bankId;
        private String chaveOperacional;
        private Long timestamp;
    }
}