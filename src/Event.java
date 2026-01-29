/**
 * Represents an Event task with a start time and end time.
 */
class Event extends Task {
    private final String from;
    private final String to;
    
    /**
     * Constructs an Event task with description, start time, and end time.
     *
     * @param description The description of the Event task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
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
        return " (from: " + from + " to: " + to + ")";
    }
    
    /**
     * Returns the start time of this event.
     *
     * @return The start time string.
     */
    public String getFrom() {
        return from;
    }
    
    /**
     * Returns the end time of this event.
     *
     * @return The end time string.
     */
    public String getTo() {
        return to;
    }
}