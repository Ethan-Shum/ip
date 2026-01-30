import java.util.Scanner;

/**
 * Handles all user interface interactions for the Kiko chatbot.
 * Responsible for displaying messages, reading input, and formatting output.
 */
public class Ui {
    private final Scanner scanner;
    
    /**
     * Constructs a Ui object with a Scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Displays the greeting message when the application starts.
     */
    public void showGreeting() {
        String greeting = " Hello! I'm Kiko the bunny\n"
                + " What can I do for you? >.<";
        System.out.println(greeting);
    }
    
    /**
     * Displays the farewell message when the application ends.
     */
    public void showFarewell() {
        String farewell = " Bye. Hope to see you again soon! Not rlly.";
        System.out.println(farewell);
    }
    
    /**
     * Reads a line of input from the user.
     * 
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }
    
    /**
     * Displays a message to the user.
     * 
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
    
    /**
     * Displays an error message to the user.
     * 
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        System.out.println(" Error: " + errorMessage);
    }
    
    /**
     * Displays the list of tasks.
     * 
     * @param tasks Array of tasks to display.
     */
    public void showTaskList(Task[] tasks) {
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
     * Displays a task with a message.
     * 
     * @param message The message to display before the task.
     * @param task The task to display.
     */
    public void showTask(String message, Task task) {
        System.out.println(message);
        System.out.println("   " + task);
    }
    
    /**
     * Displays task count information.
     * 
     * @param count The current number of tasks.
     */
    public void showTaskCount(int count) {
        System.out.println(" Now you have " + count + " tasks in the list.");
    }
    
    /**
     * Displays help for deadline command format.
     */
    public void showDeadlineHelp() {
        System.out.println(" OIII use the format: deadline <description> /by <date>");
        System.out.println(" Date formats accepted: yyyy-MM-dd HHmm, dd/MM/yyyy HHmm, MM/dd/yyyy HHmm");
        System.out.println(" Example: deadline return book /by 2019-12-02 1800");
        System.out.println(" Example: deadline return book /by 02/12/2019 1800");
    }
    
    /**
     * Displays help for event command format.
     */
    public void showEventHelp() {
        System.out.println(" OIIII use the format: event <description> /from <start> /to <end>");
        System.out.println(" Date formats accepted: yyyy-MM-dd HHmm, dd/MM/yyyy HHmm, MM/dd/yyyy HHmm");
    }
    
    /**
     * Displays date format help.
     */
    public void showDateFormatHelp() {
        System.out.println(" OI! Invalid date format. Try these formats:");
        System.out.println("   yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        System.out.println("   dd/MM/yyyy HHmm (e.g., 02/12/2019 1800)");
        System.out.println("   MM/dd/yyyy HHmm (e.g., 12/02/2019 1800)");
        System.out.println("   yyyy/MM/dd HHmm (e.g., 2019/12/02 1800)");
        System.out.println("   yyyy-MM-dd (time defaults to 0000)");
    }
}