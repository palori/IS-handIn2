package commexercise;

import commexercise.broadcast.BroadcastNode;
import commexercise.broadcast.BroadcastNodeImpl;
import commexercise.broadcast.MessageListener;

public class TestBroadcastReceiver implements MessageListener {

  private TestBroadcastReceiver(String nodename) {
    BroadcastNode bcn=new BroadcastNodeImpl(nodename);
    bcn.addMessageListener(this);
  }

  @Override
  public void messageReceived(String message, String origin) {
    System.out.println("*** Received message from '"+origin+"':");
    System.out.println("    \""+message+"\"");
  }
  
  public static void main(String args[]) {
    if (args.length<1) {
      System.out.println("Usage: TestBroadcastReceiver <nodename>");
      System.exit(1);
    }
    new TestBroadcastReceiver(args[0]);
  }
  
}
