import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Z1_Producer {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z1 PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // queue
        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1); // QOS 6.1b

        // producer (publish msg)
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String message;
//        System.out.print("Enter message: ");
//        message = br.readLine();
//
//        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//        System.out.println("Sent: " + message);

        // 6.1b
        String message;
        for (int i=0; i<10; i++){
            if (i % 2 == 0){
                message = "1";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("Sent 1");
            } else{
                message = "5";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("Sent 5");
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // close
        channel.close();
        connection.close();
    }
}
