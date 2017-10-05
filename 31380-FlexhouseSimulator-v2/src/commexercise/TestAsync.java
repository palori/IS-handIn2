package commexercise;

import commexercise.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class TestAsync {
    private static final Logger log = LoggerFactory.getLogger(TestAsync.class);

    public static void main(String[] args) throws Exception {

        // create an RPC server
        log.info("Starting RPC Server");
        RpcServer server = new RpcServerImpl(8080).start();

        // create the call listener that receives calls from clients
        server.setCallListener(new CallListener() {
            @Override
            public String[] receivedSyncCall(String function, String[] args) throws Exception {
                return new String[]{"Function called:", function.toUpperCase()};
            }

            @Override
            public String[] receivedAsyncCall(String function, String[] args, long callID) throws Exception {
                Random rand = new Random();
                int sleep = rand.nextInt(10);
                log.debug("Sleeping for {} seconds, callid: {}", sleep, callID);
                try {
                    Thread.sleep(sleep * 1000);
                } catch (InterruptedException e) {
                    log.error("Sleep failed", e);
                }
                String resp = args[0] + " " + args[1] + " (" + sleep + ")";
                return new String[]{resp, String.valueOf(callID)};
            }
        });



        // create an RPC client
        log.info("Starting RPC Client");
        RpcClient client = new RpcClientImpl("http://localhost:8080");



        // create a callback listener for use with the call
        CallbackListener asyncListener = new CallbackListener() {
            @Override
            public void functionExecuted(long callID, String[] response) {
                log.debug("async reply: {}, callid: {}", response[0], response[1], callID);
            }

            @Override
            public void functionFailed(long callID, Exception e) {
                log.error("async error: {}, callid: {}", e.getMessage(), callID);
            }
        };

        // do asynchronous call to RPC server
        client.callAsync("helloAsyncworld", new String[]{"some", "text"}, 1L, asyncListener);
        client.callAsync("helloAsyncworld", new String[]{"other", "text"}, 2L , asyncListener);




        // stop server
        Thread.sleep(10000);
        log.info("CLOSING APPLICATION!!!");
        server.stop();
    }
}
