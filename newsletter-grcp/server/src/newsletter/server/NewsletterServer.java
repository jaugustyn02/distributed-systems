package newsletter.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class NewsletterServer {
    private final int port = 50051;
    private Server server;

    public NewsletterServer() {
        server = ServerBuilder.forPort(port)
                .addService(new NewsletterServiceImpl())
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down server...");
                NewsletterServer.this.stop();
                System.out.println("Server shut down.");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final NewsletterServer server = new NewsletterServer();
        server.start();
        server.blockUntilShutdown();
    }
}
