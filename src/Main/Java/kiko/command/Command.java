package kiko.command;
/**
 * Enumeration of all valid commands supported by the application.
 * Each command has a corresponding command word used for parsing user input.
 */
public enum Command {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    BYE("bye"),
    UNKNOWN("unknown");
    
    private final String commandWord;
    
    /**
     * Constructs a Command with the specified command word.
     *
     * @param commandWord The word that identifies this command.
     */
    Command(String commandWord) {
        this.commandWord = commandWord;
    }
    
    /**
     * Returns the command word associated with this command.
     *
     * @return The command word string.
     */
    public String getCommandWord() {
        return commandWord;
    }
    
    /**
     * Determines the Command enum value from user input.
     * Matches input to command words, handling partial matches.
     *
     * @param input The user input string.
     * @return The corresponding Command enum value, or UNKNOWN if no match.
     */
    public static Command fromInput(String input) {
        String lowerInput = input.toLowerCase().trim();
        
        for (Command cmd : Command.values()) {
            if (lowerInput.equals(cmd.commandWord) || 
                lowerInput.startsWith(cmd.commandWord + " ")) {
                return cmd;
            }
        }
        
        return UNKNOWN;
    }
    
    /**
     * Extracts the argument portion from user input for this command.
     * Removes the command word and trims whitespace.
     *
     * @param input The full user input string.
     * @return The argument string, or empty string if no argument.
     */
    public String getArgument(String input) {
        if (input.length() > commandWord.length()) {
            return input.substring(commandWord.length()).trim();
        }
        return "";
    }
}