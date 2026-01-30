package kiko.ui;

/**
 * Handles all user interface interactions for the Kiko chatbot.
 * Provides methods for displaying messages, greetings, and error messages.
 */
public class Ui {
    
    /**
     * Displays the greeting message when the chatbot starts.
     */
    public void showGreeting() {
        String greeting = " Hello! I'm Kiko the bunny\n"
                + " What can I do for you? >.<";
        System.out.println(greeting);
    }
    
    /**
     * Displays the farewell message when the chatbot exits.
     */
    public void showFarewell() {
        String farewell = " Bye. Hope to see you again soon! Not rlly.";
        System.out.println(farewell);
    }
    
    /**
     * Displays an unknown command error message.
     */
    public void showUnknownCommand() {
        System.out.println(" Walao i dunno what that means ;<<");
    }
    
    /**
     * Displays the list of tasks.
     * 
     * @param tasks Array of task strings to display
     */
    public void showTaskList(String[] tasks) {
        if (tasks.length == 0) {
            System.out.println(" No tasks added yet");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.length; i++) {
                System.out.println(" " + (i + 1) + "." + tasks[i]);
            }
        }
    }
    
    /**
     * Displays a success message when a task is marked as done.
     * 
     * @param taskString The string representation of the marked task
     */
    public void showTaskMarked(String taskString) {
        System.out.println(" Yayyy good job! Task is donee:");
        System.out.println("   " + taskString);
    }
    
    /**
     * Displays a success message when a task is unmarked.
     * 
     * @param taskString The string representation of the unmarked task
     */
    public void showTaskUnmarked(String taskString) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + taskString);
    }
    
    /**
     * Displays a success message when a task is deleted.
     * 
     * @param taskString The string representation of the deleted task
     * @param remainingCount The number of remaining tasks
     */
    public void showTaskDeleted(String taskString, int remainingCount) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + taskString);
        System.out.println(" Now you have " + remainingCount + " tasks in the list.");
    }
    
    /**
     * Displays a success message when a todo task is added.
     * 
     * @param taskString The string representation of the added task
     * @param totalCount The total number of tasks
     */
    public void showTodoAdded(String taskString, int totalCount) {
        System.out.println(" Got itz. I've added this task:");
        System.out.println("   " + taskString);
        System.out.println(" Now you have " + totalCount + " tasks in the list.");
    }
    
    /**
     * Displays a success message when a deadline task is added.
     * 
     * @param taskString The string representation of the added task
     * @param totalCount The total number of tasks
     */
    public void showDeadlineAdded(String taskString, int totalCount) {
        System.out.println(" Got itz. I've added this task:");
        System.out.println("   " + taskString);
        System.out.println(" Now you have " + totalCount + " tasks in the list.");
    }
    
    /**
     * Displays a success message when an event task is added.
     * 
     * @param taskString The string representation of the added task
     * @param totalCount The total number of tasks
     */
    public void showEventAdded(String taskString, int totalCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + taskString);
        System.out.println(" Now you have " + totalCount + " tasks in the list.");
    }
    
    /**
     * Displays an error message for invalid task number.
     * 
     * @param maxTaskNumber The maximum valid task number
     */
    public void showInvalidTaskNumber(int maxTaskNumber) {
        System.out.println(" I dunno this task number. Please enter a number between 1 and " + maxTaskNumber);
    }
    
    /**
     * Displays an error message for invalid number format.
     * 
     * @param command The command that caused the error (mark, unmark, delete)
     */
    public void showInvalidNumberFormat(String command) {
        System.out.println(" Oi enter a valid task number after '" + command + "'");
    }
    
    /**
     * Displays an error message for empty todo description.
     */
    public void showEmptyTodoDescription() {
        System.out.println(" Oi provide a description for the todo task");
    }
    
    /**
     * Displays usage instructions for deadline command.
     */
    public void showDeadlineUsage() {
        System.out.println(" OIII use the format: deadline <description> /by <date>");
        System.out.println(" Date formats accepted: yyyy-MM-dd HHmm, dd/MM/yyyy HHmm, MM/dd/yyyy HHmm");
        System.out.println(" Example: deadline return book /by 2019-12-02 1800");
        System.out.println(" Example: deadline return book /by 02/12/2019 1800");
    }
    
    /**
     * Displays usage instructions for deadline command when /by is missing.
     */
    public void showDeadlineMissingBy() {
        System.out.println(" OIII use the format: deadline <description> /by <date>");
        System.out.println(" Date formats accepted: yyyy-MM-dd HHmm, dd/MM/yyyy HHmm, MM/dd/yyyy HHmm");
    }
    
    /**
     * Displays an error message for missing deadline description or date.
     */
    public void showDeadlineMissingFields() {
        System.out.println(" OIIIIII provide both description and deadline date");
    }
    
    /**
     * Displays usage instructions for event command.
     */
    public void showEventUsage() {
        System.out.println(" OIIII use the format: event <description> /from <start> /to <end>");
        System.out.println(" Date formats accepted: yyyy-MM-dd HHmm, dd/MM/yyyy HHmm, MM/dd/yyyy HHmm");
    }
    
    /**
     * Displays usage instructions for event command when /from or /to is missing.
     */
    public void showEventMissingTimeMarkers() {
        System.out.println(" OIIII use the format: event <description> /from <start> /to <end>");
        System.out.println(" Date formats accepted: yyyy-MM-dd HHmm, dd/MM/yyyy HHmm, MM/dd/yyyy HHmm");
    }
    
    /**
     * Displays an error message for missing event fields.
     */
    public void showEventMissingFields() {
        System.out.println(" OIIII provide description, start time, and end time");
    }
    
    /**
     * Displays date format error messages with examples.
     */
    public void showInvalidDateFormat() {
        System.out.println(" OI! Invalid date format. Try these formats:");
        System.out.println("   yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        System.out.println("   dd/MM/yyyy HHmm (e.g., 02/12/2019 1800)");
        System.out.println("   MM/dd/yyyy HHmm (e.g., 12/02/2019 1800)");
        System.out.println("   yyyy/MM/dd HHmm (e.g., 2019/12/02 1800)");
        System.out.println("   yyyy-MM-dd (time defaults to 0000)");
    }
    
    /**
     * Displays search results for tasks matching a keyword.
     * 
     * @param tasks Array of task strings that match the search keyword
     * @param keyword The keyword that was searched for
     */
    public void showFindResults(String[] tasks, String keyword) {
        if (tasks.length == 0) {
            System.out.println(" No matching tasks found for '" + keyword + "'");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.length; i++) {
                System.out.println(" " + (i + 1) + "." + tasks[i]);
            }
        }
    }
    
    /**
     * Displays an error message for empty find keyword.
     */
    public void showEmptyFindKeyword() {
        System.out.println(" Oi provide a keyword to search for");
    }
}