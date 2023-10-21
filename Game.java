import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
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
 * @author  Omar Malik
 * @version 2023.10.18
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomHistory; // Stack to track visited rooms
    private Room previousRoom; // Declare previousRoom at the class level
    private Player player;
    
    private Timer timer;
    private int timeLimit; // Time limit in seconds
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<>();
        player = new Player(currentRoom);
        timeLimit = 600; // Set a 10-minute time limit
        timer = new Timer();
        timer.schedule(new GameTimer(), timeLimit * 1000); // Schedule the timer
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, dorm, mainBuilding, classRoom, 
        computerLab, bathRoom, library, garden, gym, cafeteria, gamelounge;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        dorm = new Room("in a dormitory room");
        mainBuilding = new Room("in the main building");
        classRoom = new Room("in a classroom");
        computerLab = new Room("in computer lab");
        bathRoom = new Room("in a bathroom");
        library = new Room("in the university library");
        garden = new Room("in a beautiful garden");
        gym = new Room("in the university gym");
        cafeteria = new Room("in the cafeteria");
        gamelounge = new Room("in the gaming lounge");
        
        Item map = new Item("Map of the campus", "A detailed map of the university campus.", 0.5);
        outside.addItem(map);
        Item ring = new Item("Enchanted Ring", "A mysterious ring with a glow to it", 0.5);
        outside.addItem(ring);
        
        // Creates a "Wooden Sword" item and associate it with the "theater" room
        Item woodSword = new Item("Wood Sword", "A simple wooden sword", 3.0);
        theater.addItem(woodSword);
        Item fakeGun = new Item("Fake Gun","A toy gun that looks real.", 2.0);
        theater.addItem(fakeGun);
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", mainBuilding);
        theater.setExit("west", outside);
        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);
        dorm.setExit("north", outside);
        mainBuilding.setExit("south", outside);
        mainBuilding.setExit("east", classRoom);
        mainBuilding.setExit("west", library);
        classRoom.setExit("west", mainBuilding);
        library.setExit("east", mainBuilding);
        garden.setExit("south", outside);
        gym.setExit("east", outside);
        cafeteria.setExit("north", outside);
        gamelounge.setExit("north", mainBuilding);
        
        currentRoom = outside;  // start game outside
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
        // Display the initial room description
        System.out.println(currentRoom.getLongDescription()); 
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
            
            case LOOK: // Handle the "look" command here
                if (command.hasSecondWord()) {
                System.out.println("Look what?");
            } else {
                System.out.println(currentRoom.getLongDescription());
            }
            break;
            
            case EAT:
                command.executeEat();
                break;
            
            case BACK:
                goBack();
                break;

            
            case QUIT:
                wantToQuit = quit(command);
                break;
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
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        // Try to leave the current room and store it as the previous room.
        previousRoom = currentRoom;
        
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomHistory.push(currentRoom); // Push the current room onto the stack
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    private class GameTimer extends TimerTask {
        @Override
        public void run() {
            // Time's up! Implement your game over logic here.
            System.out.println("Time's up! Game over.");
            System.exit(0); // Terminate the game
        }
    }
    
    
    /**
     * Handles the opening the trap door in the current room.
     */
    private void openTrapDoor()
    {
        if (currentRoom.hasTrapDoor())
        {
            currentRoom.openTrapDoor();
            System.out.println("You open the trap door.");
        }
        else
        {
            System.out.println("There is no trap door here.");
        }
    }
    
    /**
     * Handles the closing of the trap door in the current room.
     */
    private void closeTrapDoor()
    {
        if (currentRoom.hasTrapDoor())
        {
            currentRoom.closeTrapDoor();
            System.out.println("You close the trap door.");
        }
        else
        {
            System.out.println("There is no trap door here.");
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
    
    private void goBack() {
        if (roomHistory.isEmpty()) {
            System.out.println("You can't go back any further.");
        } else {
            Room tempRoom = currentRoom;
            currentRoom = roomHistory.pop(); // Pop the previous room from the stack
            roomHistory.push(tempRoom); // Push the current room back onto the stack
            System.out.println(currentRoom.getLongDescription());
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
