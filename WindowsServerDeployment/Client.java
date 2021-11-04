package WindowsServerDeployment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
 
    public static void main(String[] args) throws InterruptedException {
        try{
            File file = new File("log.txt");
            if (file.delete()){
                file.createNewFile();
            } else {
                file.createNewFile();
            }
        } catch (IOException e){
            System.out.println("An error occurred creating new log file.");
            e.printStackTrace();
        }
        
        String IP;
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the IP you would like to connect to?(Hint: \"localhost\")");
        IP = scanner.nextLine();

        Integer port;
        System.out.println("Which port lswould you like to connect to?(Hint: \"1313\")");
        port = scanner.nextInt();

        Integer threadsInteger;
        System.out.println("How many threads would you like to spawn?");
        threadsInteger = scanner.nextInt();
        
        Integer requestTypeInteger;
        System.out.println("What kind of requests?\n1 (Date and Time)\n2 (Uptime)\n3 (Memory Use)\n4 (Net Work Status)\n5 (Current Users)\n6 (Running Process)");
        requestTypeInteger = scanner.nextInt();
        scanner.close();
        
        List<Thread> threadCount = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int threads = 0; threads < threadsInteger; threads++) {
            Multithreading object = new Multithreading(requestTypeInteger,IP,port);
            object.start();
            threadCount.add(object);
        }

        for (Thread object: threadCount){
            object.join();
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        recordLog(totalTime);
        
        long averageTime = totalTime / threadsInteger;
        recordLog(averageTime);
    }
    static void recordLog(long totalTime){
        try{
            FileWriter logFileWriter = new FileWriter("log.txt", true);
            logFileWriter.write("total time " + totalTime + " milliseconds\n");
            logFileWriter.close();
        } catch (IOException e){
            System.out.println("An error occurred while writing to log file.");
            e.printStackTrace();
        }
    }
}

class Multithreading extends Thread {
    Integer requestTypeInteger;
    String IP;
    Integer port;
    public Multithreading(Integer requestTypeInteger, String IP, Integer port){
        this.requestTypeInteger = requestTypeInteger;
        this.IP = IP;
        this.port = port;
    }

    public void run(){
        try {
            
            Socket socket = new Socket(IP, port);
            long startTime = System.currentTimeMillis();
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            String serverOutpuString;
            writer.println(requestTypeInteger);
            serverOutpuString = ReadBigString(reader);
            System.out.println(serverOutpuString);
                
            socket.close();
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            recordLog(totalTime);
            
            // System.out.println(totalTime + " milliseconds\n");

        } catch (IOException e) {
            System.out.println("An error occurred while creating new thread.");
            e.printStackTrace();
        }
    }

    static void recordLog(long totalTime){
        try{
            FileWriter logFileWriter = new FileWriter("log.txt", true);
            logFileWriter.write(totalTime + " milliseconds\n");
            logFileWriter.close();
        } catch (IOException e){
            System.out.println("An error occurred while writing to log file.");
            e.printStackTrace();
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