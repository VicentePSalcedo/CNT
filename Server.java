import java.io.*;
import java.net.*;
import java.util.Date;
 

public class Server {
 
    public static void main(String[] args) {
        if (args.length < 1) return;
 
        int port = Integer.parseInt(args[0]);
 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            long startTime = System.nanoTime();
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
 
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
 
                // String s = null;
                String text;
 
                do {
                    text = reader.readLine();
                    // parse option from client to integer for evaluation
                    int option = Integer.parseInt(text);
                    if (option == 1){
                        writer.println(new Date().toString());
                    } else if(option ==2 ){
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime);
                        writer.println(duration);
                    } else if(option == 3){
                        long UsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
                        writer.println(UsedMem);
                    } else if(option == 4){
                        long UsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
                        writer.println(UsedMem);
                    } else {
                        writer.println("Wrong Answer." + "'" + text + "'");
                    }
                } while (!text.equals("bye"));
 
                socket.close();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}