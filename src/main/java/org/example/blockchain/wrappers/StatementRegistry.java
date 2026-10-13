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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610ad9380380610ad98339818101604052810190610032919061014a565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036100a1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610098906101fa565b60405180910390fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505061021a565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610117826100ec565b9050919050565b6101278161010c565b811461013257600080fd5b50565b6000815190506101448161011e565b92915050565b6000602082840312156101605761015f6100e7565b5b600061016e84828501610135565b91505092915050565b600082825260208201905092915050565b7f456e64657265636f20646f20726567697374726f2064652062616e636f73206960008201527f6e76616c69646f00000000000000000000000000000000000000000000000000602082015250565b60006101e4602783610177565b91506101ef82610188565b604082019050919050565b60006020820190508181036000830152610213816101d7565b9050919050565b6108b0806102296000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063112f005314610051578063498fc1c214610084578063dea2d702146100b7578063f436ad73146100d3575b600080fd5b61006b600480360381019061006691906104c2565b6100f1565b60405161007b9493929190610564565b60405180910390f35b61009e600480360381019061009991906104c2565b6101d1565b6040516100ae94939291906105b8565b60405180910390f35b6100d160048036038101906100cc91906104c2565b610221565b005b6100db610463565b6040516100e8919061065c565b60405180910390f35b60008060008060006001600087815260200190815260200160002060405180608001604052908160008201548152602001600182015481526020016002820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160038201548152505090506000801b8160000151036101af576000806000809450945094509450506101ca565b60018160200151826040015183606001519450945094509450505b9193509193565b60016020528060005260406000206000915090508060000154908060010154908060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030154905084565b60008060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638ac9c93d336040518263ffffffff1660e01b815260040161027d9190610677565b6040805180830381865afa158015610299573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102bd91906106ea565b9150915081610301576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102f8906107ad565b60405180910390fd5b6000801b60016000858152602001908152602001600020600001541461035c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103539061083f565b60405180910390fd5b60405180608001604052808481526020018281526020013373ffffffffffffffffffffffffffffffffffffffff1681526020014281525060016000858152602001908152602001600020600082015181600001556020820151816001015560408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301559050503373ffffffffffffffffffffffffffffffffffffffff1681847fce7237c01e668ae4b1adbf0d30d3fa4c1a79881793978cf193bc57e9dca4e21142604051610456919061085f565b60405180910390a4505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600080fd5b6000819050919050565b61049f8161048c565b81146104aa57600080fd5b50565b6000813590506104bc81610496565b92915050565b6000602082840312156104d8576104d7610487565b5b60006104e6848285016104ad565b91505092915050565b60008115159050919050565b610504816104ef565b82525050565b6000819050919050565b61051d8161050a565b82525050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061054e82610523565b9050919050565b61055e81610543565b82525050565b600060808201905061057960008301876104fb565b6105866020830186610514565b6105936040830185610555565b6105a06060830184610514565b95945050505050565b6105b28161048c565b82525050565b60006080820190506105cd60008301876105a9565b6105da6020830186610514565b6105e76040830185610555565b6105f46060830184610514565b95945050505050565b6000819050919050565b600061062261061d61061884610523565b6105fd565b610523565b9050919050565b600061063482610607565b9050919050565b600061064682610629565b9050919050565b6106568161063b565b82525050565b6000602082019050610671600083018461064d565b92915050565b600060208201905061068c6000830184610555565b92915050565b61069b816104ef565b81146106a657600080fd5b50565b6000815190506106b881610692565b92915050565b6106c78161050a565b81146106d257600080fd5b50565b6000815190506106e4816106be565b92915050565b6000806040838503121561070157610700610487565b5b600061070f858286016106a9565b9250506020610720858286016106d5565b9150509250929050565b600082825260208201905092915050565b7f41636573736f206e656761646f3a204368617665206f7065726163696f6e616c60008201527f20696e76616c6964612c206578706972616461206f75207265766f6761646100602082015250565b6000610797603f8361072a565b91506107a28261073b565b604082019050919050565b600060208201905081810360008301526107c68161078a565b9050919050565b7f45737465206578747261746f206a6120666f692072656769737461646f20616e60008201527f746572696f726d656e7465000000000000000000000000000000000000000000602082015250565b6000610829602b8361072a565b9150610834826107cd565b604082019050919050565b600060208201905081810360008301526108588161081c565b9050919050565b60006020820190506108746000830184610514565b9291505056fea26469706673582212202483fecbd06a5c8a1c472c2f7eb0bcb76d979e9c754dae4166d1b5b37888afca64736f6c63430008140033";

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
