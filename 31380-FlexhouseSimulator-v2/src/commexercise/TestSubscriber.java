package commexercise;

import commexercise.pubsub.*;
import commexercise.pubsub.demo.ClockService;

public class TestSubscriber {

    public static void main(String[] args) throws Exception {

      if (args.length<1) {
        System.out.println("Usage: TestSubscriber <subscribername>");
        System.exit(1);
      }
        // create a pubsub client
        PubSubClient pubSubClient = new PubSubClientImpl("localhost",9090,args[0]);

        // subscribe to clock topic
        PubSubCallbackListener clockListener = new PubSubCallbackListener() {
            public void messageReceived(String[] msg) {
                System.out.println("Received: " + msg[0]);
            }
        };
        System.out.println("Subscribing to clock service.");
        pubSubClient.subscribe("clock", clockListener);

        Thread.sleep(10000);

        System.out.println("Unsubscribing from clock service.");
        pubSubClient.unsubscribe(clockListener);

        Thread.sleep(10000);
        pubSubClient.stop();
        System.exit(0);
    }
}
