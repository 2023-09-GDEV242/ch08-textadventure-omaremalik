/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  Omar Malik
 * @version 2023.10.18
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LOOK("look"), EAT("eat"), 
    BACK("back"), CLOSE_TRAPDOOR("close"),OPEN_TRAPDOOR("open");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
