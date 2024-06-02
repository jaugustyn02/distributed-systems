import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Technician {
    private final static String ORDER_EXCHANGE = "order_exchange";
    private final static String RESULT_EXCHANGE = "result_exchange";
    private final static String INFO_EXCHANGE = "info_exchange";
    private final static String LOG_QUEUE = "admin_log_queue";
    private final String info_queue;
    private final String[] specializations;
    private final String technicianName;
    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;


    public Technician(String technicianName, String[] specializations) throws IOException, TimeoutException {
        this.specializations = specializations;
        this.technicianName = technicianName;
        this.info_queue = technicianName + "_info_queue";

        this.factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        channel.exchangeDeclare(ORDER_EXCHANGE, BuiltinExchangeType.DIRECT);
        for (String spec : specializations){
            channel.queueDeclare(spec + "_queue", false, false, false, null);
            channel.queueBind(spec + "_queue", ORDER_EXCHANGE, spec);
        }

        channel.exchangeDeclare(INFO_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(info_queue, false, false, false, null);
        channel.queueBind(info_queue, INFO_EXCHANGE, "");
    }

    public void startListening() throws IOException {
        startListeningForOrders();
        startListeningForInfo();
        System.out.println("["+technicianName+"]: Started listening...");
    }

    public void startListeningForOrders() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("["+technicianName+"]: Received order: " + message);
                String[] parts = message.split(":");
                String doctorName = parts[0].trim();
                String[] parts2 = parts[1].split("-");
                String patientName = parts2[0].trim();
                String type = parts2[1].trim();
                String result = patientName + ": " + type + " - done";
                channel.basicPublish(RESULT_EXCHANGE, doctorName, null, result.getBytes());
                System.out.println("["+technicianName+"]: Sent result: " + result);
                logActivity("RESULT: " + result);
            }
        };

        for (String spec : specializations) {
            channel.basicConsume(spec + "_queue", true, consumer);
        }
    }

    public void startListeningForInfo() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("["+technicianName+"]: Received info: " + message);
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

    public static void main(String[] argv) throws Exception {
        Technician technician1 = new Technician("Technician1", new String[]{"knee", "hip"});
        Technician technician2 = new Technician("Technician2", new String[]{"knee", "elbow"});

        technician1.startListening();
        technician2.startListening();
    }
}
