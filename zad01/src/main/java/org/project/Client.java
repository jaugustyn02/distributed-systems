package org.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("CLIENT IS RUNNING");
        String hostName = "localhost";
        int PortNumber = 2137;

        try (Socket socket = new Socket(hostName, PortNumber)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String nickname;
            while (true){
                nickname = getNickname();
                out.println(nickname);
                String response = in.readLine();
                if (Objects.equals(response, nickname)){
                    System.out.println("Connected successfully as " + nickname);
                    break;
                }
            }

            Thread messageReader = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("\n"+serverMessage);
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        e.printStackTrace();
                    }
                }
            });
            messageReader.start();

            String msg = "";
            Scanner scanner = new Scanner(System.in);
            while (!msg.equals("/q")){
                System.out.print("<tcp> ");
                msg = scanner.nextLine();
                if (!msg.equals("")  && msg.charAt(0) != '/')
                    out.println(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
