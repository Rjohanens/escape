/**
 * Deze klasse bevat de informatie van de items
 *
 * @author Rick Johannes, Teijmen van der Ploeg, Lars Bosker
 * @version 22-01-2020
 */
public class Item
{
    private String description;
    private int weight;
    private String itemName;
    
    /**
     * Methode waarin de informatie van de items wordt opgeslagen
     * 
     * @param String    naam van item
     * @param String    beschrijving van item
     * @param String    gewicht van item
     */
    public Item(String itemName, String description, int weight){
        this.description = description;
        this.weight = weight;
        this.itemName = itemName;
    }
    
    /**
     * Methode die het gewicht van een item retourneert
     * 
     * @return  gewicht van item
     */
    public int getItemWeight(){
        return weight;
    }
    
    /**
     * Methode die de beschrijving van een item retourneert
     * 
     * @return beschrijving van item
     */
    public String getDescription(){
        return description;
    }
}
