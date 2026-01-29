/**
 * Represents a Todo task without any date/time constraints.
 */
class Todo extends Task {
    
    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the Todo task.
     */
    public Todo(String description) {
        super(description);
    }
    
    /**
     * Returns additional information for Todo tasks.
     * Todo tasks have no additional information.
     *
     * @return An empty string.
     */
    @Override
    public String getAdditionalInfo() {
        return "";
    }
    
    /**
     * Returns the type icon for Todo tasks.
     *
     * @return "T" representing Todo.
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }
}