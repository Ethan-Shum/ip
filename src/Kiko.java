import java.util.Scanner;
import java.util.ArrayList;

/**
 * Kiko is a task management chatbot that allows users to manage their tasks.
 * Supports adding todos, deadlines, and events, marking tasks as done/not done,
 * listing tasks, and deleting tasks.
 * The chatbot continues until the user says "bye".
 */
public class Kiko {
    /**
     * Main entry point for the Kiko chatbot application.
     * Displays a greeting, then enters a loop to process user commands.
     * Commands are parsed and delegated to appropriate handler methods.
     * The loop continues until the user enters "bye".
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        String greeting = " Hello! I'm Kiko the bunny\n"
                + " What can I do for you? >.<";
        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in);
        
        // Load tasks from file on startup
        ArrayList<Task> loadedTasks = Storage.loadTasks();
        TaskList taskList = new TaskList(loadedTasks);
        
        String input;
        
        do {
            input = scanner.nextLine();
            Command command = Command.fromInput(input);
            String argument = command.getArgument(input.toLowerCase());
            
            switch (command) {
                case LIST:
                    handleList(taskList);
                    break;
                    
                case MARK:
                    handleMark(taskList, argument);
                    break;
                    
                case UNMARK:
                    handleUnmark(taskList, argument);
                    break;
                    
                case DELETE:
                    handleDelete(taskList, argument);
                    break;
                    
                case TODO:
                    handleTodo(taskList, argument);
                    break;
                    
                case DEADLINE:
                    handleDeadline(taskList, argument);
                    break;
                    
                case EVENT:
                    handleEvent(taskList, argument);
                    break;
                    
                case BYE:
                    break;
                    
                case UNKNOWN:
                default:
                    System.out.println(" Walao i dunno what that means ;<<");
                    break;
            }
        } while (!input.equalsIgnoreCase("bye"));

        String farewell = " Bye. Hope to see you again soon! Not rlly.";
        System.out.println(farewell);
    }
    
    private static void handleList(TaskList taskList) {
        Task[] tasks = taskList.getAllTasks();
        if (tasks.length == 0) {
            System.out.println(" No tasks added yet");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.length; i++) {
                System.out.println(" " + (i + 1) + "." + tasks[i]);
            }
        }
    }
    
    private static void handleMark(TaskList taskList, String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskList.markTask(taskNumber)) {
                Task task = taskList.getTask(taskNumber);
                System.out.println(" Yayyy good job! Task is donee:");
                System.out.println("   " + task);
            } else {
                System.out.println(" I dunno this task number. Please enter a number between 1 and " + taskList.getTaskCount());
            }
        } catch (NumberFormatException e) {
            System.out.println(" Oi enter a valid task number after 'mark'");
        }
    }
    
    private static void handleUnmark(TaskList taskList, String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskList.unmarkTask(taskNumber)) {
                Task task = taskList.getTask(taskNumber);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + task);
            } else {
                System.out.println(" I dunno this task number. Please enter a number between 1 and " + taskList.getTaskCount());
            }
        } catch (NumberFormatException e) {
            System.out.println(" Oi enter a valid task number after 'unmark'");
        }
    }
    
    private static void handleDelete(TaskList taskList, String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            Task deletedTask = taskList.deleteTask(taskNumber);
            if (deletedTask != null) {
                System.out.println(" Noted. I've removed this task:");
                System.out.println("   " + deletedTask);
                System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
            } else {
                System.out.println(" I dunno this task number. Please enter a number between 1 and " + taskList.getTaskCount());
            }
        } catch (NumberFormatException e) {
            System.out.println(" Oi enter a valid task number after 'delete'");
        }
    }
    
    private static void handleTodo(TaskList taskList, String argument) {
        if (argument.isEmpty()) {
            System.out.println(" Oi provide a description for the todo task");
            return;
        }
        
        taskList.addTodo(argument);
        System.out.println(" Got itz. I've added this task:");
        System.out.println("   " + taskList.getTask(taskList.getTaskCount()));
        System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
    }
    
    private static void handleDeadline(TaskList taskList, String argument) {
        if (argument.isEmpty()) {
            System.out.println(" OIII use the format: deadline <description> /by <date>");
            return;
        }
        
        int byIndex = argument.indexOf("/by ");
        if (byIndex == -1) {
            System.out.println(" OIII use the format: deadline <description> /by <date>");
            return;
        }
        
        String description = argument.substring(0, byIndex).trim();
        String by = argument.substring(byIndex + 4).trim();
        
        if (description.isEmpty() || by.isEmpty()) {
            System.out.println(" OIIIIII provide both description and deadline date");
            return;
        }
        
        taskList.addDeadline(description, by);
        System.out.println(" Got itz. I've added this task:");
        System.out.println("   " + taskList.getTask(taskList.getTaskCount()));
        System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
    }
    
    private static void handleEvent(TaskList taskList, String argument) {
        if (argument.isEmpty()) {
            System.out.println(" OIIII use the format: event <description> /from <start> /to <end>");
            return;
        }
        
        int fromIndex = argument.indexOf("/from ");
        int toIndex = argument.indexOf("/to ");
        
        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
            System.out.println(" OIIII use the format: event <description> /from <start> /to <end>");
            return;
        }
        
        String description = argument.substring(0, fromIndex).trim();
        String from = argument.substring(fromIndex + 6, toIndex).trim();
        String to = argument.substring(toIndex + 4).trim();
        
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            System.out.println(" OIIII provide description, start time, and end time");
            return;
        }
        
        taskList.addEvent(description, from, to);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + taskList.getTask(taskList.getTaskCount()));
        System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
    }
}
