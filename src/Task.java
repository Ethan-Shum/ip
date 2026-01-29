/**
 * Abstract base class representing a task.
 * Provides common functionality for all task types.
 */
abstract class Task {
    protected final String description;
    protected boolean isDone;
    
    /**
     * Constructs a Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    
    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Checks if the task is marked as done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }
    
    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }
    
    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }
    
    /**
     * Returns the type icon representing the task type.
     * Must be implemented by subclasses.
     *
     * @return A single character representing the task type.
     */
    public abstract String getTypeIcon();
    
    /**
     * Returns additional information specific to the task type.
     * Must be implemented by subclasses.
     *
     * @return Additional information about the task.
     */
    public abstract String getAdditionalInfo();
    
    /**
     * Returns a string representation of the task.
     * Format: [TypeIcon][Status] Description AdditionalInfo
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + (isDone ? "X" : " ") + "] " + description + getAdditionalInfo();
    }
}