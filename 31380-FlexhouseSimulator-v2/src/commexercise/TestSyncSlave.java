package commexercise;

import java.util.Arrays;
import commexercise.rpc.CallListener;
import commexercise.rpc.RpcServer;
import commexercise.rpc.RpcServerImpl;

public class TestSyncSlave {
  
  public static final String REVERSEFUNCTION="reverse";

  public static void main(String[] args) throws Exception {
    
    // create an rpc server listening on port 8080
    RpcServer server = new RpcServerImpl(8080).start();

    // add a call listener that will get called when a client does an RPC call to the server
    server.setCallListener(new CallListener() {
      @Override
      public String[] receivedSyncCall(String function, String[] fargs) throws Exception {
        System.out.println("Received call for function '"+function+"' with arguments"+
                            Arrays.toString(fargs)+". Replying now.");
        if (function.equals(REVERSEFUNCTION)) {
          String rargs=new StringBuilder(Arrays.toString(fargs)).reverse().toString();
          return new String[]{"You called:",function,"I reversed your arguments and they are now",rargs};
        }
        else {
          return new String[]{"Function '",function,"' does not exist."};
        }
      }

      @Override
      public String[] receivedAsyncCall(String function, String[] args, long callID) throws Exception {
        return null; // not implemented for this test
      }
    });
  }
  
}
