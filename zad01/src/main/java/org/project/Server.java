package org.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Server {
    private static final Map<Long, BlockingQueue<ClientMessage>> clientsMsgQueue = new HashMap<>();
    private static final Object mapLock = new Object();

    private record ClientMessage(String nickname, String message) {
        @Override
            public String toString() {
                return String.format("[%s]: %s", nickname, message);
            }
        }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(2137);
    }

    public void start(int portNumber){
        System.out.println("SERVER IS RUNNING ON PORT: " + portNumber);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                int clientPort = clientSocket.getPort();
                System.out.printf("New client connected with address: %s and port: %d\n", clientAddress, clientPort);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private String clientNickname;
        private Long clientID;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
            this.clientID = null;
            this.clientNickname = null;
        }

        public void run(){
            clientID = Thread.currentThread().getId();

            PrintWriter out = null;
            BufferedReader in = null;

            synchronized (mapLock){
                clientsMsgQueue.put(clientID, new LinkedBlockingQueue<>());
            }

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                clientNickname = in.readLine();
                out.println(clientNickname);

                String msg;
                while (true) {
                    synchronized (mapLock) {
                        BlockingQueue<ClientMessage> msgQueue = clientsMsgQueue.get(clientID);
                        for (ClientMessage clientMessage : msgQueue) {
                            out.println(clientMessage.toString());
                        }
                        msgQueue.clear();
                    }

                    if (in.ready()) {
                        msg = in.readLine();
                        if (msg == null) break;
                        System.out.printf("[%s]: %s\n", clientNickname, msg);
                        ClientMessage clientMessage = new ClientMessage(clientNickname, msg);
                        synchronized (mapLock) {
                            for (Map.Entry<Long, BlockingQueue<ClientMessage>> entry : clientsMsgQueue.entrySet()) {
                                if (!Objects.equals(entry.getKey(), clientID)) {
                                    entry.getValue().add(clientMessage);
                                }
                            }
                        }
                    }
                }
//                while ((msg = in.readLine()) != null) {
//                    System.out.printf("[%s]: %s\n", clientNickname, msg);
//                    ClientMessage clientMessage = new ClientMessage(clientNickname, msg);
//                    synchronized (mapLock){
//                        for (Map.Entry<Long, BlockingQueue<ClientMessage>> set : clientsMsgQueue.entrySet()){
//                            if (!Objects.equals(set.getKey(), clientID)){
//                                set.getValue().add(clientMessage);
//                            }
//                        }
//                    }
//                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
