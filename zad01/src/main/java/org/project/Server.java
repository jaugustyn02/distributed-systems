package org.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;


public class Server {
    private ServerSocket serverTCPSocket = null;
    private final Map<Long, PrintWriter> clientTCPOutStreams = new HashMap<>();
    private final Map<Long, Socket> clientSockets = new HashMap<>();
    private final Object clientOutStreamsLock = new Object();
    private final Object clientSocketsLock = new Object();


    public static void main(String[] args) {
        Server server = new Server();
        server.start(2137);
    }

    public void start(int portNumber) {
        System.out.println("SERVER IS RUNNING ON PORT: " + portNumber);

        (new ServerConsole()).start();

        try {
            DatagramSocket serverUDPSocket = new DatagramSocket(portNumber);
            (new ClientUDPHandler(serverUDPSocket)).start();

            serverTCPSocket = new ServerSocket(portNumber);
            while (!serverTCPSocket.isClosed()) {
                try {
                    Socket clientSocket = serverTCPSocket.accept();
                    (new ClientTCPHandler(clientSocket)).start();
                } catch (SocketException se) {
                    break;
                }
            }
        } catch (IOException e) {
            if (!serverTCPSocket.isClosed()) {
                e.printStackTrace();
            }
        }
    }

    private class ClientTCPHandler extends Thread {
        private final Socket clientSocket;
        private String clientNickname;
        private Long clientID;

        public ClientTCPHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.clientID = null;
            this.clientNickname = null;
        }

        public void run() {
            clientID = Thread.currentThread().getId();

            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            int clientPort = clientSocket.getPort();
            System.out.printf(
                    "\nNew client connected with address: %s, port: %d and clientID: %d",
                    clientAddress, clientPort, clientID
            );

            synchronized (clientSocketsLock){
                clientSockets.put(clientID, clientSocket);
            }

            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // Nickname acknowledge
                clientNickname = in.readLine();
                out.println(clientNickname);

                synchronized (clientOutStreamsLock) {
                    clientTCPOutStreams.put(clientID, out);
                }

                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.printf("\nReceived message from client with ID: %d", clientID);
                    ClientMessage clientMessage = new ClientMessage(clientNickname, msg);
                    synchronized (clientOutStreamsLock) {
                        for (Map.Entry<Long, PrintWriter> entry : clientTCPOutStreams.entrySet()) {
                            if (!Objects.equals(entry.getKey(), clientID)) {
                                entry.getValue().println(clientMessage);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    synchronized (clientOutStreamsLock) {
                        clientTCPOutStreams.remove(clientID);
                    }
                    synchronized (clientSocketsLock){
                        clientSockets.remove(clientID);
                    }
                    System.out.printf("\nClient with ID: %d has disconnected\n", clientID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private record ClientMessage(String nickname, String message) {
        @Override
        public String toString() {
            return String.format("<%s>: %s", nickname, message);
        }
    }

    private class ClientUDPHandler extends Thread{
        private final DatagramSocket serverSocket;
        private final byte[] msgBuffer = new byte[1024];

        public ClientUDPHandler(DatagramSocket serverSocket){
            this.serverSocket = serverSocket;
        }

        public void run(){
            try {
                while(true){
                    DatagramPacket packet = new DatagramPacket(msgBuffer, msgBuffer.length);
                    serverSocket.receive(packet);
                    InetAddress senderAddress = packet.getAddress();
                    int senderPort = packet.getPort();
                    String data = new String(packet.getData(), 0, packet.getLength());
                    System.out.printf("\nReceived datagram from client with address: %s and port: %d\n", senderAddress.getHostAddress(), senderPort);
                    synchronized (clientSocketsLock) {
                        for (Socket socket : clientSockets.values()) {
                            if (socket.getInetAddress() != senderAddress && socket.getPort() != senderPort) {
                                packet.setAddress(socket.getInetAddress());
                                packet.setPort(socket.getPort());
                                serverSocket.send(packet);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                serverSocket.close();
            }
        }
    }

    private class ServerConsole extends Thread {
        public void run() {
            String command;
            Scanner scanner = new Scanner(System.in);
            System.out.print("<server> ");
            while (!Objects.equals(command = scanner.nextLine(), "shutdown")) {
                if (Objects.equals(command, "count")) {
                    int no_clients = clientTCPOutStreams.size();
                    System.out.printf("<server> Connected clients: %d\n", no_clients);
                }
                if (Objects.equals(command, "help")){
                    System.out.println("Available commands:");
                    System.out.println("\t-count\t\tshows number of currently connected clients");
                    System.out.println("\t-shutdown\tshutdowns server");
                    System.out.println("\t-help\t\tshow list of available commands\n");
                }
                System.out.print("<server>: ");
            }
            try {
                if (serverTCPSocket != null) {
                    serverTCPSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
