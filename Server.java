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
 
                String s = null;
                String text;
 
                do {
                    text = reader.readLine();
                    // parse option from client to integer for evaluation
                    int option = Integer.parseInt(text);
                    if (option == 1){
                        writer.println("Server: " + text);
                    } else if(option ==2 ){
                        writer.println(new Date().toString());
                    } else if(option == 3){
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime);
                        writer.println(duration);
                    } else if(option == 4){
                        try {
            
                            // run the Unix "ps -ef" command
                                // using the Runtime exec method:
                                Process p = Runtime.getRuntime().exec("ps -ef");
                                
                                BufferedReader stdInput = new BufferedReader(new 
                                     InputStreamReader(p.getInputStream()));
                    
                                BufferedReader stdError = new BufferedReader(new 
                                     InputStreamReader(p.getErrorStream()));
                    
                                // read the output from the command
                                System.out.println("Here is the standard output of the command:\n");
                                while ((s = stdInput.readLine()) != null) {
                                    System.out.println(s);
                                }
                                
                                // read any errors from the attempted command
                                System.out.println("Here is the standard error of the command (if any):\n");
                                while ((s = stdError.readLine()) != null) {
                                    System.out.println(s);
                                }
                                
                                System.exit(0);
                            }
                            catch (IOException e) {
                                System.out.println("exception happened - here's what I know: ");
                                e.printStackTrace();
                                System.exit(-1);
                            }
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