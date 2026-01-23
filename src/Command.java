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
    
    Command(String commandWord) {
        this.commandWord = commandWord;
    }
    
    public String getCommandWord() {
        return commandWord;
    }
    
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
    
    public String getArgument(String input) {
        if (input.length() > commandWord.length()) {
            return input.substring(commandWord.length()).trim();
        }
        return "";
    }
}