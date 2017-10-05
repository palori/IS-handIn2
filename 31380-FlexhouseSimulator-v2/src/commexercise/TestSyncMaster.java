package commexercise;

import commexercise.rpc.*;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSyncMaster {
    //private static final Logger log = LoggerFactory.getLogger(TestSyncSender.class);

    public static void main(String[] args) throws Exception {
      if (args.length<1) {
        System.out.println("Usage: TestSyncSender <function_name> <optional arguments>");
        System.exit(1);
      }
      
      RpcClient client = new RpcClientImpl("localhost",8080);
      // do synchronous call to server
      String[] sargs=null;
      if (args.length>1) {
        sargs=new String[args.length-1];
        System.arraycopy(args, 1, sargs, 0, args.length-1);
      }
      String[] reply = client.callSync(args[0], sargs);
      System.out.println("Synchronous reply: "+Arrays.toString(reply));

    }
}
