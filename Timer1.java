import java.util.Timer;
import java.util.TimerTask; 
/**
 * class Timer - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Timer1
{
    private boolean clockRunning = false;
    int secondPassed = 900; //15 min
    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
        public void run(){
           if(clockRunning == true){
                secondPassed--;
                if(secondPassed == 899){
                    System.out.println("You have 15 minutes left to escape!");
                }
                if(secondPassed == 600){
                    System.out.println("You have 10 minutes left to escape!");
                }
                if(secondPassed == 300){
                    System.out.println("You have 5 minutes left to escape!");
                }
                if(secondPassed == 60){
                    System.out.println("You have 1 minute left to escape!");
                }
                if(secondPassed == 30){
                    System.out.println("You have 30 seconds left to escape!");
                }
                if(secondPassed == 1){
                    System.out.println("Time is up! You got caught!");
                    clockRunning = false;
                }
                //int minutes = (secondPassed / 1000) / 60;
                //int seconds = (secondPassed / 1000) % 60;
           }
        }
          
    };
    public void getTime(){
        int timer = secondPassed;
        int minutes = secondPassed / 60;
        int seconds = secondPassed % 60;
        System.out.println(minutes + ":" + seconds);
    }
    
    public void startTimer(){
        clockRunning = true;
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    
    public void stopTimer(){
        clockRunning = false;
    }
}
