import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Doctor {
    private final static String ORDER_EXCHANGE = "order_exchange";
    private final static String RESULT_EXCHANGE = "result_exchange";
    private final static String INFO_EXCHANGE = "info_exchange";
    private final static String LOG_QUEUE = "admin_log_queue";
    private final String result_queue;
    private final String info_queue;
    private final String doctorName;
    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public Doctor(String doctorName) throws IOException, TimeoutException {
        this.doctorName = doctorName;
        this.result_queue = doctorName + "_result_queue";
        this.info_queue = doctorName + "_info_queue";

        this.factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        channel.exchangeDeclare(ORDER_EXCHANGE, BuiltinExchangeType.DIRECT);

        channel.exchangeDeclare(RESULT_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(result_queue, false, false, false, null);
        channel.queueBind(result_queue, RESULT_EXCHANGE, doctorName);

        channel.exchangeDeclare(INFO_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(info_queue, false, false, false, null);
        channel.queueBind(info_queue, INFO_EXCHANGE, "");
    }

    public void sendOrder(String type, String patientName) throws IOException {
        String order = doctorName + ": " + patientName + " - " + type;
        channel.basicPublish(ORDER_EXCHANGE, type, null, order.getBytes());
        System.out.print("\n["+doctorName+"]: Sent order: " + order);
        logActivity("ORDER: " + order);
    }

    public void startListening() throws IOException {
        startListeningForResults();
        startListeningForInfo();
        System.out.println("["+doctorName+"]: Started listening...");
    }

    public void startListeningForResults() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.print("\n["+doctorName+"]: Received result: " + message);
            }
        };

        channel.basicConsume(result_queue, true, consumer);
    }

    public void startListeningForInfo() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.print("\n["+doctorName+"]: Received info: " + message);
            }
        };

        channel.basicConsume(info_queue, true, consumer);
    }

    private void logActivity(String message) throws IOException {
        channel.basicPublish("", LOG_QUEUE, null, message.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public static void main(String[] argv) throws IOException, TimeoutException {
        Doctor doctor1 = new Doctor("Doctor1");
        Doctor doctor2 = new Doctor("Doctor2");

        doctor1.startListening();
        doctor2.startListening();

        doctor1.sendOrder("knee", "Patient1");
        doctor2.sendOrder("hip", "Patient2");
        doctor2.sendOrder("knee", "Patient3");
//        doctor1.sendOrder("elbow", "Patient4");

        Thread consoleThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter order <doctorNumber orderType patientName>: ");
            while (true) {
                String command = scanner.nextLine();
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    int doctorNumber = Integer.parseInt(parts[0]);
                    String orderType = parts[1];
                    String patientName = parts[2];
                    try {
                        if (doctorNumber == 1) {
                            doctor1.sendOrder(orderType, patientName);
                        } else if (doctorNumber == 2) {
                            doctor2.sendOrder(orderType, patientName);
                        } else {
                            System.out.println("[Error]: Invalid doctor number - use 1 or 2");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Objects.equals(parts[0], "exit")) {
                    try {
                        doctor1.close();
                        doctor2.close();
                        break;
                    } catch (IOException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                } else if (!Objects.equals(parts[0], "")) {
                    System.out.println("[Error]: Invalid order format - use <doctorNumber orderType patientName>");
                }
                System.out.print("Enter order: ");
            }
        });
        consoleThread.start();
    }
}
