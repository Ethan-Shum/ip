package kiko.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task with a specific due date/time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    
    /**
     * Constructs a Deadline task with description and deadline.
     *
     * @param description The description of the Deadline task.
     * @param by The deadline date/time for the task in yyyy-MM-dd HHmm format.
     */
    public Deadline(String description, String by) throws DateTimeParseException {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
    }
    
    /**
     * Constructs a Deadline task with description and LocalDateTime.
     *
     * @param description The description of the Deadline task.
     * @param by The deadline date/time as LocalDateTime.
     */
    public Deadline(String description, LocalDateTime by) {
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
        return " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }
    
    /**
     * Returns the deadline date/time of this task.
     *
     * @return The deadline LocalDateTime.
     */
    public LocalDateTime getBy() {
        return by;
    }
    
    /**
     * Returns the deadline as a formatted string for storage.
     *
     * @return The deadline in yyyy-MM-dd HHmm format.
     */
    public String getByForStorage() {
        return by.format(INPUT_FORMATTER);
    }
}