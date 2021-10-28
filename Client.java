import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
 
    public static void main(String[] args) {
        long totalStartTime;
        long totalEndTime;
        long totalTime;
        
        long individualStartTime;
        long individualEndTime;
        long individualTime = 0;
        long individualSumTime = 0;
        long averageTime;

        Scanner scanner = new Scanner(System.in);
        System.out.println("How many threads would you like to spawn?");
        Integer threadsInteger = scanner.nextInt();
        scanner.close();
        
        totalStartTime = System.nanoTime();
        
        for (int threads = 0; threads < threadsInteger; threads++) {
            
            individualStartTime = System.nanoTime();
            
            Multithreading object = new Multithreading();
            object.start();
            
            individualEndTime = System.nanoTime();
            
            individualTime = individualEndTime - individualStartTime;
            
            System.out.println("Turn Around Time for this request: " + individualTime);

            individualSumTime = individualSumTime + individualTime;
        
        }
        
        totalEndTime   = System.nanoTime();
        
        totalTime = totalEndTime - totalStartTime;
        
        averageTime = individualSumTime / threadsInteger;

        System.out.println("Total Turn Around Time: " + totalTime);
        System.out.println("Average Turn Around Time: " + averageTime);
    }
}

class Multithreading extends Thread {
    public void run(){
        try {
            try {
                Socket socket = new Socket("localhost", 6868);
                
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                
                Integer userInput;
                String serverOutpuString;
                
                userInput = getRandomNumber(1,6);
    
                writer.println(userInput);
                
                serverOutpuString = ReadBigString(reader);
                
                System.out.println(serverOutpuString);
                    
                socket.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }

    static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
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