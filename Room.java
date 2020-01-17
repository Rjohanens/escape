
import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;
    
    private String lockedDirection;
    private String itemToUnlock;
    
    private String objectsInRoom;
    private boolean doorIsUnlocked;
 
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
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * retourneer de ruimte die we betreden als we van deze
     * ruimte in de richting "direction" gaan. Retourneer null
     * als er in die richting geen ruimte is.
     */
    public Room getExit(String direction){
        
        return exits.get(direction);
    }
    
    /**
     * Retourneer een string met daarin de uitgangen van de ruimte,
     * bijvoorbeel "Exits: north west".
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
        String returnString = "In this room you can find these things that might be useful for you:";
        
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
     */
    public void addItem(String itemName, Item item){
        items.put(itemName, item);
    }
    
    public Item getItem(String itemKey){
        return items.get(itemKey);
    }
    
    public void removeItem(String itemName){
        items.remove(itemName);
    }
    
    public void setLockedExit(String direction){
        lockedDirection = direction;
    }
    
    public String getLockedDirection(){
        return lockedDirection;
    }
    
    public void setItemToUnlock(String itemName){
        itemToUnlock = itemName;
    }
    
    public String getItemToUnlock(){
        return itemToUnlock;
    }
    
    public void setUnlockedDoor(String direction){
       doorIsUnlocked = true;
    }
    
    public boolean getUnlockedDoor(){
        if(doorIsUnlocked == true){
            return true;
        }
        
        return false;
    }
}
