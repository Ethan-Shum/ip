import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import kiko.tasklist.TaskList;
import kiko.task.Task;

/**
 * JUnit tests for the TaskList class.
 * Tests the markTask method with various scenarios including edge cases.
 */
public class TaskListTest {
    
    private TaskList taskList;
    private static final String TEST_FILE_PATH = "./data/test_kiko.txt";
    private static final String TEST_DIR_PATH = "./data/";
    
    @BeforeEach
    void setUp() {
        // Create a fresh TaskList for each test
        taskList = new TaskList();
        
        // Ensure test directory exists
        try {
            Files.createDirectories(Paths.get(TEST_DIR_PATH));
        } catch (IOException e) {
            // Ignore directory creation errors
        }
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test file after each test
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }
    
    @Test
    void testMarkTaskValidIndex() {
        // Add a task first
        taskList.addTodo("Test todo task");
        
        // Test marking a valid task (index 1)
        boolean result = taskList.markTask(1);
        
        assertTrue(result, "markTask should return true for valid index");
        
        // Verify the task is marked as done
        Task task = taskList.getTask(1);
        assertNotNull(task, "Task should exist");
        assertTrue(task.isDone(), "Task should be marked as done");
    }
    
    @Test
    void testMarkTaskInvalidIndexZero() {
        // Add a task first
        taskList.addTodo("Test todo task");
        
        // Test marking index 0 (invalid - should be 1-based)
        boolean result = taskList.markTask(0);
        
        assertFalse(result, "markTask should return false for index 0");
        
        // Verify the task is still not done
        Task task = taskList.getTask(1);
        assertNotNull(task, "Task should exist");
        assertFalse(task.isDone(), "Task should not be marked as done");
    }
    
    @Test
    void testMarkTaskInvalidIndexNegative() {
        // Add a task first
        taskList.addTodo("Test todo task");
        
        // Test marking negative index
        boolean result = taskList.markTask(-1);
        
        assertFalse(result, "markTask should return false for negative index");
    }
    
    @Test
    void testMarkTaskInvalidIndexTooHigh() {
        // Add a task first
        taskList.addTodo("Test todo task");
        
        // Test marking index beyond list size
        boolean result = taskList.markTask(2);
        
        assertFalse(result, "markTask should return false for index beyond list size");
    }
    
    @Test
    void testMarkTaskEmptyList() {
        // Test marking task in empty list
        boolean result = taskList.markTask(1);
        
        assertFalse(result, "markTask should return false for empty list");
    }
    
    @Test
    void testMarkTaskMultipleTasks() {
        // Add multiple tasks
        taskList.addTodo("First task");
        taskList.addTodo("Second task");
        taskList.addTodo("Third task");
        
        // Mark the second task
        boolean result = taskList.markTask(2);
        
        assertTrue(result, "markTask should return true for valid index");
        
        // Verify only the second task is marked
        assertFalse(taskList.getTask(1).isDone(), "First task should not be marked");
        assertTrue(taskList.getTask(2).isDone(), "Second task should be marked");
        assertFalse(taskList.getTask(3).isDone(), "Third task should not be marked");
    }
    
    @Test
    void testMarkTaskAlreadyDone() {
        // Add and mark a task
        taskList.addTodo("Test todo task");
        taskList.markTask(1);
        
        // Try to mark it again
        boolean result = taskList.markTask(1);
        
        assertTrue(result, "markTask should return true even if task already done");
        assertTrue(taskList.getTask(1).isDone(), "Task should still be marked as done");
    }
    
    @Test
    void testMarkTaskWithDifferentTaskTypes() {
        // Test with different task types
        taskList.addTodo("Todo task");
        taskList.addDeadline("Deadline task", LocalDateTime.now().plusDays(1));
        taskList.addEvent("Event task", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        
        // Mark each task type
        assertTrue(taskList.markTask(1), "Should mark Todo task");
        assertTrue(taskList.markTask(2), "Should mark Deadline task");
        assertTrue(taskList.markTask(3), "Should mark Event task");
        
        // Verify all are marked
        assertTrue(taskList.getTask(1).isDone(), "Todo task should be marked");
        assertTrue(taskList.getTask(2).isDone(), "Deadline task should be marked");
        assertTrue(taskList.getTask(3).isDone(), "Event task should be marked");
    }
}