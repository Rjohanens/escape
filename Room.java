
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Escape" application. 
 * "Escape" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west, up and down.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Rick Johannes, Lars Bosker, Teijemen van der Ploeg
 * @version 2020.01.22
 */
public class Room 
{
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;
    
    private String lockedDirection;
    private String itemToUnlock;
    private String objectsInRoom;
    private String description;
    private String combinationLockedExit;
    private String combinationToUnlock;
    private String unlockedDirection;
 
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }
    
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param direction The direction of the exit
     * @param neighbor The room where the exit leads to
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * retourneer de ruimte die we betreden als we van deze
     * ruimte in de richting "direction" gaan. Retourneer null
     * als er in die richting geen ruimte is.
     * 
     * @return De exit met bijbehorende direction, null wanneer er geen exit is
     */
    public Room getExit(String direction){
        return exits.get(direction);
    }
    
    /**
     * Retourneer een string met daarin de uitgangen van de ruimte,
     * bijvoorbeeld "Exits: north west".
     * @return Een omschrijving van de aanwezige uitgangen in de ruimte.
     */
    public String getExitString(){
        String returnString = "You can walk in these directions: ";
        
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        
        
        return returnString;
    }

    /**
     * Retouneer de omschrijving van een kamer.
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Retourneer een lange omschrijving van deze ruimte, van de vorm:
     *      You are in the livingroom.
     *      Exits: north east south
     * @return Een omschrijving van de ruimte en haar uitgangen
     */
    public String getLongDescription(){
        return description + ".\n" + getItemString() + ".\n" + getExitString();
    }
    
    /**
     * Retourneer alle items in een ruimte, van de vorm:
     *     Items in this room: apple
     * Of wanneer er geen items zijn:
     *     Items in this room: no items in this room
     * @return Een lijst van alle items in de ruimte
     */
    public String getItemString(){
        String returnString = "Items in this room:";
        
        if(items.isEmpty()){
            returnString += " " + "There are no items in this room";
        }else{
            Set<String> item = items.keySet();
            for(String object : item){
                returnString += " " + object;
            }
        }
        
        return returnString;
    }
    
    /**
     * Voeg een item toe aan een ruimte, een item heeft
     * een naam en een Item object
     * 
     * @param itemName De naam van het item
     * @param item Een Item die hoort bij de naam
     */
    public void addItem(String itemName, Item item){
        items.put(itemName, item);
    }
    
    /**
     * Retouneer een item op basis van de naam (key).
     * Wanneer er niks gevonden wordt, return null.
     * 
     * @param itemKey De naam van het item
     * @return Het Item dat hoort bij de naam 
     */
    public Item getItem(String itemKey){
        return items.get(itemKey);
    }
    
    /**
     * Verwijder een item uit de Room.
     * 
     * @param itemName De naam van het Item
     */
    public void removeItem(String itemName){
        items.remove(itemName);
    }
    
    /**
     * Lock een exit op basis van de direction.
     * 
     * @param direction De uitgang die gelocked moet worden
     */
    public void setLockedExit(String direction){
        lockedDirection = direction;
    }
    
    /**
     * Retouneer de gelockte exit.
     * 
     * @return De gelockte exit.
     */
    public String getLockedDirection(){
        return lockedDirection;
    }
    
    /**
     * Geef een item op waarmee de exit unlocked kan worden.
     * 
     * @param itemName De naam van het item.
     */
    public void setItemToUnlock(String itemName){
        itemToUnlock = itemName;
    }
    
    /**
     * Retouneer het item waarmee de exit unlocked kan worden
     * 
     * @return Het item waarmee de exit unlocked kan worden
     */
    public String getItemToUnlock(){
        return itemToUnlock;
    }
    
    /**
     * Wanneer een exit unlocked is hoeft deze niet nog een keer
     * unlocked te worden.
     * 
     * @param direction De richting die unlocked is.
     */
    public void setUnlockedDoor(String direction){
       unlockedDirection = direction;
    }
    
    /**
     * check of een exit locked of unlocked is.
     * Retouneer false wanneer de exit unlocked is.
     * Retouneer true wanneer de exit locked is.
     * 
     * @return True wanneer de exit locked is, anders false.
     */
       
    public boolean doorIsLocked(){
        if(unlockedDirection != null){
            return false;
        }
        
        return true;
    }
    
    /**
     * Set een code om een met combinatieslot locked deur te openen.
     * 
     * @param combination De combinatie om de deur te openen
     */
    public void setCombinationLock(String combination){
        combinationToUnlock = combination;
    }
    
    /**
     * Retouneer de combinatie.
     * 
     * @return De combinatie voor de locked exit.
     */
    public String getCombinationLock(){
        return combinationToUnlock;
    }
    
    /**
     * Locked een exit met een combinatielock.
     * 
     * @param direction De richting de gelocked wordt.
     */
    public void setCombinationLockedExit(String direction){
        combinationLockedExit = direction;
    }
    
    /**
     * Retouneer de met combinatie gelockte exits
     * 
     * @return De met combinatie gelockte exits.
     */
    public String getCombinationLockedExit(){
        return combinationLockedExit;
    }
}
