import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Administrator {
    private final static String INFO_EXCHANGE = "info_exchange";
    private final static String LOG_QUEUE = "admin_log_queue";
    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public Administrator() throws Exception {
        this.factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        channel.exchangeDeclare(INFO_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(LOG_QUEUE, false, false, false, null);
    }

    public void startLogging() throws Exception {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.print("\n[Admin]: Received log: " + message);
            }
        };

        channel.basicConsume(LOG_QUEUE, true, consumer);
        System.out.print("\n[Admin]: Started listening...");
    }

    public void sendInfo(String info) throws Exception {
        channel.basicPublish(INFO_EXCHANGE, "", null, info.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public static void main(String[] argv) throws Exception {
        Administrator admin = new Administrator();
        admin.startLogging();

        admin.sendInfo("Welcome everyone!");

        Thread consoleThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter info: ");
            while (true) {
                String command = scanner.nextLine();
                if (Objects.equals(command, "exit")) {
                    try {
                        admin.close();
                        break;
                    } catch (IOException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                } else if (!Objects.equals(command, "")) {
                    try {
                        admin.sendInfo(command);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print("Enter info: ");
            }
        });
        consoleThread.start();
    }
}
