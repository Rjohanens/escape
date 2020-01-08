

import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{   
    private Parser parser;
    private Player player;
    
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createGame();
        parser = new Parser();
    }

    /**
     * Create all the rooms, link their exits together and place items in the rooms.
     */
    private void createGame()
    {
        // all rooms
        Room basement, livingroom, kitchen, bedroom, bathroom, garage, corridor;
        
        //all items
        
        Item apple, banana, testItem;
        
        //create player
        
        player = new Player(0, 10);
        
        // create the rooms
        basement = new Room("in the basement");
        livingroom = new Room("in the living room");
        kitchen = new Room("in the kitchen");
        garage = new Room("in the garage");
        bathroom = new Room("in the bathroom");
        bedroom = new Room("in the bedroom");
        corridor = new Room("in the corridor");
        
        //create items
        apple = new Item("apple", 1);
        banana = new Item("banana", 2);
        testItem = new Item("test", 8);
        
        // initialise room exits
        basement.setExits("up", garage);
        
        garage.setExits("down", basement);
        garage.setExits("north", corridor);
        
        corridor.setExits("south", garage);
        corridor.setExits("north", livingroom);
        
        livingroom.setExits("south", corridor);
        livingroom.setExits("north", kitchen);
        livingroom.setExits("east", bedroom);
        
        kitchen.setExits("south", livingroom);
        
        bedroom.setExits("west", livingroom);
        bedroom.setExits("east", bathroom);
        
        bathroom.setExits("west", bedroom);
        
        //initialize items
        garage.addItem("apple", apple);
        garage.addItem("test", testItem);
        livingroom.addItem("banana", banana);
        
        player.setPreviousRoom(basement); //begin room
        player.setCurrentRoom(basement); //start game in the basement
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    /**
     * Print out information about the current room
     */
    
    private void printLocationInfo(){
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")){
            look();
        }
        else if (commandWord.equals("back")){
            back();
        }
        else if (commandWord.equals("take")){
            take(command);
        }
        else if (commandWord.equals("drop")){
            drop(command);
        }
        else if(commandWord.equals("show")){
            show();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.print(parser.showCommands());
        System.out.println();
    }
    
    private void take(Command command){
       if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        Item item = player.getCurrentRoom().getItem(itemName);
        
        //try picking up item
        if(item == null){
            System.out.println("Can't find that item");
        }
        if(player.pickUpItem(itemName, item)){
            
            //if true
            player.getCurrentRoom().removeItem(itemName);
            System.out.println("Item taken");
        }
        
        else{
            System.out.println("Can't pick up item");
        }
    }
    
    private void drop(Command command){
        if(!command.hasSecondWord()) {
            //if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        Item item = player.getInventoryByName(itemName);
        
        if(item == null){
            System.out.println("Can't find that item");
        }
        
        if(player.dropItem(itemName)){
            player.getCurrentRoom().addItem(itemName, item);
            System.out.println("Item dropped");
        }
        
        else{
            System.out.println("Can't drop item");
        }
    }
    
    /**
     * Print out information about the room.
     * Simulate looking around in the room.
     */
    private void look(){
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    private void show(){
        System.out.print(player.getInventory());
        System.out.println();
        System.out.println("Your current weight is: " + player.getCurrentWeight());
    }
    
    private void back(){
        Room previousRoom = player.getPreviousRoom();
        
        if(previousRoom != null){
            player.setCurrentRoom(previousRoom);
            printLocationInfo();
        }else{
            System.out.println("There is no previous room");
        }
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setPreviousRoom(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo(); 
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
