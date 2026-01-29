import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving of tasks to a file.
 * Uses a relative path that works across different operating systems.
 */
public class Storage {
    private static final String FILE_PATH = "./data/kiko.txt";
    private static final String DIRECTORY_PATH = "./data/";
    
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
     *         D | 0 | return book | June 6th
     *         E | 0 | project meeting | Aug 6th 2-4pm
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
            return type + " | " + status + " | " + description + " | " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return type + " | " + status + " | " + description + " | " + event.getFrom() + " | " + event.getTo();
        }
        
        return "";
    }
    
    /**
     * Parses a task from a string representation in the file.
     *
     * @param line The line from the file.
     * @return The parsed Task, or null if parsing fails.
     */
    private static Task parseTaskFromString(String line) {
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
                        task = new Deadline(description, by);
                    }
                    break;
                case "E":
                    if (parts.length >= 5) {
                        String from = parts[3].trim();
                        String to = parts[4].trim();
                        task = new Event(description, from, to);
                    }
                    break;
            }
            
            if (task != null && isDone) {
                task.markAsDone();
            }
            
            return task;
        } catch (Exception e) {
            System.out.println(" Error parsing task from line: " + line);
            return null;
        }
    }
}