
import java.util.Stack;
import java.util.HashMap;
import java.util.Set;

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
    
    private HashMap<String, Item> inventory;
    
    private Room beamerLocation;
    
    /**
     * constructor Player
     * Een player krijgt een huidig gewicht (currentWeight),
     * en een max gewicht (maxWeight)
     */
    public Player(int currentWeight, int maxWeight){

        this.currentWeight = currentWeight;
        this.maxWeight = maxWeight;
        
        roomHistory = new Stack<Room>();
        inventory = new HashMap<>();
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
    
    public boolean pickUpItem(String itemName, Item item){
        if(canPickUpItem(item)){
            inventory.put(itemName, item);
            currentWeight += item.getItemWeight();
            return true;
        }
        
        else{
            return false;
        }
    }
    
    public boolean dropItem(String itemName){
        if(inventory.containsKey(itemName)){
            Item itemToDrop = inventory.get(itemName);
            currentWeight -= itemToDrop.getItemWeight();
            inventory.remove(itemName);
            return true;
        }
        
        else{
            return false;
        }
        
    }
    
    public String getInventory(){
        String returnString = "Your current items are:";
        
        if(inventory.isEmpty()){
            returnString += " " + "no items in inventory";
        }else{
            Set<String> items = inventory.keySet();
            for(String object : items){
                returnString += " " + object;
            }
        }
        
        return returnString;
    }
    
    public Item getInventoryByName(String itemName){
        return inventory.get(itemName);
    }
    
    public boolean isInInventory(String itemName){
        if(inventory.containsKey(itemName)){
            return true;
        }
        return false;
    }
    
    public int getCurrentWeight(){
        return currentWeight;
    }
    
    public boolean canPickUpItem(Item item) {
        if ((currentWeight + item.getItemWeight()) > maxWeight) {
            return false;
        }
        return true;
    }
    
    public void setBeamerLocation(Room beamerLocation){
        this.beamerLocation = beamerLocation;
    }
    
    public Room getBeamerLocation(){
        
        if(beamerLocation != null){
            return beamerLocation;
        }
        return null;
    }

}
