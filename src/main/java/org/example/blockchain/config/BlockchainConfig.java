package org.example.blockchain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;

@Configuration
public class BlockchainConfig {

    @Value("${blockchain.rpc.url:http://localhost:8545}")
    private String rpcUrl;

    @Value("${blockchain.gas.price:0}")
    private long gasPrice;

    @Value("${blockchain.gas.limit:3000000}")
    private long gasLimit;

    // =============================================
    // PERFIL DEV: usa uma chave privada fixa (teste)
    // =============================================
    @Bean
    @Profile("dev")
    public Credentials devCredentials() {
        // Esta é uma chave privada de exemplo (primeira conta do Ganache)
        // Em desenvolvimento, você pode substituir pela sua chave de teste
        String privateKey = "0x4f3edf983ac636a65a842ce7c78d9aa706d3b113bce9c46f30d7d21715b23b1d";
        return Credentials.create(privateKey);
    }

    // =============================================
    // PERFIL PROD: carrega do arquivo keystore
    // =============================================
    @Bean
    @Profile("prod")
    public Credentials prodCredentials(
            @Value("${blockchain.wallet.file}") String walletFile,
            @Value("${blockchain.wallet.password}") String password) throws Exception {

        File file = new File(walletFile);
        if (!file.exists()) {
            throw new IllegalStateException("Arquivo de carteira não encontrado: " + walletFile);
        }
        return WalletUtils.loadCredentials(password, walletFile);
    }

    // =============================================
    // Bean do Web3j (comum a todos os perfis)
    // =============================================
    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(rpcUrl));
    }

    // (Opcional) Beans para gas provider, se necessário
    // @Bean
    // public ContractGasProvider gasProvider() { ... }
}