package org.example.blockchain.services;

import org.example.blockchain.models.BankRegistry;
import org.example.blockchain.models.DocumentProof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockchainService {

    private static final Logger logger = LoggerFactory.getLogger(BlockchainService.class);

    private final SmartContractService smartContractService;
    private final CryptoService cryptoService;
    private final BankRegistryService bankRegistryService; // declaração do campo

    @Autowired
    public BlockchainService(SmartContractService smartContractService,
                             CryptoService cryptoService,
                             BankRegistryService bankRegistryService) {
        this.smartContractService = smartContractService;
        this.cryptoService = cryptoService;
        this.bankRegistryService = bankRegistryService;
    }

    public String anchorDocument(String documentHash, String bankId, String signature, Long timestamp) throws Exception {
        // valida assinatura
        boolean valid = cryptoService.verifySignature(documentHash, bankId, signature);
        if (!valid) {
            throw new SecurityException("Assinatura inválida para o banco " + bankId);
        }

        // busca o banco no registro
        BankRegistry bank = bankRegistryService.findByBankId(bankId);
        if (bank == null) {
            throw new IllegalArgumentException("Banco não encontrado: " + bankId);
        }
        String issuerAddress = bank.getBlockchainAddress();

        // envia para a blockchain
        String txHash = smartContractService.storeProof(documentHash, issuerAddress, timestamp);

        logger.info("Documento ancorado com sucesso. TxHash: {}", txHash);
        return txHash;
    }

    public DocumentProof verifyDocument(String documentHash) throws Exception {
        DocumentProof proof = smartContractService.verifyProof(documentHash);
        if (proof != null) {
            logger.info("Hash {} encontrado.", documentHash);
        } else {
            logger.warn("Hash {} não encontrado.", documentHash);
        }
        return proof;
    }
}