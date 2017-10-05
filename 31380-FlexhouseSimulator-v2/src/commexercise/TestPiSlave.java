package commexercise;

import java.util.Arrays;
import commexercise.rpc.CallListener;
import commexercise.rpc.RpcServer;
import commexercise.rpc.RpcServerImpl;

public class TestPiSlave {
  
  public static final String PIFUNCTION="picalc";
  
  public static void main(String[] args) throws Exception {
    
    // create an rpc server listening on port 8080
    RpcServer server = new RpcServerImpl(8080).start();

    // add a call listener that will get called when a client does an RPC call to the server
    server.setCallListener(new CallListener() {
      @Override
      public String[] receivedSyncCall(String function, String[] fargs) throws Exception {
        if (function.equals(PIFUNCTION)) {
          if ((fargs!=null) && (fargs.length>0)) {
            long rounds=Long.valueOf(fargs[0]).longValue();
            long score=PiCalculator.picalc(rounds);
            return new String[]{Long.toString(score)};
          }
        }
        else {
          return new String[]{"Function '",function,"' does not exist."};
        }
        return null;
      }

      @Override
      public String[] receivedAsyncCall(String function, String[] args, long callID) throws Exception {
        return receivedSyncCall(function,args);
      }
    });
  }
  
}
