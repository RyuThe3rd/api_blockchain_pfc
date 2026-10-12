package org.example.blockchain.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.8.0.
 */
@SuppressWarnings("rawtypes")
@Generated("org.web3j.codegen.SolidityFunctionWrapperGenerator")
public class StatementRegistry extends Contract {
    public static final String BINARY = "608060405234801561000f575f5ffd5b50604051610a82380380610a8283398181016040528101906100319190610142565b5f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361009f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610096906101ed565b60405180910390fd5b805f5f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505061020b565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610111826100e8565b9050919050565b61012181610107565b811461012b575f5ffd5b50565b5f8151905061013c81610118565b92915050565b5f60208284031215610157576101566100e4565b5b5f6101648482850161012e565b91505092915050565b5f82825260208201905092915050565b7f456e64657265636f20646f20726567697374726f2064652062616e636f7320695f8201527f6e76616c69646f00000000000000000000000000000000000000000000000000602082015250565b5f6101d760278361016d565b91506101e28261017d565b604082019050919050565b5f6020820190508181035f830152610204816101cb565b9050919050565b61086a806102185f395ff3fe608060405234801561000f575f5ffd5b506004361061004a575f3560e01c8063112f00531461004e578063498fc1c214610081578063dea2d702146100b4578063f436ad73146100d0575b5f5ffd5b6100686004803603810190610063919061049f565b6100ee565b604051610078949392919061053b565b60405180910390f35b61009b6004803603810190610096919061049f565b6101c3565b6040516100ab949392919061058d565b60405180910390f35b6100ce60048036038101906100c9919061049f565b61020e565b005b6100d8610444565b6040516100e5919061062b565b60405180910390f35b5f5f5f5f5f60015f8781526020019081526020015f206040518060800160405290815f820154815260200160018201548152602001600282015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160038201548152505090505f5f1b815f0151036101a1575f5f5f5f9450945094509450506101bc565b60018160200151826040015183606001519450945094509450505b9193509193565b6001602052805f5260405f205f91509050805f015490806001015490806002015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030154905084565b5f5f5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638ac9c93d336040518263ffffffff1660e01b81526004016102699190610644565b6040805180830381865afa158015610283573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102a791906106b1565b91509150816102eb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102e29061076f565b60405180910390fd5b5f5f1b60015f8581526020019081526020015f205f015414610342576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610339906107fd565b60405180910390fd5b60405180608001604052808481526020018281526020013373ffffffffffffffffffffffffffffffffffffffff1681526020014281525060015f8581526020019081526020015f205f820151815f0155602082015181600101556040820151816002015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301559050503373ffffffffffffffffffffffffffffffffffffffff1681847fce7237c01e668ae4b1adbf0d30d3fa4c1a79881793978cf193bc57e9dca4e21142604051610437919061081b565b60405180910390a4505050565b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f5ffd5b5f819050919050565b61047e8161046c565b8114610488575f5ffd5b50565b5f8135905061049981610475565b92915050565b5f602082840312156104b4576104b3610468565b5b5f6104c18482850161048b565b91505092915050565b5f8115159050919050565b6104de816104ca565b82525050565b5f819050919050565b6104f6816104e4565b82525050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610525826104fc565b9050919050565b6105358161051b565b82525050565b5f60808201905061054e5f8301876104d5565b61055b60208301866104ed565b610568604083018561052c565b61057560608301846104ed565b95945050505050565b6105878161046c565b82525050565b5f6080820190506105a05f83018761057e565b6105ad60208301866104ed565b6105ba604083018561052c565b6105c760608301846104ed565b95945050505050565b5f819050919050565b5f6105f36105ee6105e9846104fc565b6105d0565b6104fc565b9050919050565b5f610604826105d9565b9050919050565b5f610615826105fa565b9050919050565b6106258161060b565b82525050565b5f60208201905061063e5f83018461061c565b92915050565b5f6020820190506106575f83018461052c565b92915050565b610666816104ca565b8114610670575f5ffd5b50565b5f815190506106818161065d565b92915050565b610690816104e4565b811461069a575f5ffd5b50565b5f815190506106ab81610687565b92915050565b5f5f604083850312156106c7576106c6610468565b5b5f6106d485828601610673565b92505060206106e58582860161069d565b9150509250929050565b5f82825260208201905092915050565b7f41636573736f206e656761646f3a204368617665206f7065726163696f6e616c5f8201527f20696e76616c6964612c206578706972616461206f75207265766f6761646100602082015250565b5f610759603f836106ef565b9150610764826106ff565b604082019050919050565b5f6020820190508181035f8301526107868161074d565b9050919050565b7f45737465206578747261746f206a6120666f692072656769737461646f20616e5f8201527f746572696f726d656e7465000000000000000000000000000000000000000000602082015250565b5f6107e7602b836106ef565b91506107f28261078d565b604082019050919050565b5f6020820190508181035f830152610814816107db565b9050919050565b5f60208201905061082e5f8301846104ed565b9291505056fea2646970667358221220d136a4ec9a8a04806c9ed174db9d77b6a6ca4ccc3003623c218837c2bf27446464736f6c63430008220033";

