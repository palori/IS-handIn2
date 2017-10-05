package commexercise;

import commexercise.broadcast.BroadcastNode;
import commexercise.broadcast.BroadcastNodeImpl;

public class TestBroadcastSender {

  public static void main(String[] args) {
    if (args.length<2) {
      System.out.println("Usage: TestBroadcastSender <nodename> <message>");
      System.exit(1);
    }
    BroadcastNode bcn=new BroadcastNodeImpl(args[0]);
    bcn.sendMessage(args[1]);
    System.exit(0);
  }

}
