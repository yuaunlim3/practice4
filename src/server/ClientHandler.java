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
import java.net.Socket;

import app.CalculatorApp.Calculator;
import app.CardGame.Game;

public class ClientHandler implements Runnable{

    private final Socket sock;

    public ClientHandler(Socket s){
        sock = s;
    }
    @Override 
    public void run(){
        String threadName = Thread.currentThread().getName();
        try{
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
                        threadedServer.stopServer(); // Call the method to stop the server
                        break;
                    }
                    if ("play".equalsIgnoreCase(fromClient.trim())){
                        System.out.println("------------------------------------------------------------------");
                        System.out.printf("[%s]>>> New Game\n",threadName);
                        Game game = new Game();
                        String result = game.play();
                        bw.write("["+threadName +"] ===>" + result);
                        bw.newLine();
                        bw.flush();
                    }if("calculator".equalsIgnoreCase(fromClient.trim())){
                        System.out.println("------------------------------------------------------------------");
                        System.out.printf("[%s]>>> Calculator started\n",threadName);
                        Calculator calculator = new Calculator();
                        
                        while (true) {
                            fromClient = br.readLine(); // Read input from client
                            if (fromClient == null || "exit".equalsIgnoreCase(fromClient.trim())) {
                                System.out.printf("[%s]>>>Exiting calculator mode.\n",threadName);
                                break; // Exit calculator mode on "exit"
                            }
                            
                            try {
                                // Calculate the result and send it back to the client
                                int result = calculator.calculate(fromClient.trim());
                                bw.write("["+threadName +"] ===>" + result);
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

        }
        catch(IOException ex){
            //Exception Handler
            ex.getMessage();
        }finally{
            try{
                System.out.println("Socket close");
                sock.close();}
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
