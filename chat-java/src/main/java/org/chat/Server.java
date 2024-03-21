package org.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;


public class Server {
    private ServerSocket serverTCPSocket = null;
    private final Map<Integer, PrintWriter> clientTCPOutStreams = new HashMap<>();
    private final Map<Integer, Socket> clientSockets = new HashMap<>();
    private final Map<Integer, String> clientNicknames = new HashMap<>();
    private final Object clientOutStreamsLock = new Object();
    private final Object clientSocketsLock = new Object();
    private final Object clientNicknamesLock = new Object();

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
                    (new ClientTCPHandler(clientSocket, UniqueClientID.getClientID())).start();
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
        private final int clientID;

        public ClientTCPHandler(Socket clientSocket, int clientID) {
            this.clientSocket = clientSocket;
            this.clientID = clientID;
            this.clientNickname = null;
        }

        public void run() {
            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            int clientPort = clientSocket.getPort();
            System.out.printf("\nNew client connected with address: %s, port: %d, clientID: %d",
                    clientAddress, clientPort, clientID);

            synchronized (clientSocketsLock){
                clientSockets.put(clientID, clientSocket);
            }

            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // Acknowledge and acceptance of client nickname
                clientNickname = in.readLine();
                out.println(clientNickname);
                synchronized (clientNicknamesLock){
                    clientNicknames.put(clientID, clientNickname);
                }

                synchronized (clientOutStreamsLock) {
                    clientTCPOutStreams.put(clientID, out);
                }

                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.printf("\nReceived message from client with ID: %d", clientID);
                    ClientMessage clientMessage = new ClientMessage(clientNickname, msg);
                    synchronized (clientOutStreamsLock) {
                        for (Map.Entry<Integer, PrintWriter> entry : clientTCPOutStreams.entrySet()) {
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
                    synchronized (clientNicknamesLock){
                        clientNicknames.remove(clientID);
                    }
                    System.out.printf("\nClient with address: %s, port: %d, clientID: %d has disconnected",
                            clientAddress, clientPort, clientID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                    int clientID = getClientID(senderAddress, senderPort);
                    if (clientID != -1){
                        System.out.printf("\nReceived datagram from client with ID: %d", clientID);
                    } else {
                        System.out.printf("\nReceived datagram from client with address: %s and port: %d",
                                senderAddress.getHostAddress(), senderPort);
                    }
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
            System.out.print("<server>: ");
            while (!Objects.equals(command = scanner.nextLine(), "exit")) {
                switch (command) {
                    case "count", "c" -> {
                        int no_clients = clientTCPOutStreams.size();
                        System.out.printf("Connected clients: %d\n", no_clients);
                    }
                    case "help", "h" -> {
                        System.out.println("Available commands:");
                        System.out.println("\t-count -c\tshow number of currently connected clients");
                        System.out.println("\t-list -ls\tshow list of currently connected clients");
                        System.out.println("\t-help -h\tshow list of available commands");
                        System.out.println("\t-exit\t\tclose terminal");
                        System.out.println("\t-'ctrl + c'\tshutdown server\n");
                    }
                    case "list", "ls" -> {
                        System.out.println("ClientID\tNickname\tAddress\t\tPort");
                        synchronized (clientSocketsLock) {
                            synchronized (clientNicknamesLock) {
                                for (Map.Entry<Integer, Socket> entry : clientSockets.entrySet()) {
                                    int clientID = entry.getKey();
                                    String nickname = clientNicknames.getOrDefault(clientID, "-");
                                    String address = entry.getValue().getInetAddress().getHostAddress();
                                    int port = entry.getValue().getPort();

                                    System.out.printf("%-8d\t%-10s\t%-15s\t%d\n", clientID, nickname, address, port);
                                }
                            }
                        }
                    }
                }
                System.out.print("<server>: ");
            }
        }
    }

    private static class UniqueClientID{
        private static int nextID = 0;
        public static int getClientID(){
            return nextID++;
        }
    }

    private record ClientMessage(String nickname, String message) {
        @Override
        public String toString() {
            return String.format("<%s>: %s", nickname, message);
        }
    }

    private Integer getClientID(InetAddress address, Integer port){
        synchronized (clientSocketsLock) {
            for (Map.Entry<Integer, Socket> entry : clientSockets.entrySet()) {
                Socket socket = entry.getValue();
                if (address.equals(socket.getInetAddress()) && port.equals(socket.getPort())) {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }
}
