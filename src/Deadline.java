/**
 * Represents a Deadline task with a specific due date/time.
 */
class Deadline extends Task {
    private final String by;
    
    /**
     * Constructs a Deadline task with description and deadline.
     *
     * @param description The description of the Deadline task.
     * @param by The deadline date/time for the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }
    
    /**
     * Returns the type icon for Deadline tasks.
     *
     * @return "D" representing Deadline.
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }
    
    /**
     * Returns additional information for Deadline tasks.
     * Includes the deadline date/time.
     *
     * @return String containing the deadline information.
     */
    @Override
    public String getAdditionalInfo() {
        return " (by: " + by + ")";
    }
    
    /**
     * Returns the deadline date/time of this task.
     *
     * @return The deadline string.
     */
    public String getBy() {
        return by; 
    }
}