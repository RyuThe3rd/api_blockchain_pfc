// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract BankRegistry {
    struct Bank {
        uint256 bankId;
        string name;
        address managementKey;
        address operationalKey;
        bool isActive;
        uint256 expiresAt;
    }

    struct BankSummary {
        uint256 bankId;
        string name;
        address operationalKey;
        bool isActive;
        uint256 expiresAt;
    }

    // NOVO: Estrutura para guardar o histórico de cada chave
    struct KeyRecord {
        uint256 bankId;
        uint256 validFrom;
        uint256 validUntil;
        bool exists;
    }

    mapping(uint256 => Bank) public banks;
    mapping(address => uint256) public operationalToBankId;

    // NOVO: Histórico de chaves (Endereço da chave => Registo)
    mapping(address => KeyRecord) public keyHistory;

    uint256[] public allBankIds;
    address public admin;

    event BankRegistered(uint256 indexed bankId, string name, address managementKey, address operationalKey);
    event OperationalKeyRotated(uint256 indexed bankId, address indexed oldKey, address indexed newKey, uint256 expiresAt);
    event BankRevoked(uint256 indexed bankId);

    modifier onlyAdmin() {
        require(msg.sender == admin, "Apenas o Admin");
        _;
    }

    modifier onlyManagementOrAdmin(uint256 _bankId) {
        require(msg.sender == banks[_bankId].managementKey || msg.sender == admin, "Apenas Chave Mestre ou Admin");
        _;
    }

    constructor() {
        admin = msg.sender;
    }

    function registerBank(
        uint256 _bankId,
        string memory _name,
        address _managementKey,
        address _operationalKey,
        uint256 _durationInSeconds
    ) external onlyAdmin {
        require(_bankId > 0 && banks[_bankId].bankId == 0, "ID invalido");
        require(_managementKey != address(0) && _operationalKey != address(0), "Endereco invalido");
        require(operationalToBankId[_operationalKey] == 0, "Chave ja em uso");

        uint256 expiration = block.timestamp + _durationInSeconds;

        banks[_bankId] = Bank(_bankId, _name, _managementKey, _operationalKey, true, expiration);
        operationalToBankId[_operationalKey] = _bankId;

        // Guarda no histórico
        keyHistory[_operationalKey] = KeyRecord(_bankId, block.timestamp, expiration, true);

        allBankIds.push(_bankId);
        emit BankRegistered(_bankId, _name, _managementKey, _operationalKey);
    }

    function rotateOperationalKey(
        uint256 _bankId,
        address _newOperationalKey,
        uint256 _newDurationInSeconds
    ) external onlyManagementOrAdmin(_bankId) {
        Bank storage bank = banks[_bankId];
        require(bank.isActive && _newOperationalKey != address(0), "Estado ou endereco invalido");
        require(operationalToBankId[_newOperationalKey] == 0, "Nova chave ja atribuida");

        // 1. Atualiza o histórico da chave ANTIGA (fecha a validade para agora)
        keyHistory[bank.operationalKey].validUntil = block.timestamp;

        // 2. Remove do índice ativo
        delete operationalToBankId[bank.operationalKey];

        // 3. Atualiza para a nova chave
        address oldKey = bank.operationalKey;
        bank.operationalKey = _newOperationalKey;
        bank.expiresAt = block.timestamp + _newDurationInSeconds;

        operationalToBankId[_newOperationalKey] = _bankId;

        // 4. Guarda a NOVA chave no histórico
        keyHistory[_newOperationalKey] = KeyRecord(_bankId, block.timestamp, bank.expiresAt, true);

        emit OperationalKeyRotated(_bankId, oldKey, _newOperationalKey, bank.expiresAt);
    }

    function revokeBank(uint256 _bankId) external onlyManagementOrAdmin(_bankId) {
        Bank storage bank = banks[_bankId];
        require(bank.isActive, "Banco inativo");
        bank.isActive = false;

        // Fecha a validade da chave atual no histórico
        keyHistory[bank.operationalKey].validUntil = block.timestamp;

        delete operationalToBankId[bank.operationalKey];
        emit BankRevoked(_bankId);
    }

    // ==========================================
    // NOVA FUNÇÃO: Validação Histórica (Teste D)
    // ==========================================
    function validateHistoricalKey(address _key, uint256 _timestamp) external view returns (bool) {
        KeyRecord memory record = keyHistory[_key];
        if (!record.exists) return false;

        // A chave era válida naquele exato momento?
        return (_timestamp >= record.validFrom && _timestamp <= record.validUntil);
    }

    // Funções view existentes mantêm-se (getAllBanks, getOperationalKey, etc...)
    function getAllBanks() external view returns (BankSummary[] memory) {
        BankSummary[] memory summaries = new BankSummary[](allBankIds.length);
        for (uint256 i = 0; i < allBankIds.length; i++) {
            Bank memory b = banks[allBankIds[i]];
            summaries[i] = BankSummary(b.bankId, b.name, b.operationalKey, b.isActive, b.expiresAt);
        }
        return summaries;
    }

    function getOperationalKey(uint256 _bankId) external view returns (address) {
        return banks[_bankId].operationalKey;
    }
}