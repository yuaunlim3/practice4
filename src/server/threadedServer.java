package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class threadedServer {
    private static boolean running = true;
    private static ServerSocket serverSocket; // Keep a reference to the server socket

    public static void stopServer() {
        running = false; // Set the flag to false
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Close the server socket to stop accepting new connections
            }
            System.out.println("Server is stopping...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService thrPool = Executors.newFixedThreadPool(2); // Moved outside the try block

        try {
            int port = 3000;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }

            // Create a server socket
            serverSocket = new ServerSocket(port);

            try {
                while (running) {
                    System.out.printf("Waiting for connection on port %d\n", port);
                    Socket sock = serverSocket.accept();
                    System.out.println("Got a new connection");

                    ClientHandler handler = new ClientHandler(sock);
                    thrPool.submit(handler);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Wait for existing tasks to terminate
                thrPool.shutdown();
                try {
                    if (!thrPool.awaitTermination(60, TimeUnit.SECONDS)) {
                        thrPool.shutdownNow();
                    }
                } catch (InterruptedException ex) {
                    thrPool.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException ex) {
            ex.getMessage();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                System.out.println("Server socket closed.");
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
}
