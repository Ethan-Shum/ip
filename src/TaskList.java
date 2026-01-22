class TaskList {
    private final String[] tasks;
    private int count;
    
    public TaskList() {
        tasks = new String[100];
        count = 0;
    }
    
    public void addTask(String task) {
        tasks[count] = task;
        count++;
    }
    
    public String[] getAllTasks() {
        String[] result = new String[count];
        System.arraycopy(tasks, 0, result, 0, count);
        return result;
    }
}