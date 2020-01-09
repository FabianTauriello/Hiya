package com.fabiantauriello.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    // The port number here isn't important - as long as it's in range and not being used by another application.
    private static final int PORT = 9090;
    public static void main(String[] args) {
        // Declare ServerSocket
        ServerSocket listener;
        try {
            // Initialize ServerSocket
            listener = new ServerSocket(PORT);
            System.out.println("The date server is running...");
            // Keep the server open and listening for a client to connect
            while (true) {
                // Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made.
                Socket socket = listener.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Output the current date to the connected client.
                out.println(new Date().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
