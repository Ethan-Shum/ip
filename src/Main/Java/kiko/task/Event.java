package kiko.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event task with a start time and end time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    
    /**
     * Constructs an Event task with description, start time, and end time.
     *
     * @param description The description of the Event task.
     * @param from The start time of the event in yyyy-MM-dd HHmm format.
     * @param to The end time of the event in yyyy-MM-dd HHmm format.
     */
    public Event(String description, String from, String to) throws DateTimeParseException {
        super(description);
        this.from = LocalDateTime.parse(from, INPUT_FORMATTER);
        this.to = LocalDateTime.parse(to, INPUT_FORMATTER);
    }
    
    /**
     * Constructs an Event task with description and LocalDateTime objects.
     *
     * @param description The description of the Event task.
     * @param from The start time as LocalDateTime.
     * @param to The end time as LocalDateTime.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    
    /**
     * Returns the type icon for Event tasks.
     *
     * @return "E" representing Event.
     */
    @Override
    public String getTypeIcon() {
        return "E";
    }
    
    /**
     * Returns additional information for Event tasks.
     * Includes the start and end times.
     *
     * @return String containing the event time information.
     */
    @Override
    public String getAdditionalInfo() {
        return " (from: " + from.format(DISPLAY_FORMATTER) + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
    
    /**
     * Returns the start time of this event.
     *
     * @return The start time LocalDateTime.
     */
    public LocalDateTime getFrom() {
        return from;
    }
    
    /**
     * Returns the end time of this event.
     *
     * @return The end time LocalDateTime.
     */
    public LocalDateTime getTo() {
        return to;
    }
    
    /**
     * Returns the start time as a formatted string for storage.
     *
     * @return The start time in yyyy-MM-dd HHmm format.
     */
    public String getFromForStorage() {
        return from.format(INPUT_FORMATTER);
    }
    
    /**
     * Returns the end time as a formatted string for storage.
     *
     * @return The end time in yyyy-MM-dd HHmm format.
     */
    public String getToForStorage() {
        return to.format(INPUT_FORMATTER);
    }
}