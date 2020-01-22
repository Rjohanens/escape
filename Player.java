
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * class Player - de player van het spel 'escape'. Hier wordt het gewicht van de speler 
 * bijgehouden (met een inventory). De historie van de kamers wordt hier opgeslagen. 
 * Ook wordt de beamerlocatie bijgehouden, evenals welke items gebruikt zijn.
 *
 * @author Rick Johannes, Lars Bosker, Teijmen van der Ploeg
 * @version 2020.1.22
 */
public class Player
{
    private int maxWeight;
    private int currentWeight;
    
    private Room currentRoom;
    private Stack<Room> roomHistory;
    
    private HashMap<String, Item> inventory;
    
    private Room beamerLocation;
    
    private HashSet<Item> usedItems;
    
    /**
     * constructor Player
     * Een player krijgt een huidig gewicht (currentWeight),
     * en een max gewicht (maxWeight)
     * 
     * @param currentWeight Het huidige gewicht van de speler.
     * @param maxWeight Het maximale gewicht van de speler.
     */
    public Player(int currentWeight, int maxWeight){

        this.currentWeight = currentWeight;
        this.maxWeight = maxWeight;
        
        roomHistory = new Stack<Room>();
        inventory = new HashMap<>();
        usedItems = new HashSet<>();
    }
    
    /**
     * Retouneer de huidige kamer waar de player zich bevind.
     * 
     * @return De huidige room van de speler.
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    /**
     * 
     */
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
    
    public String getExamineString(String itemName){
        String returnString = "Examining: " + itemName + ".";
        
        if(isInInventory(itemName)){
            
            Item itemToExamine = inventory.get(itemName);
            
            returnString += " " + itemToExamine.getDescription() + ".";
            
            return returnString;
        }
        
        return null;
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
    
    public void setUsedItem(Item item){
        usedItems.add(item);
    }
    
    public boolean itemIsUsed(Item item){
        if(usedItems.contains(item)){
            return true;
        }  
        return false;
    }
}
