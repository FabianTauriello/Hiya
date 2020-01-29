package com.fabiantauriello.networking;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 9090;
    private static final String SERVER_HOST = "127.0.0.1";
    public static void main(String[] args) {
        try {
        	Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Enter lines of text then Ctrl+D or Ctrl+C to quit");
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                System.out.println(in.nextLine());
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
