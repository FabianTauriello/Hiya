package com.fabiantauriello.networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 9090;
    private static final String SERVER_HOST = "127.0.0.1";
    public static void main(String[] args) throws UnknownHostException, IOException {
        // Creates a socket and connects it to the specified port number (9090) on the named host (localhost).
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        Scanner in = new Scanner(socket.getInputStream());
        // Display the date sent from the server.
        System.out.println("Server response: " + in.nextLine());
        socket.close();
        in.close();
    }
}
