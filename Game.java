

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

public class Game extends SoundEffects 
{   
    private Parser parser;
    private Player player;
    private CountdownTimer timer;
    private SoundEffects musicplayer;
    public boolean gameStarted = false;
    private boolean codeIsEnterdCorrect = false;
    
     // all rooms
    private Room basement, livingroom, kitchen, garage, corridor, outside, secretroom, bedroom, office;
    
    //all items
    private Item crowbar, clock, keyFrontDoor, lever, paper, note_1, note_2, note_3;
    
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        //create timer
        timer = new CountdownTimer();
    }

    /**
     * Create all the rooms, link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        basement = new Room("Brrr, it's cold in the basement!");
        livingroom = new Room("Wow! This room is big! It looks like a living room!");
        kitchen = new Room("Oh this is the kitchen!");
        garage = new Room("What a big cars in this garage!");
        bedroom = new Room("Ok, this is the bedroom.");
        corridor = new Room("I'm walking in the corridor, I think");
        office = new Room("This looks like an office");
        outside = new Room("You are outside!");

        // initialise room exits
        basement.setExits("up", garage);
        
        garage.setExits("down", basement);
        garage.setExits("north", corridor);
        
        corridor.setExits("south", garage);
        corridor.setExits("north", livingroom);
        corridor.setExits("east", outside);
        
        livingroom.setExits("south", corridor);
        livingroom.setExits("north", kitchen);
        livingroom.setExits("east", bedroom);
        
        kitchen.setExits("south", livingroom);
        
        bedroom.setExits("west", livingroom);
        
        office.setExits("south", secretroom);

        //set locked exits
        basement.setLockedExit("up");
        corridor.setLockedExit("east");
    }
    
    /**
     * Create all items and place items in the rooms.
     * Set items to unlock doors.
     */
    private void createItems(){
        //create items
        crowbar = new Item("crowbar", "With this item you can break open the basementdoor", 3);
        keyFrontDoor = new Item("key", "This looks like a key to the front door", 2);
        clock = new Item("clock", "You found the magic clock! Type 'use clock' to get 2 extra minutes", 1);
        keyFrontDoor = new Item("key", "This looks the key to the front door!", 1);
        lever = new Item("lever", "Hmm, maybe can I use this lever to open a secret door", 2);
        paper = new Item("paper", "Note to myself: I have hidden 3 notes in the house, just in case I ever forget the secret code", 1 );
        note_1 = new Item("note", "_a_9__", 1);
        note_2 = new Item("note", "___y4", 1);
        note_3 = new Item("note", "2_3__", 1);
        
        //add items to rooms
        basement.addItem("crowbar", crowbar);
        
        bedroom.addItem("clock", clock);
        bedroom.addItem("note", note_1);
        
        kitchen.addItem("sticky-note", note_2);
        kitchen.addItem("lever", lever);
        
        livingroom.addItem("paper", paper);
        
        office.addItem("key", keyFrontDoor);
        
        //set items to unlock exit
        basement.setItemToUnlock("crowbar");
        corridor.setItemToUnlock("key");
    }
    
    /**
     * Create the player.
     */
    private void createPlayer(){
         //create player
        player = new Player(0, 10);
    }
    
    /**
     *  Main play routine.  Loops until end of play. 
     *  Check if player has won.
     */
    public void play() 
    {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        
        while (! finished) {
            if(checkWin()){     //check win state
                timer.stopTimer();
                System.out.println("###################################");
                System.out.println("Well done, you have escaped!");
                System.out.println("You time was: " + (14 - timer.getMinutes()) + " minutes and " + (60%timer.getSeconds()) + " seconds."); //bereken de verstreken tijd
                finished = true;
                gameStarted = false;
            }
            else{
                Command command = parser.getCommand();
                finished = processCommand(command);
            }
        }
        timer.stopTimer();
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    private void about()
    {
        System.out.println();
        System.out.println("Hi there! I heard you want to know more about this game!");
        System.out.println("You are kidnapped! Your goal is to escape within (x) minutes.");
        System.out.println("You can use several items to unlock doors and for other useabilities.");
        System.out.println("Created by: Rick, Lars and Teijmen.");
        System.out.println();
    }
    
    /**
     * Print out information about the current room
     */
    
    private void printLocationInfo(){

        if(checkWin()){     //wanneer de speler gewonnen heeft, hoeft er geen omschrijving gegeven te worden
            return;
        }
        else{
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("###################################");
        System.out.println("Welcome to Escape!");
        System.out.println();
        System.out.println("Type 'help' if you need help about the controls.");
        System.out.println("Type 'about' if you want more information.");
        System.out.println("Type 'start' if you are ready to play the game!");
        System.out.println("###################################");
        System.out.println();
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
        else if(commandWord.equals("start")){
            start();
        }
        else if(commandWord.equals("about")){
            about();
        }
        else if ((commandWord.equals("go")) && (gameStarted == true)) {
            goRoom(command);
        }
        else if ((commandWord.equals("back")) && (gameStarted == true)) {
            back();
        }
        else if ((commandWord.equals("take")) && (gameStarted == true)) {
            take(command);
        }
        else if ((commandWord.equals("drop")) && (gameStarted == true)) {
            drop(command);
        }
        else if ((commandWord.equals("show")) && (gameStarted == true)) {
            show(command);
        }
        else if ((commandWord.equals("examine")) && (gameStarted == true)) {
            examineItem(command);
        }
        else if ((commandWord.equals("beam")) && (gameStarted == true)) {
            beam(command);
        }
        else if ((commandWord.equals("use")) && (gameStarted == true)) {
            useItem(command);
        }
        else if ((commandWord.equals("enter")) && (gameStarted == true)) {
            enter(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        
        else if(gameStarted == false){
            System.out.println("Please start the game first.");
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
        System.out.println();
        System.out.print(parser.showCommands());
        System.out.println();
        System.out.println("Type 'go (direction)' to move.");
        System.out.println("Type 'back' to go to back to the previous room.");
        System.out.println("Type 'take (item name)' to take an item.");
        System.out.println("Type 'drop (item name)' to drop an item.");
        System.out.println("Type 'show' to show your current inventory + current weight.");
        System.out.println("Type 'beam (go/set)' to set a beam location or go to a beam location.");
        System.out.println("Type 'quit' to stop the game.");
        System.out.println();
    }
    
    private void take(Command command){
       if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord(); //get second word
        
        Item item = player.getCurrentRoom().getItem(itemName); //get Item corresponding to the second word
        
        //try picking up item
        if(item == null){
            System.out.println("Can't find that item.");
            return;
        }
        if(player.pickUpItem(itemName, item)){
            
            //if true
            player.getCurrentRoom().removeItem(itemName);
            System.out.println("Item taken.");
            startPlaying("music/MGS_ALERT.mp3");
        }
        
        else{
            System.out.println("Can't pick up the item you want.");
        }
    }
    
    /**
     * 'drop' was entered. A player can drop an item.
     *  Check what item the player wants to drop.
     */
    private void drop(Command command){
        if(!command.hasSecondWord()) {
            //if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord(); //get second word
        
        Item item = player.getInventoryByName(itemName); //get Item corresponding to the second word
        
        if(item == null){   //if we can't find the item
            System.out.println("Can't find that item.");
            return;
        }
        
        if(player.dropItem(itemName)){  //try to drop the item
            player.getCurrentRoom().addItem(itemName, item);
            System.out.println("Item dropped.");
            startPlaying("music/SLIP.mp3");
        }
        
        else{
            System.out.println("Can't drop item.");
        }
    }
    
    /**
     * 'examine' was entered. A player can examine an item.
     *  In this case the desciption of an item will be shown.
     */
    private void examineItem(Command command){
        if(!command.hasSecondWord()){
            //if there is no second word, we don't know what to examine
           System.out.println("Examine what? ");
           return;
        }
        
        String itemName = command.getSecondWord(); //get second word
        
        if(player.getExamineString(itemName) != null){  //get item description
            System.out.println(player.getExamineString(itemName));
        }
        else{
            System.out.println("You can only examine items that are in your inventory!");
        }
    }
    
    /**
     * 'show' was entered. Check if player entered
     * 'time' or 'inventory'. 
     */
    private void show(Command command){
        if(!command.hasSecondWord()){
            //if there is no second word, we don't know what to show
           System.out.println("Show inventory of time?");
           return;
        }
        
        String showCommand = command.getSecondWord();   //get the second word
        
        if(showCommand.equals("inventory")){    //if second word is 'inventory'
            System.out.print(player.getInventory());    //get players inventory
            System.out.println();
            System.out.println("Your current weight is: " + player.getCurrentWeight()); //get players current weight
        }
        else if (showCommand.equals("time")){   //if second word is 'time'
            //laat de speler zien hoeveel tijd hij nog heeft
            System.out.println("You have " + timer.getMinutes() + " minutes and " + timer.getSeconds() + " seconds left.");
        }
        
        else{
            System.out.println("Sorry, I don't understand.");
        }
       
    }
    
    /** 
     * 'back' was entered. Try to go to the previous room.
     * If there is no previous room print erro.
     * Keep history of all the player movements.
     */
    private void back(){
        Room previousRoom = player.getPreviousRoom(); //try to get previous room
        
        if(previousRoom != null){   //if there is a previous room
            player.setCurrentRoom(previousRoom);
            printLocationInfo();
            startPlaying("music/rewind_time.mp3");
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
        Room currentRoom = player.getCurrentRoom();
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
            return;
        }
        
        String lockedDirection = currentRoom.getLockedDirection();
        String combinationLockedDirection = currentRoom.getCombinationLockedExit();
        
        // Wanneer een speler een kant op wil gaan (direction),
        // moet worden gecheckt of deze direction locked is,
        // of de speler het item heeft om deze deur te unlocken en
        // of de deur niet al eerder geopend is (wanneer de speler het
        // item gedropt heeft).
        if( direction.equals(lockedDirection) && (player.isInInventory(currentRoom.getItemToUnlock()) == false) && (currentRoom.doorIsLocked() == true) ){
            System.out.println("This door is locked! Try to find an item to unlock this exit.");
            startPlaying("music/locked_door.mp3");
            return;
        }
        
        //check dit zelfde, maar dan voor exits met een combinatie slot
        else if( (direction.equals(combinationLockedDirection)) && (currentRoom.doorIsLocked() == true) && (codeIsEnterdCorrect == false) ){
            System.out.println("This door is locked with an combination lock! Try to find the combination.");
            System.out.println("Type 'enter (code)' to enter the combination");
        }
        
        else if( (direction.equals(lockedDirection)) &&  (player.isInInventory(currentRoom.getItemToUnlock()) == true) && (currentRoom.doorIsLocked() == true)){
            currentRoom.setUnlockedDoor(direction);
            player.setPreviousRoom(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo(); 
            //wanneer iemand een kamer binnengaat speelt een geluidje af.
            startPlaying("music/minecraft_door.mp3");
        }
        else{   //enter room
            player.setPreviousRoom(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo(); 
            //wanneer iemand een kamer binnengaat speelt een geluidje af.
            startPlaying("music/minecraft_door.mp3");
        }
    }
    
    /**
     *  Beam function. Check for 'beam set' or 'beam go' and
     *  check in case of 'beam go' if beam location is already set,
     *  else print error.
     */
    private void beam(Command command){
        
        if(!command.hasSecondWord()){
            // if there is no second word, we don't know if the player wants to set or go to the beamlocation...
            System.out.println("Set or go?");
            return;
        }
        
        String commandWord = command.getSecondWord();   //get the second word
        
        Room currentRoom = player.getCurrentRoom(); //get the currentroom
        
        if( (player.getBeamerLocation() == null) && (!commandWord.equals("set"))){  //check if 'set' wasn't entered and there is no beamlocation yet
            System.out.println("No beamer location set.");
            return;
        }
        
        if(commandWord.equals("set")){  //set a beamlocation
            player.setBeamerLocation(currentRoom);
            System.out.println("Beamer location set succesfully.");
        }
        
        else if (commandWord.equals("go")){ //go to the set beamlocation
            player.setPreviousRoom(currentRoom);
            player.setCurrentRoom(player.getBeamerLocation());
            System.out.println("Beam succesful.");
            printLocationInfo();
            startPlaying("music/beam_sound.mp3");
        }
    }
    
    /**
     * "use (item)" was entered. Some items can be 'used'.
     * 
     */
    public void useItem(Command command){
        if(!command.hasSecondWord()){
            // if there is no second word, we don't know what to use...
            System.out.println("Use what?");
            return;
        }
        
        String itemToUse = command.getSecondWord(); //get second word so we know what item to use
        
        Item item = player.getCurrentRoom().getItem(itemToUse); //get the Item corresponding the second word
        
        if(!player.isInInventory(itemToUse)){
            System.out.println("You can only use items that are in your inventory!");
            return;
        }
        
        
        if(!itemToUse.equals("clock") && (!itemToUse.equals("lever")) ){
            System.out.println("Sorry, can't use that item.");
            return;
        }
        
        //check if item is a clock, and player has the item and item is not used before
        else if( (itemToUse.equals("clock")) && (player.isInInventory(itemToUse)) && (player.itemIsUsed(item) == false)){ 
            player.setUsedItem(item);   //add item to used items list
            timer.secondPassed += 120;  //add extra time
            System.out.println("Clock used! You got 2 extra minutes.");
        }
        
        else if( (itemToUse.equals("lever")) && (player.isInInventory(itemToUse)) && (player.itemIsUsed(item) == false)){
            player.setUsedItem(item); //add item to used items list
            generateSecretRoom(); //generate new room
            System.out.println("Lever used! A secret door opened.");
            printLocationInfo();
        }
        else if(player.itemIsUsed(item) == true){   //if item is already used
            System.out.println("This item is already used.");
        }
        else{                                       
            System.out.println("Can't find that item");
        }
    }
    
    /**
     * 'enter (code)' was entered. A player can enter a code 
     *  to try and unlock combination locked doors. If the 
     *  combination is correct, unlock door so the player
     *  can enter the room. Else print error. This mehtod also
     *  checks if a player is in the room of the combination locked 
     *  exit.
     */
    private void enter(Command command){
        if(!command.hasSecondWord()){
            // if there is no second word, we don't the code...
            System.out.println("Please enter a code");
            return;
        }
        
        String code = command.getSecondWord();  //get the entered code
        Room currentRoom = player.getCurrentRoom(); //get the currentroom
        
        if(currentRoom.getDescription() != "Hmm I'm now in the secret room.."){
            System.out.println("You can only use 'enter' in the room where a combination locked exit is.");
            return;
        }
        
        if(code.equals(currentRoom.getCombinationLock())){  //check if entered code equals the combination of that room
            codeIsEnterdCorrect = true;
            System.out.println("Code accepted. Opening door...");
        }
        
        else{   //if wrong code is entered
            System.out.println("Wrong combination!");
        }
    }
    
    private void generateSecretRoom(){
        
        secretroom = new Room("Hmm I'm now in the secret room.."); //make new room
        
        //set exits for new room
        secretroom.setExits("west", bedroom);
        secretroom.setExits("north", office);
        
        //set exits to go to new room
        bedroom.setExits("secretroom", secretroom);
        
        //set locked combination exits + code to unlock
        secretroom.setCombinationLockedExit("north");
        secretroom.setCombinationLock("2a39y4");
    }
    
    /**
     *   "Start" was entered. Create the game, rooms, player
     *   and items. Print location info about current room.
     *   Set boolean gameStarted true, so we can only run
     *   one game at the time.
     *   
     *   If the game is already started, tell player that the
     *   game is already started.
     */
    private void start(){
        
        if(gameStarted == false){   //if game is not started yet
           
            //create the game
           createRooms();
           createItems();
           createPlayer();
           
           player.setPreviousRoom(basement); //begin room
           player.setCurrentRoom(basement); //start game in the basement
        
           timer.startTimer();
           printLocationInfo(); 
           gameStarted = true;
        }
        
        else{
            System.out.println("Game already started.");
        }
        
    }
    
    
    /**
     *  check of de speler gewonnen heeft. Wanneer de speler
     *  'outside' is, heeft de speler gewonnen. Retourneer true
     *  wanneer de speler outside is. 
     *  
     *  @return True wanneer de speler gewonnen heeft, anders false.
     */
    private boolean checkWin(){
        
        if(gameStarted){
            if(player.getCurrentRoom().getDescription() == "You are outside!"){
                return true;
            }
        }
        
        return false;
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
            startPlaying("music/windows_shutdown.mp3");
            return true;  // signal that we want to quit
        }
    }
}
