package org.example.blockchain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blockchain.dto.DeploymentResponse;
import org.example.blockchain.dto.FullDeploymentResponse;
import org.example.blockchain.wrappers.BankRegistry;
import org.example.blockchain.wrappers.StatementRegistry;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractDeploymentService {

    private final Web3j web3j;
    private final Credentials adminCredentials;
    private final ContractGasProvider gasProvider;

    public DeploymentResponse deployBankRegistry() throws Exception {
        log.info("A iniciar o deploy do BankRegistry...");

        BankRegistry bankRegistry = BankRegistry.deploy(
                web3j,
                adminCredentials,
                gasProvider
        ).send();

        String contractAddress = bankRegistry.getContractAddress();
        String txHash = bankRegistry.getTransactionReceipt()
                .map(receipt -> receipt.getTransactionHash())
                .orElse("N/A");

        log.info("BankRegistry implantado com sucesso no endereço: {}", contractAddress);

        return DeploymentResponse.builder()
                .contractName("BankRegistry")
                .contractAddress(contractAddress)
                .transactionHash(txHash)
                .deployedBy(adminCredentials.getAddress())
                .build();
    }

    public DeploymentResponse deployStatementRegistry(String bankRegistryAddress) throws Exception {
        log.info("A iniciar o deploy do StatementRegistry com BankRegistry em {}...", bankRegistryAddress);

        StatementRegistry statementRegistry = StatementRegistry.deploy(
                web3j,
                adminCredentials,
                gasProvider,
                bankRegistryAddress
        ).send();

        String contractAddress = statementRegistry.getContractAddress();
        String txHash = statementRegistry.getTransactionReceipt()
                .map(receipt -> receipt.getTransactionHash())
                .orElse("N/A");

        log.info("StatementRegistry implantado com sucesso no endereço: {}", contractAddress);

        return DeploymentResponse.builder()
                .contractName("StatementRegistry")
                .contractAddress(contractAddress)
                .transactionHash(txHash)
                .deployedBy(adminCredentials.getAddress())
                .build();
    }

    public FullDeploymentResponse deployAll() throws Exception {
        log.info("A iniciar deploy em cadeia do ecossistema de contratos...");

        DeploymentResponse bankRegResponse = deployBankRegistry();
        DeploymentResponse stmtRegResponse = deployStatementRegistry(bankRegResponse.getContractAddress());

        return FullDeploymentResponse.builder()
                .bankRegistry(bankRegResponse)
                .statementRegistry(stmtRegResponse)
                .build();
    }
}