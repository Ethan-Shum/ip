import java.util.ArrayList;

class TaskList {
    private final ArrayList<Task> tasks;
    
    public TaskList() {
        tasks = new ArrayList<>();
    }
    
    public void addTask(String taskDescription) {
        // Default behavior - create a Todo task
        tasks.add(new Todo(taskDescription));
    }
    
    public void addTodo(String description) {
        tasks.add(new Todo(description));
    }
    
    public void addDeadline(String description, String by) {
        tasks.add(new Deadline(description, by));
    }
    
    public void addEvent(String description, String from, String to) {
        tasks.add(new Event(description, from, to));
    }
    
    public Task[] getAllTasks() {
        return tasks.toArray(new Task[0]);
    }
    
    public boolean markTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            tasks.get(index - 1).markAsDone();
            return true;
        }
        return false;
    }
    
    public boolean unmarkTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            tasks.get(index - 1).markAsNotDone();
            return true;
        }
        return false;
    }
    
    public int getTaskCount() {
        return tasks.size();
    }
    
    public Task getTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            return tasks.get(index - 1);
        }
        return null;
    }
    
    // New method to delete a task
    public Task deleteTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            return tasks.remove(index - 1);
        }
        return null;
    }
}