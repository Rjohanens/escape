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
    int secondPassed = 900000; //15 min
    
    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
        public void run(){
           if(clockRunning == true){
                secondPassed--;
                System.out.println(secondPassed);
                //int minutes = (secondPassed / 1000) / 60;
                //int seconds = (secondPassed / 1000) % 60;
           }
        }
          
    };
    public void getTime(){
        int timer = secondPassed;
        int minutes = (secondPassed / 1000) / 60;
        int seconds = (secondPassed / 1000) % 60;
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
