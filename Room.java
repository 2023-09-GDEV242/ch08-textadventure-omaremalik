import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Omar Malik
 * @version 2023.10.18
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private List<Item> items; // Use a List to store multiple items
    private TrapDoor trapDoor; // A one-way trap door
    
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
        items = new ArrayList<>(); // Initialize the collection
        trapDoor = new TrapDoor();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Checks if the room has a trap door and if it's open.
     *
     * @return true if the room has an open trap door, false otherwise.
     */
    public boolean hasTrapDoor()
    {
        return trapDoor.isOpen();
    }

    /**
     * Opens the trap door in the room.
     */
    public void openTrapDoor()
    {
        trapDoor.open();
    }

    /**
     * Closes the trap door in the room.
     */
    public void closeTrapDoor()
    {
        trapDoor.close();
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    
    /**
     * Checks if the room contains an item.
     *
     * @return true if the room contains items, false otherwise.
     */
    public boolean hasItem() {
        return !items.isEmpty();
    }
    
    /**
     * Checks if the room contains a specific item.
     *
     * @param item The item to check for.
     * @return true if the item is in the room, false otherwise.
     */
    public boolean hasItem(Item item) {
        return items.contains(item);
    }
    
    /**
     * Removes an item from the room.
     *
     * @param item The item to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription() {
        String itemDescription = "Items in the room:";
        for (Item item : items) {
            itemDescription += "\n - " + item.getDescription();
        }
        return "You are " + description + ".\n" + getExitString() + "\n" + itemDescription;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
        private String getExitString()
        {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
        }
    
    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

