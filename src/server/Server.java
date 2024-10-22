package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import app.CalculatorApp.Calculator;
import app.CardGame.Game;
import app.WordDistribute.WordDistribution;

public class Server{

    private static ServerSocket socket;
    private static Socket sock;

    public static void main(String[] args) {
        try{
            int port = 3000;

            //Create socket
            socket = new ServerSocket(port);
            
            try{
                System.out.printf("Waiting for connection on port %d\n", port);
                sock = socket.accept();
                System.out.println("Got a new connection\n");

                InputStream is = sock.getInputStream();
                Reader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);

                // Get the output stream
                OutputStream os = sock.getOutputStream();
                Writer writer = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(writer);

                // Read from the client
                String fromClient;
                while ((fromClient = br.readLine()) != null) {
                    if ("close".equalsIgnoreCase(fromClient.trim())) {
                        System.out.println("Exit command received. Shutting down server...");
                        System.exit(0);; // Call the method to stop the server
                        break;
                    }
                    if ("play".equalsIgnoreCase(fromClient.trim())){
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("NEW GAME");
                        Game game = new Game();
                        String result = game.play();
                        bw.write(result);
                        bw.newLine();
                        bw.flush();
                    }if("calculator".equalsIgnoreCase(fromClient.trim())){
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("Calculator started.");
                        Calculator calculator = new Calculator();
                        
                        while (true) {
                            fromClient = br.readLine(); // Read input from client
                            if (fromClient == null || "exit".equalsIgnoreCase(fromClient.trim())) {
                                System.out.println("Exiting calculator mode.");
                                break; // Exit calculator mode on "exit"
                            }
                            
                            try {
                                // Calculate the result and send it back to the client
                                int result = calculator.calculate(fromClient.trim());
                                System.out.println("Result: " + result); // Log the result on the server
                                bw.write("Result: " + result);
                                bw.newLine();
                                bw.flush();
                            } catch (NumberFormatException e) {
                                bw.write("Invalid input. Please enter a valid equation.");
                                bw.newLine();
                                bw.flush();
                            }
                        }
                    }
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                System.out.println("Server socket closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
