package commexercise;

import java.util.Arrays;
import commexercise.rpc.CallbackListener;
import commexercise.rpc.RpcClient;
import commexercise.rpc.RpcClientImpl;

public class TestPiAsyncMaster {

  private static long slavescore;
  private static boolean slave_finished;
  private static final Object slave_monitor = new Object();
  private static long starttime;
  
  public static void main(String[] args) throws Exception {
    if (args.length<1) {
      System.out.println("Usage: TestPiAsyncMaster <rounds>");
      System.exit(1);
    }
    slave_finished=false;
    long rounds=Long.valueOf(args[0]).longValue()/2;
    
    RpcClient client = new RpcClientImpl("http://localhost:8080");
    
    // create a callback listener for use with the call
    CallbackListener asyncListener = new CallbackListener() {
        @Override
        public void functionExecuted(long callID, String[] response) {
          if (callID==1L) {
            slavescore=Long.valueOf(response[0]).longValue();
            slave_finished=true;
            synchronized(slave_monitor) {
              slave_monitor.notify(); 
            }
            System.out.println("Slave finished in "+
                ((double)(System.currentTimeMillis()-starttime)/1000)+"s; score="+
                slavescore+"/"+rounds+".");
          }
        }

        @Override
        public void functionFailed(long callID, Exception e) {
          e.printStackTrace();
        }
    };

    System.out.println("Asking slave to calculate "+rounds+" rounds.");
    starttime=System.currentTimeMillis();
    client.callAsync(TestPiSlave.PIFUNCTION,
                     new String[]{Long.toString(rounds)}, 1L, asyncListener);
    
    System.out.println("Asking master (myself) to calculate "+rounds+" rounds.");
    long starttime2=System.currentTimeMillis();
    long masterscore=PiCalculator.picalc(rounds);
    System.out.println("Master finished in "+
        ((double)(System.currentTimeMillis()-starttime2)/1000)+"s; score="+
        masterscore+"/"+rounds+".");
    
    synchronized(slave_monitor) {
      while (!slave_finished) { 
        slave_monitor.wait();
      }
    }
    
    double pi = 4.0 * (double)(masterscore+slavescore)/(double)(rounds*2);
    
    System.out.println("Estimating Pi="+pi+", total time="+
        ((double)(System.currentTimeMillis()-starttime)/1000)+"s.");
    
  }
  
}
