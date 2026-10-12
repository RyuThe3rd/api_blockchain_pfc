// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract BankRegistry {
    struct Bank {
        uint256 bankId;           // ID único da instituição (ex: 101, 102)
        string name;              // Nome da instituição (ex: "Banco A")
        address managementKey;    // Chave Mestre (Offline/Cofre) - Altera definições
        address operationalKey;   // Chave Operacional (API Spring Boot) - Assina extratos
        bool isActive;            // Estado de acesso do banco na rede
        uint256 expiresAt;        // Timestamp de expiração da chave operacional
    }

    // Struct simplificada para retornos em lote no Frontend / API
    struct BankSummary {
        uint256 bankId;
        string name;
        address operationalKey;
        bool isActive;
        uint256 expiresAt;
    }

    // Mapeamento: ID do Banco => Dados do Banco
    mapping(uint256 => Bank) public banks;

    // Mapeamento para rápida verificação: Chave Operacional => ID do Banco
    mapping(address => uint256) public operationalToBankId;

    // Array com todos os IDs de bancos registados (permite iteração)
    uint256[] public allBankIds;

    // Administrador do Consórcio (ex: Regulador / Banco Central)
    address public admin;

    event BankRegistered(uint256 indexed bankId, string name, address managementKey, address operationalKey);
    event OperationalKeyRotated(uint256 indexed bankId, address indexed oldKey, address indexed newKey, uint256 expiresAt);
    event BankRevoked(uint256 indexed bankId);

    modifier onlyAdmin() {
        require(msg.sender == admin, "Apenas o Administrador do Consorcio pode executar esta acao");
        _;
    }

    modifier onlyManagementOrAdmin(uint256 _bankId) {
        require(
            msg.sender == banks[_bankId].managementKey || msg.sender == admin,
            "Apenas a Chave Mestre do banco ou o Admin podem executar esta acao"
        );
        _;
    }

    constructor() {
        admin = msg.sender;
    }

    /**
     * @notice Regista uma nova instituição no consórcio (Onboarding).
     */
    function registerBank(
        uint256 _bankId,
        string memory _name,
        address _managementKey,
        address _operationalKey,
        uint256 _durationInSeconds
    ) external onlyAdmin {
        require(_bankId > 0, "ID do banco deve ser maior que zero");
        require(banks[_bankId].bankId == 0, "ID do banco ja existe");
        require(_managementKey != address(0) && _operationalKey != address(0), "Enderecos invalidos");
        require(operationalToBankId[_operationalKey] == 0, "Chave operacional ja esta em uso");

        uint256 expiration = block.timestamp + _durationInSeconds;

        banks[_bankId] = Bank({
            bankId: _bankId,
            name: _name,
            managementKey: _managementKey,
            operationalKey: _operationalKey,
            isActive: true,
            expiresAt: expiration
        });

        operationalToBankId[_operationalKey] = _bankId;
        allBankIds.push(_bankId);

        emit BankRegistered(_bankId, _name, _managementKey, _operationalKey);
    }

    /**
     * @notice Roda a Chave Operacional do Spring Boot se for exposta ou se expirar.
     */
    function rotateOperationalKey(
        uint256 _bankId,
        address _newOperationalKey,
        uint256 _newDurationInSeconds
    ) external onlyManagementOrAdmin(_bankId) {
        Bank storage bank = banks[_bankId];
        require(bank.isActive, "O banco encontra-se inativo");
        require(_newOperationalKey != address(0), "Endereco invalido");
        require(operationalToBankId[_newOperationalKey] == 0, "A nova chave ja esta atribuida a outro banco");

        delete operationalToBankId[bank.operationalKey];

        address oldKey = bank.operationalKey;
        bank.operationalKey = _newOperationalKey;
        bank.expiresAt = block.timestamp + _newDurationInSeconds;

        operationalToBankId[_newOperationalKey] = _bankId;

        emit OperationalKeyRotated(_bankId, oldKey, _newOperationalKey, bank.expiresAt);
    }

    /**
     * @notice Revoga o acesso do banco em caso de emergência.
     */
    function revokeBank(uint256 _bankId) external onlyManagementOrAdmin(_bankId) {
        Bank storage bank = banks[_bankId];
        require(bank.isActive, "O banco ja esta inativo");

        bank.isActive = false;
        delete operationalToBankId[bank.operationalKey];

        emit BankRevoked(_bankId);
    }

    // =========================================================================
    // FUNÇÕES DE CONSULTA (VIEW) - CUSTO DE GAS $0 OFF-CHAIN
    // =========================================================================

    /**
     * @notice Retorna o endereço da Chave Operacional com base no ID do Banco.
     */
    function getOperationalKey(uint256 _bankId) external view returns (address) {
        require(banks[_bankId].bankId != 0, "Banco nao encontrado");
        return banks[_bankId].operationalKey;
    }

    /**
     * @notice Retorna o nome da instituição com base no ID do Banco.
     */
    function getBankName(uint256 _bankId) external view returns (string memory) {
        require(banks[_bankId].bankId != 0, "Banco nao encontrado");
        return banks[_bankId].name;
    }

    /**
     * @notice Retorna todos os bancos registados (IDs, nomes, chaves e estados).
     * Ideal para popular o accordion no Frontend ou alimentar a cache do Spring Boot.
     */
    function getAllBanks() external view returns (BankSummary[] memory) {
        uint256 total = allBankIds.length;
        BankSummary[] memory summaries = new BankSummary[](total);

        for (uint256 i = 0; i < total; i++) {
            uint256 id = allBankIds[i];
            Bank memory bank = banks[id];
            summaries[i] = BankSummary({
                bankId: bank.bankId,
                name: bank.name,
                operationalKey: bank.operationalKey,
                isActive: bank.isActive,
                expiresAt: bank.expiresAt
            });
        }

        return summaries;
    }

    /**
     * @notice Consulta executada pelo StatementRegistry para validar quem envia a transação.
     */
    function validateOperationalKey(address _operationalKey) external view returns (bool isValid, uint256 bankId) {
        uint256 id = operationalToBankId[_operationalKey];
        if (id == 0) return (false, 0);

        Bank memory bank = banks[id];
        if (bank.isActive && block.timestamp <= bank.expiresAt && bank.operationalKey == _operationalKey) {
            return (true, id);
        }

        return (false, 0);
    }
}