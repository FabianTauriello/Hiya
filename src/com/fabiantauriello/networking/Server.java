package com.fabiantauriello.networking;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 9090;
    public static void main(String[] args) {
    	ServerSocket listener;
        try {
        	listener = new ServerSocket(PORT);
            System.out.println("The capitalization server is running...");
            // Thread Pools are useful when you need to limit the number of threads running in your 
            // application to optimize performance. Here, I've set the pool size to accept 20 threads.
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Capitalizer(listener.accept()));
                // System.out.println("Current pool size: " + pool.toString());
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
