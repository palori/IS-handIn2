package commexercise;

import java.util.Arrays;
import commexercise.rpc.RpcClient;
import commexercise.rpc.RpcClientImpl;

public class TestPiSyncMaster {

  public static void main(String[] args) throws Exception {
    if (args.length<1) {
      System.out.println("Usage: TestPiSyncMaster <rounds>");
      System.exit(1);
    }
    long rounds=Long.valueOf(args[0]).longValue()/2;
    
    RpcClient client = new RpcClientImpl("http://localhost:8080");
    // do synchronous call to server

    System.out.println("Asking slave to calculate "+rounds+" rounds.");
    long starttime=System.currentTimeMillis();
    String[] reply = client.callSync(TestPiSlave.PIFUNCTION,
                                     new String[]{Long.toString(rounds)});
    long slavescore=Long.valueOf(reply[0]).longValue();
    System.out.println("Slave finished in "+
                       ((double)(System.currentTimeMillis()-starttime)/1000)+"s; score="+
                       slavescore+"/"+rounds+".");
    
    System.out.println("Asking master (myself) to calculate "+rounds+" rounds.");
    long starttime2=System.currentTimeMillis();
    long masterscore=PiCalculator.picalc(rounds);
    System.out.println("Master finished in "+
        ((double)(System.currentTimeMillis()-starttime2)/1000)+"s; score="+
        masterscore+"/"+rounds+".");
    
    double pi = 4.0 * (double)(masterscore+slavescore)/(double)(rounds*2);
    
    System.out.println("Estimating Pi="+pi+", total time="+
        ((double)(System.currentTimeMillis()-starttime)/1000)+"s.");
    
  }
  
}
