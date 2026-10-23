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
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
import org.web3j.tuples.generated.Tuple6;
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
public class BankRegistry extends Contract {
    public static final String BINARY = "6080604052348015600f57600080fd5b5033600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550612269806100606000396000f3fe608060405234801561001057600080fd5b50600436106100a85760003560e01c8063951c8a0711610071578063951c8a071461018e578063a0bfad70146101ac578063c3583041146101dc578063e05887181461020f578063e44243241461022b578063f851a44014610247576100a8565b806285ae2b146100ad57806301b98524146100e2578063033419601461011257806327cc3477146101425780636f7e8adc1461015e575b600080fd5b6100c760048036038101906100c29190611463565b610265565b6040516100d99695949392919061158b565b60405180910390f35b6100fc60048036038101906100f7919061161f565b610376565b604051610109919061165f565b60405180910390f35b61012c60048036038101906101279190611463565b610433565b604051610139919061167a565b60405180910390f35b61015c600480360381019061015791906117ca565b610472565b005b61017860048036038101906101739190611861565b61093f565b604051610185919061188e565b60405180910390f35b610196610957565b6040516101a39190611a58565b60405180910390f35b6101c660048036038101906101c19190611463565b610bf9565b6040516101d3919061188e565b60405180910390f35b6101f660048036038101906101f19190611861565b610c1d565b6040516102069493929190611a7a565b60405180910390f35b61022960048036038101906102249190611abf565b610c5a565b005b61024560048036038101906102409190611463565b611128565b005b61024f6113ac565b60405161025c919061167a565b60405180910390f35b600060205280600052604060002060009150905080600001549080600101805461028e90611b41565b80601f01602080910402602001604051908101604052809291908181526020018280546102ba90611b41565b80156103075780601f106102dc57610100808354040283529160200191610307565b820191906000526020600020905b8154815290600101906020018083116102ea57829003601f168201915b5050505050908060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030160149054906101000a900460ff16908060040154905086565b600080600260008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206040518060800160405290816000820154815260200160018201548152602001600282015481526020016003820160009054906101000a900460ff1615151515815250509050806060015161041157600091505061042d565b80602001518310158015610429575080604001518311155b9150505b92915050565b600080600083815260200190815260200160002060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610502576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104f990611bbe565b60405180910390fd5b6000851180156105275750600080600087815260200190815260200160002060000154145b610566576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161055d90611c2a565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16141580156105d05750600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b61060f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161060690611c96565b60405180910390fd5b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205414610691576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161068890611d02565b60405180910390fd5b6000814261069f9190611d51565b90506040518060c001604052808781526020018681526020018573ffffffffffffffffffffffffffffffffffffffff1681526020018473ffffffffffffffffffffffffffffffffffffffff1681526020016001151581526020018281525060008088815260200190815260200160002060008201518160000155602082015181600101908161072e9190611f44565b5060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160030160146101000a81548160ff02191690831515021790555060a0820151816004015590505085600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550604051806080016040528087815260200142815260200182815260200160011515815250600260008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083151502179055509050506003869080600181540180825580915050600190039060005260206000200160009091909190915055857f25d45502b4a3c4393a0ae2d0405cc2043dcffbbe8f8e0edf2cbae23a1556e02486868660405161092f93929190612016565b60405180910390a2505050505050565b60016020528060005260406000206000915090505481565b6060600060038054905067ffffffffffffffff81111561097a5761097961169f565b5b6040519080825280602002602001820160405280156109b357816020015b6109a06113d2565b8152602001906001900390816109985790505b50905060005b600380549050811015610bf1576000806000600384815481106109df576109de612054565b5b906000526020600020015481526020019081526020016000206040518060c001604052908160008201548152602001600182018054610a1d90611b41565b80601f0160208091040260200160405190810160405280929190818152602001828054610a4990611b41565b8015610a965780601f10610a6b57610100808354040283529160200191610a96565b820191906000526020600020905b815481529060010190602001808311610a7957829003601f168201915b505050505081526020016002820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016003820160149054906101000a900460ff1615151515815260200160048201548152505090506040518060a001604052808260000151815260200182602001518152602001826060015173ffffffffffffffffffffffffffffffffffffffff1681526020018260800151151581526020018260a00151815250838381518110610bd857610bd7612054565b5b60200260200101819052505080806001019150506109b9565b508091505090565b60038181548110610c0957600080fd5b906000526020600020016000915090505481565b60026020528060005260406000206000915090508060000154908060010154908060020154908060030160009054906101000a900460ff16905084565b8260008082815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610d175750600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b610d56576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d4d906120cf565b60405180910390fd5b600080600086815260200190815260200160002090508060030160149054906101000a900460ff168015610db75750600073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1614155b610df6576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ded9061213b565b60405180910390fd5b6000600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205414610e78576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e6f906121a7565b60405180910390fd5b42600260008360030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020181905550600160008260030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000905560008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050848260030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508342610fc29190611d51565b826004018190555085600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060405180608001604052808781526020014281526020018360040154815260200160011515815250600260008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083151502179055509050508473ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16877fb8abba1af5b8a780acbd88555fb97e43952780421e108ded1e3bccd576b16c4c8560040154604051611118919061188e565b60405180910390a4505050505050565b8060008082815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806111e55750600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b611224576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161121b906120cf565b60405180910390fd5b600080600084815260200190815260200160002090508060030160149054906101000a900460ff1661128b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161128290612213565b60405180910390fd5b60008160030160146101000a81548160ff02191690831515021790555042600260008360030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020181905550600160008260030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009055827f0edd4f48c5940002a834fe3979b412b3845397005cbcb559ec1bb8547dc46a9c60405160405180910390a2505050565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6040518060a001604052806000815260200160608152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600015158152602001600081525090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b6114408161142d565b811461144b57600080fd5b50565b60008135905061145d81611437565b92915050565b60006020828403121561147957611478611423565b5b60006114878482850161144e565b91505092915050565b6114998161142d565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156114d95780820151818401526020810190506114be565b60008484015250505050565b6000601f19601f8301169050919050565b60006115018261149f565b61150b81856114aa565b935061151b8185602086016114bb565b611524816114e5565b840191505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061155a8261152f565b9050919050565b61156a8161154f565b82525050565b60008115159050919050565b61158581611570565b82525050565b600060c0820190506115a06000830189611490565b81810360208301526115b281886114f6565b90506115c16040830187611561565b6115ce6060830186611561565b6115db608083018561157c565b6115e860a0830184611490565b979650505050505050565b6115fc8161154f565b811461160757600080fd5b50565b600081359050611619816115f3565b92915050565b6000806040838503121561163657611635611423565b5b60006116448582860161160a565b92505060206116558582860161144e565b9150509250929050565b6000602082019050611674600083018461157c565b92915050565b600060208201905061168f6000830184611561565b92915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6116d7826114e5565b810181811067ffffffffffffffff821117156116f6576116f561169f565b5b80604052505050565b6000611709611419565b905061171582826116ce565b919050565b600067ffffffffffffffff8211156117355761173461169f565b5b61173e826114e5565b9050602081019050919050565b82818337600083830152505050565b600061176d6117688461171a565b6116ff565b9050828152602081018484840111156117895761178861169a565b5b61179484828561174b565b509392505050565b600082601f8301126117b1576117b0611695565b5b81356117c184826020860161175a565b91505092915050565b600080600080600060a086880312156117e6576117e5611423565b5b60006117f48882890161144e565b955050602086013567ffffffffffffffff81111561181557611814611428565b5b6118218882890161179c565b94505060406118328882890161160a565b93505060606118438882890161160a565b92505060806118548882890161144e565b9150509295509295909350565b60006020828403121561187757611876611423565b5b60006118858482850161160a565b91505092915050565b60006020820190506118a36000830184611490565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6118de8161142d565b82525050565b600082825260208201905092915050565b60006119008261149f565b61190a81856118e4565b935061191a8185602086016114bb565b611923816114e5565b840191505092915050565b6119378161154f565b82525050565b61194681611570565b82525050565b600060a08301600083015161196460008601826118d5565b506020830151848203602086015261197c82826118f5565b9150506040830151611991604086018261192e565b5060608301516119a4606086018261193d565b5060808301516119b760808601826118d5565b508091505092915050565b60006119ce838361194c565b905092915050565b6000602082019050919050565b60006119ee826118a9565b6119f881856118b4565b935083602082028501611a0a856118c5565b8060005b85811015611a465784840389528151611a2785826119c2565b9450611a32836119d6565b925060208a01995050600181019050611a0e565b50829750879550505050505092915050565b60006020820190508181036000830152611a7281846119e3565b905092915050565b6000608082019050611a8f6000830187611490565b611a9c6020830186611490565b611aa96040830185611490565b611ab6606083018461157c565b95945050505050565b600080600060608486031215611ad857611ad7611423565b5b6000611ae68682870161144e565b9350506020611af78682870161160a565b9250506040611b088682870161144e565b9150509250925092565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611b5957607f821691505b602082108103611b6c57611b6b611b12565b5b50919050565b7f4170656e6173206f2041646d696e000000000000000000000000000000000000600082015250565b6000611ba8600e836114aa565b9150611bb382611b72565b602082019050919050565b60006020820190508181036000830152611bd781611b9b565b9050919050565b7f494420696e76616c69646f000000000000000000000000000000000000000000600082015250565b6000611c14600b836114aa565b9150611c1f82611bde565b602082019050919050565b60006020820190508181036000830152611c4381611c07565b9050919050565b7f456e64657265636f20696e76616c69646f000000000000000000000000000000600082015250565b6000611c806011836114aa565b9150611c8b82611c4a565b602082019050919050565b60006020820190508181036000830152611caf81611c73565b9050919050565b7f4368617665206a6120656d2075736f0000000000000000000000000000000000600082015250565b6000611cec600f836114aa565b9150611cf782611cb6565b602082019050919050565b60006020820190508181036000830152611d1b81611cdf565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611d5c8261142d565b9150611d678361142d565b9250828201905080821115611d7f57611d7e611d22565b5b92915050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302611de77fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611daa565b611df18683611daa565b95508019841693508086168417925050509392505050565b6000819050919050565b6000611e2e611e29611e248461142d565b611e09565b61142d565b9050919050565b6000819050919050565b611e4883611e13565b611e5c611e5482611e35565b848454611db7565b825550505050565b600090565b611e71611e64565b611e7c818484611e3f565b505050565b60005b82811015611ea457611e996000828401611e69565b600181019050611e84565b505050565b601f821115611ef85782821115611ef757611ec381611d85565b611ecc83611d9a565b611ed585611d9a565b6020861015611ee357600090505b808301611ef282840382611e81565b505050505b5b505050565b600082821c905092915050565b6000611f1b60001984600802611efd565b1980831691505092915050565b6000611f348383611f0a565b9150826002028217905092915050565b611f4d8261149f565b67ffffffffffffffff811115611f6657611f6561169f565b5b611f708254611b41565b611f7b828285611ea9565b600060209050601f831160018114611fae5760008415611f9c578287015190505b611fa68582611f28565b86555061200e565b601f198416611fbc86611d85565b60005b82811015611fe457848901518255600182019150602085019450602081019050611fbf565b868310156120015784890151611ffd601f891682611f0a565b8355505b6001600288020188555050505b505050505050565b6000606082019050818103600083015261203081866114f6565b905061203f6020830185611561565b61204c6040830184611561565b949350505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4170656e6173204368617665204d6573747265206f752041646d696e00000000600082015250565b60006120b9601c836114aa565b91506120c482612083565b602082019050919050565b600060208201905081810360008301526120e8816120ac565b9050919050565b7f45737461646f206f7520656e64657265636f20696e76616c69646f0000000000600082015250565b6000612125601b836114aa565b9150612130826120ef565b602082019050919050565b6000602082019050818103600083015261215481612118565b9050919050565b7f4e6f7661206368617665206a6120617472696275696461000000000000000000600082015250565b60006121916017836114aa565b915061219c8261215b565b602082019050919050565b600060208201905081810360008301526121c081612184565b9050919050565b7f42616e636f20696e617469766f00000000000000000000000000000000000000600082015250565b60006121fd600d836114aa565b9150612208826121c7565b602082019050919050565b6000602082019050818103600083015261222c816121f0565b905091905056fea26469706673582212203ebbf4e7befabec3e7135da842f9681af5af1071719ea62af2a104e96924e9b464736f6c63430008220033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_ALLBANKIDS = "allBankIds";

