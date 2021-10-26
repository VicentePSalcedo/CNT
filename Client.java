import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
 
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6868);
            
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            Console console = System.console();
            String userInputString;
            String serverOutpuString;
            
            userInputString = console.readLine("Select Option 1-6\n");

            writer.println(userInputString);
            
            serverOutpuString = ReadBigString(reader);
            
            System.out.println(serverOutpuString);
                
            
            socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String ReadBigString(BufferedReader reader) throws IOException{
        StringBuilder everything = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            everything.append(line + "\n");
        }
        return everything.toString();
    }
}