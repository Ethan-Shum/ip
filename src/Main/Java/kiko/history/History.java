package kiko.history;

import java.util.Stack;
import kiko.tasklist.TaskList;

/**
 * Manages the history of TaskList states to support undo operations.
 * Implements the Memento pattern.
 */
public class History {
    private Stack<TaskList> historyStack;

    public History() {
        this.historyStack = new Stack<>();
    }

    /**
     * Saves the current state of the TaskList to the history.
     * 
     * @param taskList The TaskList state to save.
     */
    public void saveState(TaskList taskList) {
        historyStack.push(new TaskList(taskList));
    }

    /**
     * Restores the previous state of the TaskList.
     * 
     * @return The previous TaskList state, or null if no history exists.
     */
    public TaskList undo() {
        if (historyStack.isEmpty()) {
            return null;
        }
        return historyStack.pop();
    }

    /**
     * Checks if there are any states in the history.
     * 
     * @return true if undo is possible, false otherwise.
     */
    public boolean canUndo() {
        return !historyStack.isEmpty();
    }
}