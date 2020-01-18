



/**
 * class item - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Item
{
    private String description;
    private int weight;
    private String itemName;
    
    public Item(String itemName, String description, int weight){
        this.description = description;
        this.weight = weight;
        this.itemName = itemName;
    }
    
    public int getItemWeight(){
        return weight;
    }
    
    public String getDescription(){
        return description;
    }
}
