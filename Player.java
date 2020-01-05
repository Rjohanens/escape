
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
    
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> roomHistory;
    
    
    public Player(int maxWeight, int currentWeight){
        this.maxWeight = maxWeight;
        this.currentWeight = currentWeight;
        
        roomHistory = new Stack<Room>();
    }
    
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    public void setCurrentRoom(Room currentRoom) {
	this.currentRoom = currentRoom;
    }
    
    public Room getPreviousRoom(){
        
        if(roomHistory.empty()){
            return null;
        }else{
            this.currentRoom = roomHistory.pop();
            return currentRoom;
        }
    }
    
    public void setPreviousRoom(Room currentRoom){
        this.currentRoom = roomHistory.push(currentRoom);
    }
    
    public Stack getHistoryRooms(){
        if(roomHistory.empty()){
            return null;
        }else{
            return roomHistory;
        }
    }
}