    private static String librariesLinkedBinary;

    public static final String FUNC_BANKREGISTRY = "bankRegistry";

    public static final String FUNC_REGISTERSTATEMENT = "registerStatement";

    public static final String FUNC_STATEMENTS = "statements";

    public static final String FUNC_VERIFYSTATEMENT = "verifyStatement";

    public static final Event STATEMENTREGISTERED_EVENT = new Event("StatementRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected StatementRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected StatementRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StatementRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected StatementRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<StatementRegisteredEventResponse> getStatementRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STATEMENTREGISTERED_EVENT, transactionReceipt);
        ArrayList<StatementRegisteredEventResponse> responses = new ArrayList<StatementRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StatementRegisteredEventResponse typedResponse = new StatementRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.documentHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.chaveOperacional = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static StatementRegisteredEventResponse getStatementRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STATEMENTREGISTERED_EVENT, log);
        StatementRegisteredEventResponse typedResponse = new StatementRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.documentHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.chaveOperacional = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<StatementRegisteredEventResponse> statementRegisteredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getStatementRegisteredEventFromLog(log));
    }

    public Flowable<StatementRegisteredEventResponse> statementRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STATEMENTREGISTERED_EVENT));
        return statementRegisteredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> bankRegistry() {
        final Function function = new Function(FUNC_BANKREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerStatement(byte[] _documentHash) {
        final Function function = new Function(
                FUNC_REGISTERSTATEMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_documentHash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<byte[], BigInteger, String, BigInteger>> statements(
            byte[] param0) {
        final Function function = new Function(FUNC_STATEMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<byte[], BigInteger, String, BigInteger>>(function,
                new Callable<Tuple4<byte[], BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple4<byte[], BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<byte[], BigInteger, String, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<Boolean, BigInteger, String, BigInteger>> verifyStatement(
            byte[] _documentHash) {
        final Function function = new Function(FUNC_VERIFYSTATEMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_documentHash)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<Boolean, BigInteger, String, BigInteger>>(function,
                new Callable<Tuple4<Boolean, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple4<Boolean, BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<Boolean, BigInteger, String, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    @Deprecated
    public static StatementRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StatementRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static StatementRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StatementRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static StatementRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StatementRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StatementRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StatementRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<StatementRegistry> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _bankRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _bankRegistryAddress)));
        return deployRemoteCall(StatementRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<StatementRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String _bankRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _bankRegistryAddress)));
        return deployRemoteCall(StatementRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<StatementRegistry> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _bankRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _bankRegistryAddress)));
        return deployRemoteCall(StatementRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<StatementRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String _bankRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _bankRegistryAddress)));
        return deployRemoteCall(StatementRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class StatementRegisteredEventResponse extends BaseEventResponse {
        public byte[] documentHash;

        public BigInteger bankId;

        public String chaveOperacional;

        public BigInteger timestamp;
    }
}
