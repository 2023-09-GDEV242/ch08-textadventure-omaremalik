import java.util.List;
import java.util.ArrayList;
/**
 * The Player class represents the player in the "World of Zuul" game.
 * It holds information about the current room and manages the player's inventory.
 *
 * The player can pick up and drop items within the game.
 * 
 * @author  Omar Malik
 * @version 2023.10.18
 */
public class Player {
    private Room currentRoom;
    private List<Item> inventory;

    /**
     * Constructs a new Player instance with the starting room.
     *
     * @param startingRoom The room where the player begins the game.
     */
    public Player(Room startingRoom) {
        currentRoom = startingRoom;
        inventory = new ArrayList<>();
    }

    /**
     * Retrieves the current room where the player is located.
     *
     * @return The current room.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the current room where the player is located.
     *
     * @param room The room to set as the current room.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    /**
     * Retrieves the player's inventory of items.
     *
     * @return The list of items in the player's inventory.
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Picks up an item and adds it to the player's inventory.
     *
     * @param item The item to pick up.
     * @return true if the item was successfully picked up, false otherwise.
     */
    public boolean pickUpItem(Item item) {
        if (currentRoom.hasItem(item)) {
            inventory.add(item);
            currentRoom.removeItem(item);
            return true;
        }
        return false;
    }

    /**
     * Drops an item from the player's inventory into the current room.
     *
     * @param item The item to drop.
     * @return true if the item was successfully dropped, false otherwise.
     */
    public boolean dropItem(Item item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
            currentRoom.addItem(item);
            return true;
        }
        return false;
    }
}
