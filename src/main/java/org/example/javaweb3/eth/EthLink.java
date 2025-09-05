package org.example.javaweb3.eth;

import io.reactivex.disposables.Disposable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

public class EthLink {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


    }

    public static Web3ClientVersion Web3jClient() throws ExecutionException, InterruptedException {
        Web3j client = Web3j.build(new HttpService("https://mainnet.infura.io/v3/14216fbbc06c4844a8c88881eedbf056"));
        Web3ClientVersion clientVersion = client.web3ClientVersion().sendAsync().get();
        System.out.println(clientVersion.getWeb3ClientVersion());
        return clientVersion;
}

    /**
     *  * 使用 Web3J 获取以太坊账户余额
     *
     *  @throws ExecutionException
     *  @throws InterruptedException
     */
    public static void getETHBalance() throws ExecutionException, InterruptedException {
        Web3j client = Web3j.build(new HttpService("https://mainnet.infura.io/v3/14216fbbc06c4844a8c88881eedbf056"));
        EthGetBalance ethGetBalance = client.ethGetBalance("0xYour Address", DefaultBlockParameterName.LATEST).sendAsync().get();
        System.out.println(ethGetBalance.getBalance());
    }


    /**
     *  * 使用 Web3J 获取当前的 Gas 价格
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void getEthGasPrice() throws ExecutionException, InterruptedException {
        Web3j client = Web3j.build(new HttpService("https://ropsten.infura.io/v3/You Infura Project Id"));
        EthGasPrice ethGasPrice = client.ethGasPrice().sendAsync().get();
        System.out.println(ethGasPrice.getGasPrice());
    }

    /**
     *  * 使用 Web3J 通过交易哈希获取交易详情
     *
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void getEthTradeDetailByTradeHash() throws ExecutionException, InterruptedException {
        Web3j client = Web3j.build(new HttpService("https://ropsten.infura.io/v3/You Infura Project Id"));
        String transactionHash = "0x9030edd43f8ae6c4ed49bcbc11dd7d6f6ce2798e8bb1c5ea4f1e130780fec74a";
        EthGetTransactionReceipt ethGetTransactionReceipt = client.ethGetTransactionReceipt(transactionHash).sendAsync().get();
        TransactionReceipt transactionReceipt = ethGetTransactionReceipt.getTransactionReceipt().orElseThrow(RuntimeException::new);
        System.out.println(transactionReceipt);
    }


    /**
     *  * 订阅新区块
     *  * 当有新的区块产生时，会触发回调函数
     *  * 回调函数中可以获取到新区块的详细信息
     *  * 订阅会持续运行，直到手动取消订阅
     *  * 取消订阅可以通过调用 Disposable.dispose() 方法来实现
     * @throws ConnectException
     */
    public static void subNewBlock() throws ConnectException {
        WebSocketService webSocketService = new WebSocketService("wss://ropsten.infura.io/ws/v3/You Infura Project Id", true);
        webSocketService.connect();
        Web3j client = Web3j.build(webSocketService);
        Disposable subscribe = client.replayPastBlocksFlowable(DefaultBlockParameterName.fromString("earliest"), true).subscribe(ethBlock -> {
            System.out.println(ethBlock.getBlock());
            // => org.web3j.protocol.core.methods.response.EthBlock$Block@39fcf950
        });
    }


    /**
     *  * 订阅新交易
     *  *
     * @throws ConnectException
     */
    public static void subNewTrade() throws ConnectException {
        WebSocketService webSocketService = new WebSocketService("wss://ropsten.infura.io/ws/v3/You Infura Project Id", true);
        webSocketService.connect();
        Web3j client = Web3j.build(webSocketService);
        Disposable subscribe = client.replayPastAndFutureBlocksFlowable(DefaultBlockParameterName.fromString("earliest"), true).subscribe(ethBlock -> {
            System.out.println(ethBlock.getBlock());
        });
    }



}
