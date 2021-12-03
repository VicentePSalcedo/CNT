package WindowsServerDeployment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
 
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            try (ServerSocket serverSocket = new ServerSocket(1313)) {
                System.out.println("Server is listening on port 1313");
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");
                    new ServerThread(socket,startTime).start();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occured while establishing a new client connection.");
            e.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    Socket socket;
    long startTime;
    public ServerThread(Socket socket, long startTime){
        this.socket = socket;
        this.startTime = startTime;
    }
    public void run(){
        String serverOutputString;
        try{

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            String userInputString;
            Integer userInputInteger;

            userInputString = reader.readLine();
                
            userInputInteger = Integer.parseInt(userInputString);

            serverOutputString = optionLogic(userInputInteger, startTime);

            writer.println(serverOutputString);
            
            socket.close();
            System.out.println("Client disconected.");
        } catch (IOException ex) {
            System.out.println("An error occured while establishing a new client connection.");
            ex.printStackTrace();
        }
    }
    static String optionLogic(Integer userInputInteger, long startTime){
        String output;
        switch (userInputInteger) {
            case 1:
                output = dateAndTime();
                break;
            case 2:
                output = upTime(startTime);
                break;
            case 3:
                output = memoryUse();
                break;
            case 4:
                output = netStat();
                break;
            case 5:
                output = currentUsers();
                break;
            case 6:
                output = runningProcess();
                break;
            default:
                output = "Invalid Option";
                break;
        }
        return output;
    }
    
    static String dateAndTime(){
        System.out.println("Date and Time Requested\nPlease wait...");
        String date = "";
        date = new Date().toString();
        System.out.println("Done.");
        return "Date and Time Request: " + date;
    }

    static String upTime(long startTime){
        System.out.println("Uptime Requested\nPlease wait...");
        String totalTimeString = "";
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        totalTimeString = Long.toString(totalTime);
        System.out.println("Done.");
        return "Up Time Request: " + totalTimeString + " milliseconds";
    }
    
    static String memoryUse(){
        System.out.println("Memory Use Requested\nPlease wait...");
        String usedMemString = "";
        long usedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        usedMemString = Long.toString(usedMem);
        System.out.println("Done.");
        return "Memory usage request: " + usedMemString + "KB";
    }

    static String netStat(){
        System.out.println("Net Work Status requested\nPlease wait...");
        String currentLine = "";
        String netStatString = "";
        try {
            Process NetStat = Runtime.getRuntime().exec("netstat");
            BufferedReader reader = new BufferedReader(new InputStreamReader(NetStat.getInputStream()));
            while ((currentLine = reader.readLine()) != null) {
                netStatString = netStatString + currentLine + "\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occured while attempting to retrive network status.");
        }
        System.out.println("Done.");
        return "Network Status Request: " + netStatString;
    }

    static String currentUsers(){ 
        System.out.println("Current Users Requested\nPlease wait...");
        String currentLine = "";
        String currentUserString = "";
        try{
            Process CurrentUsers = Runtime.getRuntime().exec("whoami");
            BufferedReader reader = new BufferedReader(new InputStreamReader(CurrentUsers.getInputStream()));
            while ((currentLine = reader.readLine()) != null){
                currentUserString = currentUserString + currentLine + "\n";
            }
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("An error occured while attempting to retrive current users.");
        }
        System.out.println("Done.");
        return "Current User Request: " + currentUserString;
    }

    static String runningProcess(){
        System.out.println("Running Process\nPlease wait...");
        String currentLine = "";
        String runningProcessString = "";
        try {
            Process RunningProcess = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(RunningProcess.getInputStream()));
            while ((currentLine = reader.readLine()) != null){
                runningProcessString = runningProcessString + currentLine + "\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occured while attempting to retrive current processes.");
        }
        System.out.println("Done.");
        return "Running Process Request: " + runningProcessString;
    }
}