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
import org.web3j.tuples.generated.Tuple2;
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
    public static final String BINARY = "6080604052348015600e575f5ffd5b503360035f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506124078061005c5f395ff3fe608060405234801561000f575f5ffd5b50600436106100a6575f3560e01c80638ac9c93d1161006f5780638ac9c93d1461018b578063951c8a07146101bc578063a0bfad70146101da578063e05887181461020a578063e442432414610226578063f851a44014610242576100a6565b806285ae2b146100aa57806303341960146100df57806309ebea0e1461010f57806327cc34771461013f5780636f7e8adc1461015b575b5f5ffd5b6100c460048036038101906100bf91906114ec565b610260565b6040516100d6969594939291906115ef565b60405180910390f35b6100f960048036038101906100f491906114ec565b610368565b6040516101069190611655565b60405180910390f35b610129600480360381019061012491906114ec565b6103f7565b604051610136919061166e565b60405180910390f35b610159600480360381019061015491906117e4565b6104ee565b005b61017560048036038101906101709190611877565b610937565b60405161018291906118a2565b60405180910390f35b6101a560048036038101906101a09190611877565b61094c565b6040516101b39291906118bb565b60405180910390f35b6101c4610b99565b6040516101d19190611a85565b60405180910390f35b6101f460048036038101906101ef91906114ec565b610e36565b60405161020191906118a2565b60405180910390f35b610224600480360381019061021f9190611aa5565b610e56565b005b610240600480360381019061023b91906114ec565b611233565b005b61024a611440565b6040516102579190611655565b60405180910390f35b5f602052805f5260405f205f91509050805f01549080600101805461028490611b22565b80601f01602080910402602001604051908101604052809291908181526020018280546102b090611b22565b80156102fb5780601f106102d2576101008083540402835291602001916102fb565b820191905f5260205f20905b8154815290600101906020018083116102de57829003601f168201915b505050505090806002015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030160149054906101000a900460ff16908060040154905086565b5f5f5f5f8481526020019081526020015f205f0154036103bd576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103b490611b9c565b60405180910390fd5b5f5f8381526020019081526020015f206003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b60605f5f5f8481526020019081526020015f205f01540361044d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161044490611b9c565b60405180910390fd5b5f5f8381526020019081526020015f20600101805461046b90611b22565b80601f016020809104026020016040519081016040528092919081815260200182805461049790611b22565b80156104e25780601f106104b9576101008083540402835291602001916104e2565b820191905f5260205f20905b8154815290600101906020018083116104c557829003601f168201915b50505050509050919050565b60035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461057d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161057490611c2a565b60405180910390fd5b5f85116105bf576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105b690611cb8565b60405180910390fd5b5f5f5f8781526020019081526020015f205f015414610613576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161060a90611d20565b60405180910390fd5b5f73ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415801561067b57505f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b6106ba576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106b190611d88565b60405180910390fd5b5f60015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205414610739576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161073090611df0565b60405180910390fd5b5f81426107469190611e3b565b90506040518060c001604052808781526020018681526020018573ffffffffffffffffffffffffffffffffffffffff1681526020018473ffffffffffffffffffffffffffffffffffffffff168152602001600115158152602001828152505f5f8881526020019081526020015f205f820151815f015560208201518160010190816107d1919061201f565b506040820151816002015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506060820151816003015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160030160146101000a81548160ff02191690831515021790555060a082015181600401559050508560015f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2081905550600286908060018154018082558091505060019003905f5260205f20015f9091909190915055857f25d45502b4a3c4393a0ae2d0405cc2043dcffbbe8f8e0edf2cbae23a1556e024868686604051610927939291906120ee565b60405180910390a2505050505050565b6001602052805f5260405f205f915090505481565b5f5f5f60015f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205490505f81036109a2575f5f9250925050610b94565b5f5f5f8381526020019081526020015f206040518060c00160405290815f82015481526020016001820180546109d790611b22565b80601f0160208091040260200160405190810160405280929190818152602001828054610a0390611b22565b8015610a4e5780601f10610a2557610100808354040283529160200191610a4e565b820191905f5260205f20905b815481529060010190602001808311610a3157829003601f168201915b50505050508152602001600282015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600382015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016003820160149054906101000a900460ff16151515158152602001600482015481525050905080608001518015610b3d57508060a001514211155b8015610b7857508473ffffffffffffffffffffffffffffffffffffffff16816060015173ffffffffffffffffffffffffffffffffffffffff16145b15610b8b57600182935093505050610b94565b5f5f9350935050505b915091565b60605f60028054905090505f8167ffffffffffffffff811115610bbf57610bbe611696565b5b604051908082528060200260200182016040528015610bf857816020015b610be5611465565b815260200190600190039081610bdd5790505b5090505f5f90505b82811015610e2d575f60028281548110610c1d57610c1c61212a565b5b905f5260205f20015490505f5f5f8381526020019081526020015f206040518060c00160405290815f8201548152602001600182018054610c5d90611b22565b80601f0160208091040260200160405190810160405280929190818152602001828054610c8990611b22565b8015610cd45780601f10610cab57610100808354040283529160200191610cd4565b820191905f5260205f20905b815481529060010190602001808311610cb757829003601f168201915b50505050508152602001600282015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600382015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016003820160149054906101000a900460ff1615151515815260200160048201548152505090506040518060a00160405280825f0151815260200182602001518152602001826060015173ffffffffffffffffffffffffffffffffffffffff1681526020018260800151151581526020018260a00151815250848481518110610e1357610e1261212a565b5b602002602001018190525050508080600101915050610c00565b50809250505090565b60028181548110610e45575f80fd5b905f5260205f20015f915090505481565b825f5f8281526020019081526020015f206002015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610f0f575060035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b610f4e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f45906121ed565b60405180910390fd5b5f5f5f8681526020019081526020015f2090508060030160149054906101000a900460ff16610fb2576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610fa990612255565b60405180910390fd5b5f73ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1603611020576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611017906122bd565b60405180910390fd5b5f60015f8673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20541461109f576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016110969061234b565b60405180910390fd5b60015f826003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f90555f816003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905084826003015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555083426111779190611e3b565b82600401819055508560015f8773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20819055508473ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16877fb8abba1af5b8a780acbd88555fb97e43952780421e108ded1e3bccd576b16c4c856004015460405161122391906118a2565b60405180910390a4505050505050565b805f5f8281526020019081526020015f206002015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806112ec575060035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b61132b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611322906121ed565b60405180910390fd5b5f5f5f8481526020019081526020015f2090508060030160149054906101000a900460ff1661138f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611386906123b3565b60405180910390fd5b5f8160030160146101000a81548160ff02191690831515021790555060015f826003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9055827f0edd4f48c5940002a834fe3979b412b3845397005cbcb559ec1bb8547dc46a9c60405160405180910390a2505050565b60035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6040518060a001604052805f8152602001606081526020015f73ffffffffffffffffffffffffffffffffffffffff1681526020015f151581526020015f81525090565b5f604051905090565b5f5ffd5b5f5ffd5b5f819050919050565b6114cb816114b9565b81146114d5575f5ffd5b50565b5f813590506114e6816114c2565b92915050565b5f60208284031215611501576115006114b1565b5b5f61150e848285016114d8565b91505092915050565b611520816114b9565b82525050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f61156882611526565b6115728185611530565b9350611582818560208601611540565b61158b8161154e565b840191505092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6115bf82611596565b9050919050565b6115cf816115b5565b82525050565b5f8115159050919050565b6115e9816115d5565b82525050565b5f60c0820190506116025f830189611517565b8181036020830152611614818861155e565b905061162360408301876115c6565b61163060608301866115c6565b61163d60808301856115e0565b61164a60a0830184611517565b979650505050505050565b5f6020820190506116685f8301846115c6565b92915050565b5f6020820190508181035f830152611686818461155e565b905092915050565b5f5ffd5b5f5ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6116cc8261154e565b810181811067ffffffffffffffff821117156116eb576116ea611696565b5b80604052505050565b5f6116fd6114a8565b905061170982826116c3565b919050565b5f67ffffffffffffffff82111561172857611727611696565b5b6117318261154e565b9050602081019050919050565b828183375f83830152505050565b5f61175e6117598461170e565b6116f4565b90508281526020810184848401111561177a57611779611692565b5b61178584828561173e565b509392505050565b5f82601f8301126117a1576117a061168e565b5b81356117b184826020860161174c565b91505092915050565b6117c3816115b5565b81146117cd575f5ffd5b50565b5f813590506117de816117ba565b92915050565b5f5f5f5f5f60a086880312156117fd576117fc6114b1565b5b5f61180a888289016114d8565b955050602086013567ffffffffffffffff81111561182b5761182a6114b5565b5b6118378882890161178d565b9450506040611848888289016117d0565b9350506060611859888289016117d0565b925050608061186a888289016114d8565b9150509295509295909350565b5f6020828403121561188c5761188b6114b1565b5b5f611899848285016117d0565b91505092915050565b5f6020820190506118b55f830184611517565b92915050565b5f6040820190506118ce5f8301856115e0565b6118db6020830184611517565b9392505050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b611914816114b9565b82525050565b5f82825260208201905092915050565b5f61193482611526565b61193e818561191a565b935061194e818560208601611540565b6119578161154e565b840191505092915050565b61196b816115b5565b82525050565b61197a816115d5565b82525050565b5f60a083015f8301516119955f86018261190b565b50602083015184820360208601526119ad828261192a565b91505060408301516119c26040860182611962565b5060608301516119d56060860182611971565b5060808301516119e8608086018261190b565b508091505092915050565b5f6119fe8383611980565b905092915050565b5f602082019050919050565b5f611a1c826118e2565b611a2681856118ec565b935083602082028501611a38856118fc565b805f5b85811015611a735784840389528151611a5485826119f3565b9450611a5f83611a06565b925060208a01995050600181019050611a3b565b50829750879550505050505092915050565b5f6020820190508181035f830152611a9d8184611a12565b905092915050565b5f5f5f60608486031215611abc57611abb6114b1565b5b5f611ac9868287016114d8565b9350506020611ada868287016117d0565b9250506040611aeb868287016114d8565b9150509250925092565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f6002820490506001821680611b3957607f821691505b602082108103611b4c57611b4b611af5565b5b50919050565b7f42616e636f206e616f20656e636f6e747261646f0000000000000000000000005f82015250565b5f611b86601483611530565b9150611b9182611b52565b602082019050919050565b5f6020820190508181035f830152611bb381611b7a565b9050919050565b7f4170656e6173206f2041646d696e6973747261646f7220646f20436f6e736f725f8201527f63696f20706f64652065786563757461722065737461206163616f0000000000602082015250565b5f611c14603b83611530565b9150611c1f82611bba565b604082019050919050565b5f6020820190508181035f830152611c4181611c08565b9050919050565b7f494420646f2062616e636f206465766520736572206d61696f7220717565207a5f8201527f65726f0000000000000000000000000000000000000000000000000000000000602082015250565b5f611ca2602383611530565b9150611cad82611c48565b604082019050919050565b5f6020820190508181035f830152611ccf81611c96565b9050919050565b7f494420646f2062616e636f206a612065786973746500000000000000000000005f82015250565b5f611d0a601583611530565b9150611d1582611cd6565b602082019050919050565b5f6020820190508181035f830152611d3781611cfe565b9050919050565b7f456e64657265636f7320696e76616c69646f73000000000000000000000000005f82015250565b5f611d72601383611530565b9150611d7d82611d3e565b602082019050919050565b5f6020820190508181035f830152611d9f81611d66565b9050919050565b7f4368617665206f7065726163696f6e616c206a61206573746120656d2075736f5f82015250565b5f611dda602083611530565b9150611de582611da6565b602082019050919050565b5f6020820190508181035f830152611e0781611dce565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f611e45826114b9565b9150611e50836114b9565b9250828201905080821115611e6857611e67611e0e565b5b92915050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f60088302611eca7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611e8f565b611ed48683611e8f565b95508019841693508086168417925050509392505050565b5f819050919050565b5f611f0f611f0a611f05846114b9565b611eec565b6114b9565b9050919050565b5f819050919050565b611f2883611ef5565b611f3c611f3482611f16565b848454611e9b565b825550505050565b5f5f905090565b611f53611f44565b611f5e818484611f1f565b505050565b5f5b82811015611f8457611f795f828401611f4b565b600181019050611f65565b505050565b601f821115611fd75782821115611fd657611fa381611e6e565b611fac83611e80565b611fb585611e80565b6020861015611fc2575f90505b808301611fd182840382611f63565b505050505b5b505050565b5f82821c905092915050565b5f611ff75f1984600802611fdc565b1980831691505092915050565b5f61200f8383611fe8565b9150826002028217905092915050565b61202882611526565b67ffffffffffffffff81111561204157612040611696565b5b61204b8254611b22565b612056828285611f89565b5f60209050601f831160018114612087575f8415612075578287015190505b61207f8582612004565b8655506120e6565b601f19841661209586611e6e565b5f5b828110156120bc57848901518255600182019150602085019450602081019050612097565b868310156120d957848901516120d5601f891682611fe8565b8355505b6001600288020188555050505b505050505050565b5f6060820190508181035f830152612106818661155e565b905061211560208301856115c6565b61212260408301846115c6565b949350505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f4170656e61732061204368617665204d657374726520646f2062616e636f206f5f8201527f75206f2041646d696e20706f64656d206578656375746172206573746120616360208201527f616f000000000000000000000000000000000000000000000000000000000000604082015250565b5f6121d7604283611530565b91506121e282612157565b606082019050919050565b5f6020820190508181035f830152612204816121cb565b9050919050565b7f4f2062616e636f20656e636f6e7472612d736520696e617469766f00000000005f82015250565b5f61223f601b83611530565b915061224a8261220b565b602082019050919050565b5f6020820190508181035f83015261226c81612233565b9050919050565b7f456e64657265636f20696e76616c69646f0000000000000000000000000000005f82015250565b5f6122a7601183611530565b91506122b282612273565b602082019050919050565b5f6020820190508181035f8301526122d48161229b565b9050919050565b7f41206e6f7661206368617665206a6120657374612061747269627569646120615f8201527f206f7574726f2062616e636f0000000000000000000000000000000000000000602082015250565b5f612335602c83611530565b9150612340826122db565b604082019050919050565b5f6020820190508181035f83015261236281612329565b9050919050565b7f4f2062616e636f206a61206573746120696e617469766f0000000000000000005f82015250565b5f61239d601783611530565b91506123a882612369565b602082019050919050565b5f6020820190508181035f8301526123ca81612391565b905091905056fea26469706673582212202b12f4a11f65d4e9b9a9b8ea07a757ea52bbfd51e6ef954c771744ea5b52805f64736f6c63430008220033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_ALLBANKIDS = "allBankIds";

    public static final String FUNC_BANKS = "banks";

    public static final String FUNC_GETALLBANKS = "getAllBanks";

    public static final String FUNC_GETBANKNAME = "getBankName";

    public static final String FUNC_GETOPERATIONALKEY = "getOperationalKey";

    public static final String FUNC_OPERATIONALTOBANKID = "operationalToBankId";

    public static final String FUNC_REGISTERBANK = "registerBank";

    public static final String FUNC_REVOKEBANK = "revokeBank";

    public static final String FUNC_ROTATEOPERATIONALKEY = "rotateOperationalKey";

    public static final String FUNC_VALIDATEOPERATIONALKEY = "validateOperationalKey";

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

    public RemoteFunctionCall<String> getBankName(BigInteger _bankId) {
        final Function function = new Function(FUNC_GETBANKNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bankId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getOperationalKey(BigInteger _bankId) {
        final Function function = new Function(FUNC_GETOPERATIONALKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bankId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<Tuple2<Boolean, BigInteger>> validateOperationalKey(
            String _operationalKey) {
        final Function function = new Function(FUNC_VALIDATEOPERATIONALKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _operationalKey)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, BigInteger>>(function,
                new Callable<Tuple2<Boolean, BigInteger>>() {
                    @Override
                    public Tuple2<Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
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
