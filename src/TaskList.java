class TaskList {
    private final Task[] tasks;
    private int count;
    
    public TaskList() {
        tasks = new Task[100];
        count = 0;
    }
    
    public void addTask(String taskDescription) {
        tasks[count] = new Task(taskDescription);
        count++;
    }
    
    public Task[] getAllTasks() {
        Task[] result = new Task[count];
        System.arraycopy(tasks, 0, result, 0, count);
        return result;
    }
    
    public boolean markTask(int index) {
        if (index >= 1 && index <= count) {
            tasks[index - 1].markAsDone();
            return true;
        }
        return false;
    }
    
    public boolean unmarkTask(int index) {
        if (index >= 1 && index <= count) {
            tasks[index - 1].markAsNotDone();
            return true;
        }
        return false;
    }
    
    public int getTaskCount() {
        return count;
    }
    
    public Task getTask(int index) {
        if (index >= 1 && index <= count) {
            return tasks[index - 1];
        }
        return null;
    }
}