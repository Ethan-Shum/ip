abstract class Task {
    protected final String description;
    protected boolean isDone;
    
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isDone() {
        return isDone;
    }
    
    public void markAsDone() {
        isDone = true;
    }
    
    public void markAsNotDone() {
        isDone = false;
    }
    
    public abstract String getTypeIcon();
    
    public abstract String getAdditionalInfo();
    
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + (isDone ? "X" : " ") + "] " + description + getAdditionalInfo();
    }
}