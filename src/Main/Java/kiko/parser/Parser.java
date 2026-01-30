package kiko.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Handles parsing of dates and command arguments for the Kiko chatbot.
 * Provides methods for parsing various date formats and extracting command arguments.
 */
public class Parser {
    
    // Date parser that handles multiple formats
    private static final DateTimeFormatter[] DATE_PARSERS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy HHmm"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm"),
        new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .optionalStart()
            .appendPattern(" HHmm")
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .toFormatter()
    };
    
    /**
     * Parses a date/time string using multiple possible formats.
     *
     * @param dateString The date/time string to parse.
     * @return The parsed LocalDateTime.
     * @throws DateTimeParseException If the string cannot be parsed with any format.
     */
    public LocalDateTime parseDateTime(String dateString) throws DateTimeParseException {
        for (DateTimeFormatter formatter : DATE_PARSERS) {
            try {
                return LocalDateTime.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }
        throw new DateTimeParseException("Unable to parse date: " + dateString, dateString, 0);
    }
    
    /**
     * Parses deadline argument to extract description and date.
     *
     * @param argument The full argument string
     * @return A string array where [0] is description and [1] is date, or null if invalid
     */
    public String[] parseDeadlineArgument(String argument) {
        if (argument.isEmpty()) {
            return null;
        }
        
        int byIndex = argument.indexOf("/by ");
        if (byIndex == -1) {
            return null;
        }
        
        String description = argument.substring(0, byIndex).trim();
        String dateString = argument.substring(byIndex + 4).trim();
        
        if (description.isEmpty() || dateString.isEmpty()) {
            return null;
        }
        
        return new String[]{description, dateString};
    }
    
    /**
     * Parses event argument to extract description, start date, and end date.
     *
     * @param argument The full argument string
     * @return A string array where [0] is description, [1] is start date, and [2] is end date, or null if invalid
     */
    public String[] parseEventArgument(String argument) {
        if (argument.isEmpty()) {
            return null;
        }
        
        int fromIndex = argument.indexOf("/from ");
        int toIndex = argument.indexOf("/to ");
        
        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
            return null;
        }
        
        String description = argument.substring(0, fromIndex).trim();
        String fromString = argument.substring(fromIndex + 6, toIndex).trim();
        String toString = argument.substring(toIndex + 4).trim();
        
        if (description.isEmpty() || fromString.isEmpty() || toString.isEmpty()) {
            return null;
        }
        
        return new String[]{description, fromString, toString};
    }
    
    /**
     * Parses a task number string to integer.
     *
     * @param argument The task number as string
     * @return The parsed task number, or -1 if invalid
     */
    public int parseTaskNumber(String argument) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}