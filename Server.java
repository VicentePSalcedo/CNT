import java.util.Date;
import java.util.Scanner;

public class Server {
 
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Scanner scanner = new Scanner(System.in);

        System.out.println("1.Date and Time\n2.Uptime\n3.Memory Use\n4.Netstat\n5.Current Users\n6.Running Processes\nPlease select an option by number:");
        Integer option = scanner.nextInt();
        System.out.println("Option Selected was: " + option);
        
       
        String output = "Empty";
        switch (option) {
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
            System.out.println("Select Valid Option");
            break;
        }
        System.out.println(output);
        scanner.close();
    }

    static String dateAndTime(){
        String date = new Date().toString();
        return date;
    }

    static String upTime(long startTime){
        String totalTimeString;
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        totalTimeString = Long.toString(totalTime);
        return totalTimeString;
    }
    
    static String memoryUse(){
        return null;
    }

    static String netStat(){
        return null;
    }

    static String currentUsers(){
        return null;
    }

    static String runningProcess(){
        return null;
    }

}