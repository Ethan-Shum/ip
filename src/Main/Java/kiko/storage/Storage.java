package kiko.storage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import kiko.task.Deadline;
import kiko.task.Event;
import kiko.task.Task;
import kiko.task.Todo;

/**
 * Handles loading and saving of tasks to a file.
 * Uses a relative path that works across different operating systems.
 */
public class Storage {
    private static final String FILE_PATH = "./data/kiko.txt";
    private static final String DIRECTORY_PATH = "./data/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    
    /**
     * Saves all tasks to the file.
     * Creates the directory and file if they don't exist.
     *
     * @param tasks The list of tasks to save.
     */
    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            // Create directory if it doesn't exist
            Path directoryPath = Paths.get(DIRECTORY_PATH);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            
            // Write tasks to file
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(taskToFileString(task) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(" Error saving tasks to file: " + e.getMessage());
        }
    }
    
    /**
     * Loads tasks from the file.
     * Returns an empty list if the file doesn't exist.
     *
     * @return List of loaded tasks.
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            // File doesn't exist yet - return empty list
            return tasks;
        }
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                Task task = parseTaskFromString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println(" Error loading tasks from file: " + e.getMessage());
        }
        
        return tasks;
    }
    
    /**
     * Converts a task to a string representation for file storage.
     * Format: T | 1 | read book
     *         D | 0 | return book | 2019-12-02 1800
     *         E | 0 | project meeting | 2019-08-06 1400 | 2019-08-06 1600
     *
     * @param task The task to convert.
     * @return String representation for file storage.
     */
    private static String taskToFileString(Task task) {
        String type = task.getTypeIcon();
        String status = task.isDone() ? "1" : "0";
        String description = task.getDescription();
        
        if (task instanceof Todo) {
            return type + " | " + status + " | " + description;
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return type + " | " + status + " | " + description + " | " + deadline.getByForStorage();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return type + " | " + status + " | " + description + " | " + event.getFromForStorage() + " | " + event.getToForStorage();
        }
        
        return "";
    }
    
    /**
     * Parses a task from a string representation in the file.
     *
     * @param line The line from the file.
     * @return The parsed Task, or null if parsing fails.
     */
    public static Task parseTaskFromString(String line) {
        try {
            String[] parts = line.split(" \\| ");
            
            if (parts.length < 3) {
                return null;
            }
            
            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();
            
            Task task = null;
            
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length >= 4) {
                    String by = parts[3].trim();
                    LocalDateTime byDateTime = LocalDateTime.parse(by, DATE_FORMATTER);
                    task = new Deadline(description, byDateTime);
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    String from = parts[3].trim();
                    String to = parts[4].trim();
                    LocalDateTime fromDateTime = LocalDateTime.parse(from, DATE_FORMATTER);
                    LocalDateTime toDateTime = LocalDateTime.parse(to, DATE_FORMATTER);
                    task = new Event(description, fromDateTime, toDateTime);
                }
                break;
            }
            
            if (task != null && isDone) {
                task.markAsDone();
            }
            
            return task;
        } catch (DateTimeParseException e) {
            System.out.println(" Error parsing date from line: " + line);
            return null;
        } catch (Exception e) {
            System.out.println(" Error parsing task from line: " + line);
            return null;
        }
    }
}