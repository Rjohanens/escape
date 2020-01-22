import java.util.Timer;
import java.util.TimerTask; 
/**
 * class Timer - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class CountdownTimer
{
    private boolean clockRunning = false;
    public boolean gameStopped = false;
    public int secondPassed = 600; //10 min
    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
        public void run(){
           if(clockRunning == true){
                secondPassed--;
                if(secondPassed == 599){
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
                    gameStopped = true;
                }
                //int minutes = (secondPassed / 1000) / 60;
                //int seconds = (secondPassed / 1000) % 60;
           }
        }
          
    };
    
    /**
     *  Retouneer het aantal minuten dat de speler nog over heeft.
     *  
     *  @return Het aantal minuten dat nog over is.
     */
    public int getMinutes(){
        int timer = secondPassed;
        int minutes = secondPassed / 60;
        return minutes;
    }
    
    /**
     *  retouneer het aantal seconden dat de speler nog over heeft.
     *  
     *  @return Het aantal seconden dat de speler nog heeft.
     */
    public int getSeconds(){
        int timer = secondPassed;
        int seconds = secondPassed % 60;
        
        return seconds;
    }
    
    /**
     *  Start de timer.
     */
    public void startTimer(){
        clockRunning = true;
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    
    /**
     *  Stop de timer.
     */
    public void stopTimer(){
        clockRunning = false;
        gameStopped = true;
    }
}
