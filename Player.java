
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
     *  Set de huidige kamer van de player.
     *  
     *  @param currentRoom De huidige Room van de player.
     */
    public void setCurrentRoom(Room currentRoom) {
    this.currentRoom = currentRoom;
    }
    
    /**
     *  Retouneer de vorige kamer van de speler.
     *  
     *  @return De vorige kamer waar de speler zich bevond, wanneer er geen vorige is return null.
     */
    public Room getPreviousRoom(){
        
        if(roomHistory.empty()){
            return null;
        }else{
            this.currentRoom = roomHistory.pop();
            return currentRoom;
        }
    }
    
    /**
     *  Voeg de vorige ruimte toe aan aan de geschiedenis van de kamers
     *  
     *  @param currentRoom De vorige kamer van de speler.
     */
    public void setPreviousRoom(Room currentRoom){
        this.currentRoom = roomHistory.push(currentRoom);
    }
    
    /**
     *  Methode om items op te pakken. Check of de speler
     *  het item kan op pakken. Zo ja, voeg item toe
     *  aan inventory. Pas gewicht van de speler aan.
     *  
     *  @param itemName De naam van het item.
     *  @param item Het item dat de speler wil oppakken.
     *  
     *  @return True if the player can pickup the item, otherwise false.
     */
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
    
    /**
     *  Items kunnen gedropt worden. Check of de speler het
     *  item in de inventory heeft. Voeg item toe aan de room,
     *  verwijder item uit inventory. Pas het gewicht van de player
     *  aan.
     *  
     *  @param itemName De naam van het item.
     *  @return True if player can drop item, otherwise false
     */
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
    
    /**
     *  Retouneer alle items in de inventory van de speler.
     *  In de vorm: 
     *     Your current items are: apple banana
     *  
     *  @return Een string met alle items.
     */
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
    
    /**
     *  Zoek een specifiek item op in de inventory.
     *  
     *  @param itemName De naam van het gezochte item.
     *  @return Een item wanneer deze in de inventory zit, anders null
     */
    public Item getInventoryByName(String itemName){
        return inventory.get(itemName);
    }
    
    /**
     *  Check of een item in de inventory van de speler zit. 
     *  
     *  @param itemName De naam van het item.
     *  @return True wanneer het item in de inventory zit, anders false.
     */
    public boolean isInInventory(String itemName){
        if(inventory.containsKey(itemName)){
            return true;
        }
        return false;
    }
    
    /**
     *  Retouneer het huidige gewicht van de speler.
     *  
     *  @return Het huidige gewicht van de speler.
     */
    public int getCurrentWeight(){
        return currentWeight;
    }
    
    /**
     *  Check of een speler een item kan oppakken.
     *  
     *  @param item Het item dat de speler wil oppakken.
     *  @return True wanneer de speler het item kan oppakken, anders false.
     */
    public boolean canPickUpItem(Item item) {
        if ((currentWeight + item.getItemWeight()) > maxWeight) {
            return false;
        }
        return true;
    }
    
    /**
     *  Retouneer een beschrijving van het item. In de vorm:
     *      Examining: apple. A juicy apple.
     *  
     *  @param itemName De naam van het item.
     *  @return Een omscrhijving van een item.
     */
    public String getExamineString(String itemName){
        String returnString = "Examining: " + itemName + ".";
        
        if(isInInventory(itemName)){
            
            Item itemToExamine = inventory.get(itemName);
            
            returnString += " " + itemToExamine.getDescription() + ".";
            
            return returnString;
        }
        
        return null;
    }
    
    /**
     *  Set een beamer locatie zodat hier later naar terug gekeerd kan
     *  worden.
     *  
     *  @param beamerLocation De locatie waar de speler naar terug kan.
     */
    public void setBeamerLocation(Room beamerLocation){
        this.beamerLocation = beamerLocation;
    }
    
    /**
     * Retouneer de beamer locatie van de speler.
     * 
     * @return De beamer locatie van de speler, wanneer deze niet is geset return null.
     */
    public Room getBeamerLocation(){
        
        if(beamerLocation != null){
            return beamerLocation;
        }
        return null;
    }
    
    /**
     *  Sommige items kunnen niet meerdere keren worden gebruikt.
     *  
     *  @param item Een item dat niet meerdere keren gebruikt kan worden.
     */
    public void setUsedItem(Item item){
        usedItems.add(item);
    }
    
    /**
     *  Retouneer of een item gebruikt is of niet.
     *  
     *  @return True wanneer een item gebruikt is, anders false.
     */
    public boolean itemIsUsed(Item item){
        if(usedItems.contains(item)){
            return true;
        }  
        return false;
    }
}
