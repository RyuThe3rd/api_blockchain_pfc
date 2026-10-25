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

    /**
    Se uma chave privada ser comprometida,
    *mesmo que a assinatura dos extratos emitidos seja válida,
    *a chave operacional utilizada para ancoragem de extratos no intervalo em que se acredita que a chave privada foi descoberta por terceiros,
    //consequentemente fica comprometida
    */
    struct KeyCompromise {
        uint256 suspectedFrom;
        uint256 reportedAt;
        bool exists;
    }

    mapping(address => KeyCompromise) public keyCompromises;

    event KeyCompromiseReported(
        uint256 indexed bankId,
        address indexed operationalKey,
        uint256 suspectedFrom,
        uint256 reportedAt
    );


    mapping(uint256 => Bank) public banks;
    mapping(address => uint256) public operationalToBankId;

    //Histórico de chaves (Endereço da chave => Registo)
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
        require(!keyHistory[_operationalKey].exists, "Chave operacional ja utilizada anteriormente");
        require(_durationInSeconds > 0, "Duracao de validade invalida");

        uint256 expiration = block.timestamp + _durationInSeconds;

        banks[_bankId] = Bank(_bankId, _name, _managementKey, _operationalKey, true, expiration);
        operationalToBankId[_operationalKey] = _bankId;

        // Guarda no histórico
        keyHistory[_operationalKey] = KeyRecord(_bankId, block.timestamp, expiration, true);

        allBankIds.push(_bankId);
        emit BankRegistered(_bankId, _name, _managementKey, _operationalKey);
    }


    /**
     * @notice Rotação normal: expiração ou renovação preventiva.
     */
    function rotateOperationalKey(
        uint256 _bankId,
        address _newOperationalKey,
        uint256 _newDurationInSeconds
    ) external onlyManagementOrAdmin(_bankId) {

        _rotateOperationalKey(
            _bankId,
            _newOperationalKey,
            _newDurationInSeconds
        );
    }

    /**
     * @notice Rotação e comunicação de comprometimento.
     * Ambas as operações ocorrem numa única transacção.
     */
    function rotateAndReportCompromise(
        uint256 _bankId,
        address _newOperationalKey,
        uint256 _newDurationInSeconds,
        uint256 _suspectedFrom
    ) external onlyManagementOrAdmin(_bankId) {

        // Guarda a identidade da chave comprometida.
        address oldKey = banks[_bankId].operationalKey;

        // Substitui a chave operacional.
        _rotateOperationalKey(
            _bankId,
            _newOperationalKey,
            _newDurationInSeconds
        );

        // Consulta o histórico da chave antiga.
        KeyRecord storage record = keyHistory[oldKey];

        require(
            _suspectedFrom >= record.validFrom &&
            _suspectedFrom < record.validUntil &&
            _suspectedFrom <= block.timestamp,
            "Periodo suspeito invalido"
        );

        require(
            !keyCompromises[oldKey].exists,
            "Incidente ja registado"
        );

        // Regista o incidente.
        keyCompromises[oldKey] = KeyCompromise({
            suspectedFrom: _suspectedFrom,
            reportedAt: block.timestamp,
            exists: true
        });

        emit KeyCompromiseReported(
            _bankId,
            oldKey,
            _suspectedFrom,
            block.timestamp
        );
    }

    /**
     * @notice Lógica comum de rotação.
     * Só pode ser chamada internamente.
     */
    function _rotateOperationalKey(
        uint256 _bankId,
        address _newOperationalKey,
        uint256 _newDurationInSeconds
    ) internal {

        Bank storage bank = banks[_bankId];

        require(bank.bankId != 0, "Banco inexistente");
        require(bank.isActive, "Banco inactivo");

        require(
            _newOperationalKey != address(0),
            "Endereco invalido"
        );

        require(
            operationalToBankId[_newOperationalKey] == 0,
            "Chave ja atribuida"
        );

        require(
            !keyHistory[_newOperationalKey].exists,
            "Chave utilizada anteriormente"
        );

        require(
            _newDurationInSeconds > 0,
            "Duracao invalida"
        );

        address oldKey = bank.operationalKey;

        // Fecha o histórico da chave antiga,
        // sem prolongar uma validade já expirada.
        KeyRecord storage oldRecord = keyHistory[oldKey];

        if (block.timestamp < oldRecord.validUntil) {
            oldRecord.validUntil = block.timestamp;
        }

        // Remove a autorização operacional antiga.
        delete operationalToBankId[oldKey];

        // Configura a nova chave.
        uint256 expiration =
            block.timestamp + _newDurationInSeconds;

        bank.operationalKey = _newOperationalKey;
        bank.expiresAt = expiration;

        operationalToBankId[_newOperationalKey] = _bankId;

        // Regista o novo intervalo histórico.
        keyHistory[_newOperationalKey] = KeyRecord({
            bankId: _bankId,
            validFrom: block.timestamp,
            validUntil: expiration,
            exists: true
        });

        emit OperationalKeyRotated(
            _bankId,
            oldKey,
            _newOperationalKey,
            expiration
        );
    }


    function revokeBank(uint256 _bankId) external onlyManagementOrAdmin(_bankId) {
        Bank storage bank = banks[_bankId];
        require(bank.isActive, "Banco inativo");
        bank.isActive = false;

        // Fecha a validade da chave atual no histórico
        KeyRecord storage record = keyHistory[bank.operationalKey];

        if (block.timestamp < record.validUntil) {
            record.validUntil = block.timestamp;
        }

        delete operationalToBankId[bank.operationalKey];
        emit BankRevoked(_bankId);
    }

    /**
     * @notice Suspende o banco e comunica o comprometimento
     * da sua chave operacional numa única transacção.
     *
     * @param _bankId Identificador do banco.
     * @param _suspectedFrom Timestamp UNIX do início suspeito
     *                       do comprometimento.
     */
    function revokeBankAndReportCompromise(
        uint256 _bankId,
        uint256 _suspectedFrom
    ) external onlyManagementOrAdmin(_bankId) {

        Bank storage bank = banks[_bankId];

        require(bank.bankId != 0, "Banco inexistente");
        require(bank.isActive, "Banco ja esta inactivo");

        // Identificar a chave comprometida.
        address compromisedKey = bank.operationalKey;

        KeyRecord storage record = keyHistory[compromisedKey];

        require(
            record.exists && record.bankId == _bankId,
            "Historico da chave invalido"
        );

        require(
            !keyCompromises[compromisedKey].exists,
            "Incidente ja registado"
        );

        // A chave pode ter expirado antes da revogacao.
        // Nunca prolongar retroactivamente a validade.
        uint256 endOfValidity = block.timestamp;

        if (record.validUntil < endOfValidity) {
            endOfValidity = record.validUntil;
        }

        // O inicio suspeito deve pertencer ao periodo
        // em que a chave estava historicamente autorizada.
        require(
            _suspectedFrom >= record.validFrom &&
            _suspectedFrom < endOfValidity,
            "Periodo suspeito invalido"
        );

        // 1. Suspender o banco.
        bank.isActive = false;

        // 2. Encerrar a validade historica da chave.
        record.validUntil = endOfValidity;

        // Remover a chave do indice operacional.
        delete operationalToBankId[compromisedKey];

        // 3. Registar o comprometimento.
        keyCompromises[compromisedKey] = KeyCompromise({
            suspectedFrom: _suspectedFrom,
            reportedAt: block.timestamp,
            exists: true
        });

        // Registar ambos os acontecimentos.
        emit BankRevoked(_bankId);

        emit KeyCompromiseReported(
            _bankId,
            compromisedKey,
            _suspectedFrom,
            block.timestamp
        );
    }


    event BankReactivated(
        uint256 indexed bankId,
        address indexed newOperationalKey
    );

    function reactivateBank(
        uint256 _bankId,
        address _newOperationalKey,
        uint256 _durationInSeconds
    ) external onlyManagementOrAdmin(_bankId) {

        Bank storage bank = banks[_bankId];

        require(bank.bankId != 0, "Banco nao encontrado");
        require(!bank.isActive, "Banco ja esta activo");

        require(
            _newOperationalKey != address(0),
            "Endereco invalido"
        );

        require(
            _durationInSeconds > 0,
            "Duracao invalida"
        );

        require(
            operationalToBankId[_newOperationalKey] == 0,
            "Chave ja atribuida"
        );

        require(
            !keyHistory[_newOperationalKey].exists,
            "Chave utilizada anteriormente"
        );

        uint256 expiration =
            block.timestamp + _durationInSeconds;

        bank.operationalKey = _newOperationalKey;
        bank.expiresAt = expiration;
        bank.isActive = true;

        operationalToBankId[_newOperationalKey] = _bankId;

        keyHistory[_newOperationalKey] = KeyRecord({
            bankId: _bankId,
            validFrom: block.timestamp,
            validUntil: expiration,
            exists: true
        });

        emit BankReactivated(_bankId, _newOperationalKey);
    }

    function reportKeyCompromise(
        uint256 _bankId,
        address _key,
        uint256 _suspectedFrom
    ) external onlyManagementOrAdmin(_bankId) {

        KeyRecord storage record = keyHistory[_key];

        require(record.exists, "Chave desconhecida");
        require(record.bankId == _bankId, "Chave de outro banco");

        // Nesta versao, exige que a chave ja tenha sido retirada
        // do conjunto de chaves operacionais activas.
        require(
            operationalToBankId[_key] == 0,
            "Desactive ou rode a chave antes de comunicar"
        );

        require(
            _suspectedFrom >= record.validFrom &&
            _suspectedFrom < record.validUntil &&
            _suspectedFrom <= block.timestamp,
            "Inicio suspeito invalido"
        );
        KeyCompromise storage incident = keyCompromises[_key];

        if (incident.exists) {
            require(
                _suspectedFrom < incident.suspectedFrom,
                "A nova data deve ser anterior a data registada"
            );

            // Alarga o periodo suspeito, sem apagar
            // a data da primeira comunicacao.
            incident.suspectedFrom = _suspectedFrom;
        } else {
            keyCompromises[_key] = KeyCompromise({
                suspectedFrom: _suspectedFrom,
                reportedAt: block.timestamp,
                exists: true
            });
        }

        emit KeyCompromiseReported(
            _bankId,
            _key,
            _suspectedFrom,
            block.timestamp
        );
    }

    /**
     * @notice Verifica se uma chave operacional está
     * actualmente autorizada a ancorar extractos.
     *
     * @return isValid Indica se a chave está autorizada.
     * @return bankId Identificador do banco associado.
     */
    function validateOperationalKey(
        address _operationalKey
    ) external view returns (bool isValid, uint256 bankId) {

        uint256 id = operationalToBankId[_operationalKey];

        if (id == 0) {
            return (false, 0);
        }

        Bank storage bank = banks[id];

       if( isAffectedByCompromise(_operationalKey, block.timestamp)){

            return (false, id);
       }

        if (
            bank.isActive &&
            block.timestamp < bank.expiresAt &&
            bank.operationalKey == _operationalKey
        ) {
            return (true, id);
        }

        return (false, 0);
    }

    // ==========================================
    // NOVA FUNÇÃO: Validação Histórica (Teste D)
    // ==========================================
    function validateHistoricalKey(address _key, uint256 _timestamp) external view returns (bool) {
        KeyRecord memory record = keyHistory[_key];
        if (!record.exists) return false;

        // A chave era válida naquele exato momento?
        return (_timestamp >= record.validFrom && _timestamp < record.validUntil);
    }

    function isAffectedByCompromise(
        address _key,
        uint256 _timestamp
    ) public view returns (bool) {

        KeyCompromise memory incident = keyCompromises[_key];

        if (!incident.exists) {
            return false;
        }

        return _timestamp >= incident.suspectedFrom;
    }

    function verifyHistoricalStatus(
        address _key,
        uint256 _bankId,
        uint256 _timestamp
    ) external view returns (
        bool historicalValid,
        bool affectedByCompromise
    ) {
        KeyRecord storage record = keyHistory[_key];

        historicalValid = (
            record.exists &&
            record.bankId == _bankId &&
            _timestamp >= record.validFrom &&
            _timestamp < record.validUntil
        );

        KeyCompromise storage incident = keyCompromises[_key];

        affectedByCompromise = (
            incident.exists &&
            _timestamp >= incident.suspectedFrom
        );
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