/**
 * In deze klasse staan alle command words die je in de game kunt gebruiken
 *
 * @author  Rick Johannes, Teijmen van der Ploeg, Lars Bosker
 * @version 22-01-2020
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "start", "about", "quit", "help", "back", "take", "drop", "show", "beam", "examine", "use", "enter"
    };

    /**
     * Constructor die de command words initialiseert
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Controleert wanneer een gegeven String is een geldig command word.
     * @return true als de gegeven String een geldig command word is,
     * false als het geen geldig command word is.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    /**
     * Lijst die retourneert welke geldige command words je kan gebruiken
     */
    public String getCommandList(){
        
        String returnString = "Your command words are:";
        for(String command : validCommands){
            returnString += " " + command;
        }
        
        return returnString;
    }
}
