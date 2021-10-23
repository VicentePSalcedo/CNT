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
        long startTime = System.nanoTime();
        try {
            ServerSocket serverSocket = new ServerSocket(6868);
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                String userInputString = reader.readLine();
                Integer userInpuInteger = Integer.parseInt(userInputString);
                
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                
                String outputString;
                switch (userInpuInteger) {
                    case 1:
                        outputString = dateAndTime();
                        break;
                    case 2:
                        outputString = upTime(startTime);
                        break;
                    case 3:
                        outputString = memoryUse();
                        break;
                    case 4:
                        outputString = netStat();
                        break;
                    case 5:
                        outputString = currentUsers();
                        break;
                    case 6:
                        outputString = runningProcess();
                        break;
                    default:
                        outputString = "Something went wrong on the server side";
                        break;
                }
                writer.println(outputString);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String dateAndTime(){
        String date = "";
        date = new Date().toString();
        return date;
    }

    static String upTime(long startTime){
        String totalTimeString = "";
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        totalTimeString = Long.toString(totalTime);
        return totalTimeString;
    }
    
    static String memoryUse(){
        String usedMemString = "";
        long usedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        usedMemString = Long.toString(usedMem);
        return usedMemString;
    }

    static String netStat(){
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
        }
        return netStatString;
    }

    static String currentUsers(){
        return "5 is not setup yet";
    }

    static String runningProcess(){
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
        }
        return runningProcessString;
    }

}