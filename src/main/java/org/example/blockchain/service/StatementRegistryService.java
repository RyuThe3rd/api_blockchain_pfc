package org.example.blockchain.service;

import java.math.BigInteger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.blockchain.dto.VerificationResponseDTO;
import org.example.blockchain.wrappers.StatementRegistry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Numeric;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementRegistryService {

    private final Web3j web3j;
    private final ContractGasProvider gasProvider;

    private static final String READ_ONLY_ADDRESS =
            "0x0000000000000000000000000000000000000000";

    @Value("${blockchain.contracts.statement-registry-address}")
    private String statementRegistryAddress;

    // Para transaccoes de escrita: utiliza as credenciais do banco.
    private StatementRegistry loadContract(
            Credentials bankCredentials
    ) {
        return StatementRegistry.load(
                statementRegistryAddress,
                web3j,
                bankCredentials,
                gasProvider
        );
    }

    // Para consultas: nao precisa da chave privada de um banco.
    private StatementRegistry loadReadOnlyContract() {

        ReadonlyTransactionManager transactionManager =
                new ReadonlyTransactionManager(
                        web3j,
                        READ_ONLY_ADDRESS
                );

        return StatementRegistry.load(
                statementRegistryAddress,
                web3j,
                transactionManager,
                gasProvider
        );
    }

    /**
     * Ancora o extracto com a chave operacional do banco emissor.
     */
    public String anchorStatement(
            String documentHashHex,
            Credentials bankCredentials
    ) throws Exception {

        byte[] documentHashBytes32 =
                convertHexToBytes32(documentHashHex);

        StatementRegistry contract =
                loadContract(bankCredentials);

        TransactionReceipt receipt = contract
                .registerStatement(documentHashBytes32)
                .send();

        if (!receipt.isStatusOK()) {
            throw new IllegalStateException(
                    "A transaccao de ancoragem foi revertida"
            );
        }

        log.info(
                "Extracto ancorado pelo banco {}. TxHash: {}",
                bankCredentials.getAddress(),
                receipt.getTransactionHash()
        );

        return receipt.getTransactionHash();
    }

    /**
     * Consulta a ancoragem, a validade historica da chave
     * e os incidentes de comprometimento.
     *
     * Apenas uma eth_call ao StatementRegistry.
     */
    public VerificationResponseDTO verifyStatement(
            String documentHashHex
    ) throws Exception {

        byte[] documentHashBytes32 =
                convertHexToBytes32(documentHashHex);

        StatementRegistry contract =
                loadReadOnlyContract();

        Tuple6<Boolean, BigInteger, String, BigInteger, Boolean, Boolean> result =
                contract
                .verifyStatementWithStatus(documentHashBytes32)
                .send();

        if (!result.component1()) {
            return VerificationResponseDTO.builder()
                    .exists(false)
                    .historicalKeyValid(false)
                    .affectedByCompromise(false)
                    .status("NOT_FOUND")
                    .message("Este extracto não foi encontrado no registo blockchain.")
                    .build();
        }

        boolean historicalValid = result.component5();
        boolean affected = result.component6();

        String status;
        String message;

        if (!historicalValid) {
            status = "INVALID_KEY";
            message = "A chave utilizada não estava autorizada "
                    + "na data de registo deste extracto.";

        } else if (affected) {
            status = "UNDER_REVIEW";
            message = "A chave utilizada neste extracto foi "
                    + "associada a um incidente de segurança. "
                    + "Solicite confirmação ao banco emissor.";

        } else {
            status = "ANCHOR_VALID";
            message = "O registo do extracto e a validade "
                    + "histórica da chave foram confirmados.";
        }

        return VerificationResponseDTO.builder()
                .exists(true)
                .bankId(result.component2().longValueExact())
                .chaveOperacional(result.component3())
                .timestamp(result.component4().longValueExact())
                .historicalKeyValid(historicalValid)
                .affectedByCompromise(affected)
                .status(status)
                .message(message)
                .build();
    }

    private byte[] convertHexToBytes32(String hex) {
        String cleanHex = Numeric.cleanHexPrefix(hex);
        byte[] rawBytes = Numeric.hexStringToByteArray(cleanHex);
        if (rawBytes.length != 32) {
            throw new IllegalArgumentException(
                    "O hash SHA-256 deve conter exactamente 32 bytes."
            );
        }
        return rawBytes;
    }
}
