
/**
 * Represents a one-way trap door that can be opened and closed
 *
 * @author  Omar Malik
 * @version 2023.10.18
 */
public class TrapDoor
{
    private boolean isOpen;
    
    /**
     * Constructs a new trap door. It is initially closed.
     */
    public TrapDoor()
    {
        isOpen = false;
    }
    
    /**
     * Checks if the trap door is open.
     *
     * @return true if the trap door is open, false if it's closed.
     */
    public boolean isOpen()
    {
        return isOpen;
    }
    
    /**
     * Opens the trap door.
     */
    public void open()
    {
        isOpen = true;
    }
    
    /**
     * Closes the trap door.
     */
    public void close()
    {
        isOpen = false;
    }
}
