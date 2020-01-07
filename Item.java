



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
    
    public Item(String description, int weight){
        this.description = description;
        this.weight = weight;
    }
    
    public int getItemWeight(){
        return weight;
    }
}
