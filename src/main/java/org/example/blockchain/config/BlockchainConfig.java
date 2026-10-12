package org.example.blockchain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Slf4j
@Configuration
public class BlockchainConfig {

    // Lê BLOCKCHAIN_RPC_URL ou blockchain.rpc.url. Padrão: IP da tua VM Besu
    @Value("${blockchain.rpc-url:${blockchain.rpc.url:http://136.80.69.253:8545}}")
    private String rpcUrl;

    // Lê BLOCKCHAIN_ADMIN_PRIVATE_KEY
    @Value("${blockchain.admin-private-key:${blockchain.admin.private-key:}}")
    private String adminPrivateKey;

    @Value("${blockchain.gas.price:0}")
    private long gasPrice;

    @Value("${blockchain.gas.limit:4700000}")
    private long gasLimit;

    /**
     * Instância do Web3j para comunicação com o nó Besu
     */
    @Bean
    public Web3j web3j() {
        log.info("A inicializar ligacao Web3j para o no RPC: {}", rpcUrl);
        return Web3j.build(new HttpService(rpcUrl));
    }

    /**
     * Cria as Credenciais do Administrador/Operador.
     * Inclui salvaguarda para o container nunca crashar caso a chave venha vazia ou mal formatada.
     */
    @Bean
    public Credentials credentials() {
        // Chave dummy de segurança (64 caracteres hex) caso a real falhe
        String fallbackKey = "0x0000000000000000000000000000000000000000000000000000000000000001";

        try {
            if (adminPrivateKey == null || adminPrivateKey.trim().isEmpty()) {
                log.warn("ATENCAO: 'BLOCKCHAIN_ADMIN_PRIVATE_KEY' nao foi configurada. A usar chave dummy de salvaguarda.");
                return Credentials.create(fallbackKey);
            }

            // Se o utilizador não colocou '0x' no início, o Web3j precisa que a string tenha exatamente 64 chars
            String cleanKey = adminPrivateKey.trim();
            log.info("A carregar credenciais com a chave privada configurada...");
            return Credentials.create(cleanKey);

        } catch (Exception e) {
            log.error("Erro ao carregar a chave privada no arranque da aplicacao. A usar chave de reserva para manter a API ativa.", e);
            return Credentials.create(fallbackKey);
        }
    }

    /**
     * Provedor de Gas estático compatível com redes Besu QBFT/IBFT
     */
    @Bean
    public ContractGasProvider gasProvider() {
        return new StaticGasProvider(
                BigInteger.valueOf(gasPrice),
                BigInteger.valueOf(gasLimit)
        );
    }
}