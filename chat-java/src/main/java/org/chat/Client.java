package org.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private final String nickname;

    public Client(String nickname){
        this.nickname = nickname;
    }

    public static void main(String[] args){
        System.out.println("CLIENT IS RUNNING");
        Client client = new Client(getNickname());
        client.start("127.0.0.1", 2137, "230.0.0.1", 4446);
    }

    public void start(String serverAddress, int serverPort, String multicastAddress, int multicastPort){
        try (Socket tcpSocket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
             DatagramSocket udpSocket = new DatagramSocket(tcpSocket.getLocalPort())){

            MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(multicastAddress));

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    multicastSocket.leaveGroup(InetAddress.getByName(multicastAddress));
                    multicastSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            while (true){
                out.println(nickname);
                String response = in.readLine();
                if (Objects.equals(response, nickname)) break;
            }
            System.out.printf("Connected successfully to server as '%s'\n", nickname);

            (new TCPReceiver(in)).start();
            (new UDPReceiver(udpSocket)).start();
            (new MulticastReceiver(multicastSocket)).start();

            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.printf("<%s>: ", nickname);
                String msg = scanner.nextLine();
                if (msg.equals("U")){
                    sendUDPDatagram(udpSocket, serverAddress, serverPort);
                }
                else if (msg.equals("M")){
                    sendMulticastDatagram(multicastSocket, multicastAddress, multicastPort);
                }
                else if (!msg.equals("")) {
                    out.println(msg);
                }
            }
        } catch (ConnectException ce){
            System.out.println("Couldn't connect with the server: " + ce.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class TCPReceiver extends Thread {
        private final BufferedReader in;

        public TCPReceiver(BufferedReader in){
            this.in = in;
        }
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.print("\n" + serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.print("\nThe connection with the server has been closed");
            }
        }
    }

    private static class UDPReceiver extends Thread {
        private final DatagramSocket socket;
        private final byte[] msgBuffer = new byte[1024];

        public UDPReceiver(DatagramSocket socket){
            this.socket = socket;
        }

        public void run() {
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(msgBuffer, msgBuffer.length);
                    socket.receive(packet);
                    String data = new String(packet.getData(), 0, packet.getLength());
                    System.out.printf("\n[UDP]:\n%s\n", data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MulticastReceiver extends Thread {
        private final MulticastSocket socket;
        private final byte[] msgBuffer = new byte[1024];

        private MulticastReceiver(MulticastSocket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(msgBuffer, msgBuffer.length);
                    socket.receive(packet);
                    String data = new String(packet.getData(), 0, packet.getLength());
                    System.out.printf("\n[Multicast]:\n%s\n", data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendUDPDatagram(DatagramSocket socket, String host, int port) throws IOException {
        if (!socket.isClosed()){
            String asciiArt = """
                                 ⠀⠀⠀⠀⢀⣴⣶⠿⠟⠻⠿⢷⣦⣄⠀⠀⠀
                                 ⠀⠀⠀⠀⣾⠏⠀⠀⣠⣤⣤⣤⣬⣿⣷⣄⡀
                                 ⠀⢀⣀⣸⡿⠀⠀⣼⡟⠁⠀⠀⠀⠀⠀⠙⣷
                                 ⢸⡟⠉⣽⡇⠀⠀⣿⡇⠀⠀⠀⠀⠀⠀⢀⣿
                                 ⣾⠇⠀⣿⡇⠀⠀⠘⠿⢶⣶⣤⣤⣶⡶⣿⠋
                                 ⣿⠂⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠃
                                 ⣿⡆⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀
                                 ⢿⡇⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⠀
                                 ⠘⠻⠷⢿⡇⠀⠀⠀⣴⣶⣶⠶⠖⠀⢸⡟⠀
                                 ⠀⠀⠀⢸⣇⠀⠀⠀⣿⡇⣿⡄⠀⢀⣿⠇⠀
                                 ⠀⠀⠀⠘⣿⣤⣤⣴⡿⠃⠙⠛⠛⠛⠋⠀⠀""";
            byte[] data = asciiArt.getBytes();
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
        }
    }

    private static void sendMulticastDatagram(MulticastSocket socket, String host, int port) throws IOException {
        if (!socket.isClosed()){
            String asciiArt = """
⡏⠙⠻⢦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⠶⠟⠛⠿⠗⣦⣄⠀⠀⠀⠀
⡇⠀⠀⠀⠀⠙⠦⣄⠀⠀⠀⠀⠀⠀⠀⢀⣴⠋⠀⠀⠀⠀⠀⠀⠀⠉⢧⡀⠀⠀
⠙⢄⠀⠀⠀⠀⠀⠙⠲⢦⣀⠀⠀⠀⠀⣸⠋⠀⠀⠀⢠⣤⠀⠀⠀⠀⠘⢳⡀⠀
⠀⠈⠳⣀⠀⠀⠀⠀⠀⠀⠉⠻⢦⣤⣼⠴⠖⠒⣄⠀⠈⠉⠀⠀⠀⠀⠀⠈⣵⠀
⠀⠀⠀⠈⠳⣄⠀⠀⠀⠀⠀⠀⢀⣿⠋⠀⠀⠀⠈⢆⠀⠀⠀⠀⠀⠀⠀⠀⠀⢣
⠀⠀⠀⠀⠀⠈⠳⢦⡀⠀⠀⠀⣸⠁⠀⠀⠀⠀⣠⣼⣷⡤⢤⣄⠀⠀⠀⠀⠀⣻
⠀⠀⠀⠀⠀⠀⠀⠀⠙⢦⣤⣴⠋⠀⠀⠀⢀⣴⣿⣿⢿⠁⢘⣻⠀⠀⠀⠀⠀⣿
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣔⣢⣲⣼⡿⣿⣿⠟⠁⠀⢸⠾⠀⠀⠀⠀⠀⢸
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠫⣿⠋⠀⠀⠀⣾⡇⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠇⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⠀⠀⠀⠀⠀⠀⠀""";
            byte[] data = asciiArt.getBytes();
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
        }
    }

    private static String getNickname(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter nickname: ");
        String nickname = scanner.nextLine().trim();
        while(nickname.length() < 3){
            System.out.println("Nickname should be at least 3 characters long");
            System.out.print("Enter nickname: ");
            nickname = scanner.nextLine().trim();
        }
        return nickname;
    }

}
