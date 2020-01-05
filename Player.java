
import java.util.Stack;

/**
 * class Player - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Player
{
    private int maxWeight;
    private int currentWeight;
    
    private String name;
    
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> historyRooms;
    
    
    public Player(String name, int maxWeight, int currentWeight){
        this.name = name;
        this.maxWeight = maxWeight;
        this.currentWeight = currentWeight;
    }
    
    public String getPlayerName(){
        return name;
    }
    
    public void setPlayerName(String name){
        this.name = name;
    }
    
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    public void setCurrentRoom(Room currentRoom) {
	this.currentRoom = currentRoom;
    }
}
