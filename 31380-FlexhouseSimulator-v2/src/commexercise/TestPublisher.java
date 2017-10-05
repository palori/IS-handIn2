package commexercise;

import commexercise.pubsub.*;
import commexercise.pubsub.demo.ClockService;
import commexercise.rpc.*;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPublisher {
    private static final Logger log = LoggerFactory.getLogger(TestPublisher.class);

    public static void main(String[] args) throws Exception {

        // create a pubsub server listening on port 9090
        log.info("Starting PubSub Server");
        PubSubServer pubSubServer = new PubSubServerImpl(9090).start();

        // add subscriber listener (gets called when a client subscribes or unsubscribes)
        pubSubServer.addSubscriberListener(new PubSubSubscriberListener() {
            public void subscriberJoined(String topic, String id) {
              System.out.println("Subscriber '"+id+"' joined for topic '"+topic);
            }

            public void subscriberLeft(String topic, String id) {
              System.out.println("Subscriber '"+id+"' left for topic '"+topic);
            }
        });

        // start the demo service that publishes current time to subscribers
        ClockService clock = new ClockService(pubSubServer).start();


        while (1==1) {
          Thread.sleep(1000);

          // get clients subscribed to clock topic
          String[] list = pubSubServer.getSubscriberListForTopic("clock");
          System.out.println("*** clients subscribed:"+Arrays.toString(list));
        }
    }
}
  