    public static final String FUNC_BANKS = "banks";

    public static final String FUNC_GETALLBANKS = "getAllBanks";

    public static final String FUNC_GETOPERATIONALKEY = "getOperationalKey";

    public static final String FUNC_KEYHISTORY = "keyHistory";

    public static final String FUNC_OPERATIONALTOBANKID = "operationalToBankId";

    public static final String FUNC_REGISTERBANK = "registerBank";

    public static final String FUNC_REVOKEBANK = "revokeBank";

    public static final String FUNC_ROTATEOPERATIONALKEY = "rotateOperationalKey";

    public static final String FUNC_VALIDATEHISTORICALKEY = "validateHistoricalKey";

    public static final Event BANKREGISTERED_EVENT = new Event("BankRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event BANKREVOKED_EVENT = new Event("BankRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event OPERATIONALKEYROTATED_EVENT = new Event("OperationalKeyRotated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected BankRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BankRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BankRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BankRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BankRegisteredEventResponse> getBankRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BANKREGISTERED_EVENT, transactionReceipt);
        ArrayList<BankRegisteredEventResponse> responses = new ArrayList<BankRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BankRegisteredEventResponse typedResponse = new BankRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.managementKey = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.operationalKey = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BankRegisteredEventResponse getBankRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BANKREGISTERED_EVENT, log);
        BankRegisteredEventResponse typedResponse = new BankRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.managementKey = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.operationalKey = (String) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<BankRegisteredEventResponse> bankRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBankRegisteredEventFromLog(log));
    }

    public Flowable<BankRegisteredEventResponse> bankRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BANKREGISTERED_EVENT));
        return bankRegisteredEventFlowable(filter);
    }

    public static List<BankRevokedEventResponse> getBankRevokedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BANKREVOKED_EVENT, transactionReceipt);
        ArrayList<BankRevokedEventResponse> responses = new ArrayList<BankRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BankRevokedEventResponse typedResponse = new BankRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BankRevokedEventResponse getBankRevokedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BANKREVOKED_EVENT, log);
        BankRevokedEventResponse typedResponse = new BankRevokedEventResponse();
        typedResponse.log = log;
        typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<BankRevokedEventResponse> bankRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBankRevokedEventFromLog(log));
    }

    public Flowable<BankRevokedEventResponse> bankRevokedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BANKREVOKED_EVENT));
        return bankRevokedEventFlowable(filter);
    }

    public static List<OperationalKeyRotatedEventResponse> getOperationalKeyRotatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OPERATIONALKEYROTATED_EVENT, transactionReceipt);
        ArrayList<OperationalKeyRotatedEventResponse> responses = new ArrayList<OperationalKeyRotatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OperationalKeyRotatedEventResponse typedResponse = new OperationalKeyRotatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.oldKey = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newKey = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OperationalKeyRotatedEventResponse getOperationalKeyRotatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OPERATIONALKEYROTATED_EVENT, log);
        OperationalKeyRotatedEventResponse typedResponse = new OperationalKeyRotatedEventResponse();
        typedResponse.log = log;
        typedResponse.bankId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.oldKey = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.newKey = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OperationalKeyRotatedEventResponse> operationalKeyRotatedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOperationalKeyRotatedEventFromLog(log));
    }

    public Flowable<OperationalKeyRotatedEventResponse> operationalKeyRotatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OPERATIONALKEYROTATED_EVENT));
        return operationalKeyRotatedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> allBankIds(BigInteger param0) {
        final Function function = new Function(FUNC_ALLBANKIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, String, String, String, Boolean, BigInteger>> banks(
            BigInteger param0) {
        final Function function = new Function(FUNC_BANKS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, String, String, String, Boolean, BigInteger>>(function,
                new Callable<Tuple6<BigInteger, String, String, String, Boolean, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, String, String, String, Boolean, BigInteger> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, String, String, String, Boolean, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getAllBanks() {
        final Function function = new Function(FUNC_GETALLBANKS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<BankSummary>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> getOperationalKey(BigInteger _bankId) {
        final Function function = new Function(FUNC_GETOPERATIONALKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bankId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, Boolean>> keyHistory(
            String param0) {
        final Function function = new Function(FUNC_KEYHISTORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, Boolean> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> operationalToBankId(String param0) {
        final Function function = new Function(FUNC_OPERATIONALTOBANKID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerBank(BigInteger _bankId, String _name,
            String _managementKey, String _operationalKey, BigInteger _durationInSeconds) {
        final Function function = new Function(
                FUNC_REGISTERBANK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bankId), 
                new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Address(160, _managementKey), 
                new org.web3j.abi.datatypes.Address(160, _operationalKey), 
                new org.web3j.abi.datatypes.generated.Uint256(_durationInSeconds)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeBank(BigInteger _bankId) {
        final Function function = new Function(
                FUNC_REVOKEBANK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bankId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> rotateOperationalKey(BigInteger _bankId,
            String _newOperationalKey, BigInteger _newDurationInSeconds) {
        final Function function = new Function(
                FUNC_ROTATEOPERATIONALKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bankId), 
                new org.web3j.abi.datatypes.Address(160, _newOperationalKey), 
                new org.web3j.abi.datatypes.generated.Uint256(_newDurationInSeconds)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> validateHistoricalKey(String _key, BigInteger _timestamp) {
        final Function function = new Function(FUNC_VALIDATEHISTORICALKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _key), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestamp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static BankRegistry load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new BankRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BankRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BankRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BankRegistry load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new BankRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BankRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BankRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BankRegistry> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BankRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<BankRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BankRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BankRegistry> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BankRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BankRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BankRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class BankSummary extends DynamicStruct {
        public BigInteger bankId;

        public String name;

        public String operationalKey;

        public Boolean isActive;

        public BigInteger expiresAt;

        public BankSummary(BigInteger bankId, String name, String operationalKey, Boolean isActive,
                BigInteger expiresAt) {
            super(new org.web3j.abi.datatypes.generated.Uint256(bankId), 
                    new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.Address(160, operationalKey), 
                    new org.web3j.abi.datatypes.Bool(isActive), 
                    new org.web3j.abi.datatypes.generated.Uint256(expiresAt));
            this.bankId = bankId;
            this.name = name;
            this.operationalKey = operationalKey;
            this.isActive = isActive;
            this.expiresAt = expiresAt;
        }

        public BankSummary(Uint256 bankId, Utf8String name, Address operationalKey, Bool isActive,
                Uint256 expiresAt) {
            super(bankId, name, operationalKey, isActive, expiresAt);
            this.bankId = bankId.getValue();
            this.name = name.getValue();
            this.operationalKey = operationalKey.getValue();
            this.isActive = isActive.getValue();
            this.expiresAt = expiresAt.getValue();
        }
    }

    public static class BankRegisteredEventResponse extends BaseEventResponse {
        public BigInteger bankId;

        public String name;

        public String managementKey;

        public String operationalKey;
    }

    public static class BankRevokedEventResponse extends BaseEventResponse {
        public BigInteger bankId;
    }

    public static class OperationalKeyRotatedEventResponse extends BaseEventResponse {
        public BigInteger bankId;

        public String oldKey;

        public String newKey;

        public BigInteger expiresAt;
    }
}
