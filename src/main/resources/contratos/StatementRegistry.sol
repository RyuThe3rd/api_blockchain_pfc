// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

interface IBankRegistry {
    function validateOperationalKey(address _operationalKey) external view returns (bool isValid, uint256 bankId);
}

contract StatementRegistry {
    IBankRegistry public bankRegistry;

    struct StatementRecord {
        bytes32 documentHash;      // Hash SHA-256 do extrato bancário
        uint256 bankId;            // ID permanente do banco (ex: 101)
        address chaveOperacional;  // Chave que assinou este registo específico
        uint256 timestamp;         // Momento exato do registo
    }

    mapping(bytes32 => StatementRecord) public statements;

    event StatementRegistered(
        bytes32 indexed documentHash,
        uint256 indexed bankId,
        address indexed chaveOperacional,
        uint256 timestamp
    );

    constructor(address _bankRegistryAddress) {
        require(_bankRegistryAddress != address(0), "Endereco do registro de bancos invalido");
        bankRegistry = IBankRegistry(_bankRegistryAddress);
    }

    function registerStatement(bytes32 _documentHash) external {
        (bool isValid, uint256 bankId) = bankRegistry.validateOperationalKey(msg.sender);
        require(isValid, "Acesso negado: Chave operacional invalida, expirada ou revogada");
        require(statements[_documentHash].documentHash == 0, "Este extrato ja foi registado anteriormente");

        statements[_documentHash] = StatementRecord({
            documentHash: _documentHash,
            bankId: bankId,
            chaveOperacional: msg.sender,
            timestamp: block.timestamp
        });

        emit StatementRegistered(_documentHash, bankId, msg.sender, block.timestamp);
    }

    function verifyStatement(bytes32 _documentHash) external view returns (
        bool exists,
        uint256 bankId,
        address chaveOperacional,
        uint256 timestamp
    ) {
        StatementRecord memory record = statements[_documentHash];
        if (record.documentHash == 0) {
            return (false, 0, address(0), 0);
        }
        return (true, record.bankId, record.chaveOperacional, record.timestamp);
    }
}