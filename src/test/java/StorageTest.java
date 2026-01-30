import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import kiko.storage.Storage;
import kiko.task.Task;
import kiko.task.Todo;
import kiko.task.Deadline;
import kiko.task.Event;

/**
 * JUnit tests for the Storage class.
 * Tests the parseTaskFromString method with various scenarios including edge cases.
 */
public class StorageTest {
    
    @Test
    void testParseValidTodoTask() {
        String line = "T | 0 | Read book";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNotNull(task, "Should parse valid Todo task");
        assertTrue(task instanceof Todo, "Should be Todo instance");
        assertEquals("Read book", task.getDescription(), "Description should match");
        assertFalse(task.isDone(), "Task should not be done (status 0)");
    }
    
    @Test
    void testParseValidTodoTaskDone() {
        String line = "T | 1 | Complete project";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNotNull(task, "Should parse valid Todo task");
        assertTrue(task instanceof Todo, "Should be Todo instance");
        assertEquals("Complete project", task.getDescription(), "Description should match");
        assertTrue(task.isDone(), "Task should be done (status 1)");
    }
    
    @Test
    void testParseValidDeadlineTask() {
        String line = "D | 0 | Submit assignment | 2024-12-31 2359";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNotNull(task, "Should parse valid Deadline task");
        assertTrue(task instanceof Deadline, "Should be Deadline instance");
        assertEquals("Submit assignment", task.getDescription(), "Description should match");
        assertFalse(task.isDone(), "Task should not be done");
        
        // Verify deadline-specific properties
        Deadline deadline = (Deadline) task;
        LocalDateTime expectedDateTime = LocalDateTime.of(2024, 12, 31, 23, 59);
        assertEquals(expectedDateTime, deadline.getBy(), "Deadline should match");
    }
    
    @Test
    void testParseValidEventTask() {
        String line = "E | 1 | Team meeting | 2024-12-25 1400 | 2024-12-25 1600";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNotNull(task, "Should parse valid Event task");
        assertTrue(task instanceof Event, "Should be Event instance");
        assertEquals("Team meeting", task.getDescription(), "Description should match");
        assertTrue(task.isDone(), "Task should be done (status 1)");
        
        // Verify event-specific properties
        Event event = (Event) task;
        LocalDateTime expectedFrom = LocalDateTime.of(2024, 12, 25, 14, 0);
        LocalDateTime expectedTo = LocalDateTime.of(2024, 12, 25, 16, 0);
        assertEquals(expectedFrom, event.getFrom(), "Event start time should match");
        assertEquals(expectedTo, event.getTo(), "Event end time should match");
    }
    
    @Test
    void testParseInvalidFormatTooFewParts() {
        String line = "T | 0"; // Missing description
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNull(task, "Should return null for invalid format (too few parts)");
    }
    
    @Test
    void testParseInvalidFormatEmptyLine() {
        String line = "";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNull(task, "Should return null for empty line");
    }
    
    @Test
    void testParseInvalidFormatNullLine() {
        Task task = Storage.parseTaskFromString(null);
        
        assertNull(task, "Should return null for null line");
    }
    
    @Test
    void testParseInvalidType() {
        String line = "X | 0 | Invalid task type";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNull(task, "Should return null for invalid task type");
    }
    
    @Test
    void testParseInvalidDateFormat() {
        String line = "D | 0 | Invalid date task | invalid-date-format";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNull(task, "Should return null for invalid date format");
    }
    
    @Test
    void testParseDeadlineMissingDate() {
        String line = "D | 0 | Deadline without date";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNull(task, "Should return null for Deadline missing date");
    }
    
    @Test
    void testParseEventMissingDates() {
        String line = "E | 0 | Event without dates";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNull(task, "Should return null for Event missing dates");
    }
    
    @Test
    void testParseEventOnlyStartDate() {
        String line = "E | 0 | Event with only start | 2024-12-25 1400";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNull(task, "Should return null for Event with only start date");
    }
    
    @Test
    void testParseWhitespaceHandling() {
        String line = "  T  |  1  |  Task with whitespace  ";
        
        Task task = Storage.parseTaskFromString(line);
        
        assertNotNull(task, "Should handle whitespace correctly");
        assertEquals("Task with whitespace", task.getDescription(), "Should trim whitespace");
        assertTrue(task.isDone(), "Should parse status correctly despite whitespace");
    }
    
    @Test
    void testParseSpecialCharactersInDescription() {
        String line = "T | 0 | Task with | pipe character";
        
        Task task = Storage.parseTaskFromString(line);
        
        // This should parse as "Task with" since the pipe splits the description
        assertNotNull(task, "Should parse task with special characters");
        assertEquals("Task with", task.getDescription(), "Should handle pipe in description");
    }
    
    @Test
    void testParseEmptyDescription() {
        String line = "T | 0 | ";
        
        Task task = Storage.parseTaskFromString(line);
        
        // The parsing might fail due to the trailing space after the last pipe
        // Let's test with a more realistic empty description scenario
        assertNull(task, "Should return null for malformed line with trailing space");
    }
}