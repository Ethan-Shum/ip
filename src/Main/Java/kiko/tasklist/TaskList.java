package kiko.tasklist;
import java.util.ArrayList;

import kiko.task.Deadline;
import kiko.task.Event;
import kiko.task.Task;
import kiko.task.Todo;
import kiko.storage.Storage;

import java.time.LocalDateTime;

/**
 * Represents a list of tasks with operations to manage them.
 * Provides methods to add, delete, mark, unmark, and retrieve tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    
    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }
    
    /**
     * Constructs a TaskList with existing tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Copy constructor for deep copying.
     * 
     * @param other The TaskList to copy.
     */
    public TaskList(TaskList other) {
        this.tasks = new ArrayList<>();
        for (Task task : other.tasks) {
            if (task instanceof Todo) {
                Todo newTodo = new Todo(task.getDescription());
                if (task.isDone()) newTodo.markAsDone();
                this.tasks.add(newTodo);
            } else if (task instanceof Deadline) {
                Deadline d = (Deadline) task;
                Deadline newDeadline = new Deadline(d.getDescription(), d.getBy());
                if (task.isDone()) newDeadline.markAsDone();
                this.tasks.add(newDeadline);
            } else if (task instanceof Event) {
                Event e = (Event) task;
                Event newEvent = new Event(e.getDescription(), e.getFrom(), e.getTo());
                if (task.isDone()) newEvent.markAsDone();
                this.tasks.add(newEvent);
            }
        }
    }
    
    /**
     * Adds a Todo task with the given description.
     * Default behavior - creates a Todo task.
     *
     * @param taskDescription The description of the task to add.
     */
    public void addTask(String taskDescription) {
        // Default behavior - create a Todo task
        assert taskDescription != null : "Task description should not be null";
        tasks.add(new Todo(taskDescription));
        saveTasks();
    }
    
    /**
     * Adds a Todo task with the given description.
     *
     * @param description The description of the Todo task.
     */
    public void addTodo(String description) {
        assert description != null && !description.isEmpty() : "Todo description cannot be empty";
        tasks.add(new Todo(description));
        saveTasks();
    }
    
    /**
     * Adds a Deadline task with the given description and deadline.
     *
     * @param description The description of the Deadline task.
     * @param by The deadline date/time for the task.
     */
    public void addDeadline(String description, LocalDateTime by) {
        assert description != null && !description.isEmpty() : "Deadline description cannot be empty";
        assert by != null : "Deadline date cannot be null";
        tasks.add(new Deadline(description, by));
        saveTasks();
    }
    
    /**
     * Adds an Event task with the given description, start time, and end time.
     *
     * @param description The description of the Event task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public void addEvent(String description, LocalDateTime from, LocalDateTime to) {
        assert description != null && !description.isEmpty() : "Event description cannot be empty";
        assert from != null : "Event start time cannot be null";
        assert to != null : "Event end time cannot be null";
        tasks.add(new Event(description, from, to));
        saveTasks();
    }
    
    /**
     * Returns the internal ArrayList of tasks.
     * 
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getAllTasksArrayList() {
        return tasks;
    }

    /**
     * Returns all tasks in the list as an array.
     *
     * @return An array containing all tasks in the list.
     */
    public Task[] getAllTasks() {
        return tasks.toArray(new Task[0]);
    }
    
    /**
     * Marks a task as done based on its index in the list.
     * Index is 1-based (first task is index 1).
     *
     * @param index The 1-based index of the task to mark.
     * @return true if the task was successfully marked, false if index is invalid.
     */
    public boolean markTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            assert tasks.get(index - 1) != null : "Task at valid index should not be null";
            tasks.get(index - 1).markAsDone();
            saveTasks();
            return true;
        }
        return false;
    }
    
    /**
     * Marks a task as not done based on its index in the list.
     * Index is 1-based (first task is index 1).
     *
     * @param index The 1-based index of the task to unmark.
     * @return true if the task was successfully unmarked, false if index is invalid.
     */
    public boolean unmarkTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            assert tasks.get(index - 1) != null : "Task at valid index should not be null";
            tasks.get(index - 1).markAsNotDone();
            saveTasks();
            return true;
        }
        return false;
    }
    
    /**
     * Returns the number of tasks in the list.
     *
     * @return The total count of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }
    
    /**
     * Retrieves a task by its index in the list.
     * Index is 1-based (first task is index 1).
     *
     * @param index The 1-based index of the task to retrieve.
     * @return The task at the specified index, or null if index is invalid.
     */
    public Task getTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            return tasks.get(index - 1);
        }
        return null;
    }
    
    /**
     * Deletes a task from the list based on its index.
     * Index is 1-based (first task is index 1).
     *
     * @param index The 1-based index of the task to delete.
     * @return The deleted task, or null if index is invalid.
     */
    public Task deleteTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            Task deletedTask = tasks.remove(index - 1);
            saveTasks();
            return deletedTask;
        }
        return null;
    }
    
    /**
     * Saves the current task list to file.
     */
    private void saveTasks() {
        Storage.saveTasks(tasks);
    }
    
    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return An array of tasks that match the keyword, or empty array if none found.
     */
    public Task[] findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }
        
        return matchingTasks.toArray(new Task[0]);
    }
}