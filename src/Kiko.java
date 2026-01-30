import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Kiko is a task management chatbot that allows users to manage their tasks.
 * Supports adding todos, deadlines, and events, marking tasks as done/not done,
 * listing tasks, and deleting tasks.
 * The chatbot continues until the user says "bye".
 */
public class Kiko {
    private final Ui ui;
    private final TaskList taskList;
    
    /**
     * Constructs a Kiko chatbot with UI and task list.
     */
    public Kiko() {
        this.ui = new Ui();
        ArrayList<Task> loadedTasks = Storage.loadTasks();
        this.taskList = new TaskList(loadedTasks);
    }
    
    /**
     * Main entry point for the Kiko chatbot application.
     * Displays a greeting, then enters a loop to process user commands.
     * Commands are parsed and delegated to appropriate handler methods.
     * The loop continues until the user enters "bye".
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Kiko kiko = new Kiko();
        kiko.run();
    }
    
    /**
     * Runs the main application loop.
     */
    public void run() {
        ui.showGreeting();
        
        String input;
        do {
            input = ui.readCommand();
            Command command = Command.fromInput(input);
            String argument = command.getArgument(input.toLowerCase());
            
            switch (command) {
                case LIST:
                    handleList();
                    break;
                    
                case MARK:
                    handleMark(argument);
                    break;
                    
                case UNMARK:
                    handleUnmark(argument);
                    break;
                    
                case DELETE:
                    handleDelete(argument);
                    break;
                    
                case TODO:
                    handleTodo(argument);
                    break;
                    
                case DEADLINE:
                    handleDeadline(argument);
                    break;
                    
                case EVENT:
                    handleEvent(argument);
                    break;
                    
                case BYE:
                    break;
                    
                case UNKNOWN:
                default:
                    ui.showMessage(" Walao i dunno what that means ;<<");
                    break;
            }
        } while (!input.equalsIgnoreCase("bye"));

        ui.showFarewell();
    }
    
    private void handleList() {
        Task[] tasks = taskList.getAllTasks();
        ui.showTaskList(tasks);
    }
    
    private void handleMark(String argument) {
        int taskNumber = Parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            ui.showMessage(" Oi enter a valid task number after 'mark'");
            return;
        }
        
        if (taskList.markTask(taskNumber)) {
            Task task = taskList.getTask(taskNumber);
            ui.showTask(" Yayyy good job! Task is donee:", task);
        } else {
            ui.showMessage(" I dunno this task number. Please enter a number between 1 and " + taskList.getTaskCount());
        }
    }
    
    private void handleUnmark(String argument) {
        int taskNumber = Parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            ui.showMessage(" Oi enter a valid task number after 'unmark'");
            return;
        }
        
        if (taskList.unmarkTask(taskNumber)) {
            Task task = taskList.getTask(taskNumber);
            ui.showTask(" OK, I've marked this task as not done yet:", task);
        } else {
            ui.showMessage(" I dunno this task number. Please enter a number between 1 and " + taskList.getTaskCount());
        }
    }
    
    private void handleDelete(String argument) {
        int taskNumber = Parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            ui.showMessage(" Oi enter a valid task number after 'delete'");
            return;
        }
        
        Task deletedTask = taskList.deleteTask(taskNumber);
        if (deletedTask != null) {
            ui.showMessage(" Noted. I've removed this task:");
            ui.showTask("", deletedTask);
            ui.showTaskCount(taskList.getTaskCount());
        } else {
            ui.showMessage(" I dunno this task number. Please enter a number between 1 and " + taskList.getTaskCount());
        }
    }
    
    private void handleTodo(String argument) {
        if (argument.isEmpty()) {
            ui.showMessage(" Oi provide a description for the todo task");
            return;
        }
        
        taskList.addTodo(argument);
        ui.showMessage(" Got itz. I've added this task:");
        ui.showTask("", taskList.getTask(taskList.getTaskCount()));
        ui.showTaskCount(taskList.getTaskCount());
    }
    
    private void handleDeadline(String argument) {
        if (argument.isEmpty()) {
            ui.showDeadlineHelp();
            return;
        }
        
        String[] parsedArgs = Parser.parseDeadlineArgument(argument);
        if (parsedArgs == null) {
            ui.showDeadlineHelp();
            return;
        }
        
        String description = parsedArgs[0];
        String dateString = parsedArgs[1];
        
        try {
            LocalDateTime dateTime = Parser.parseDateTime(dateString);
            taskList.addDeadline(description, dateTime);
            ui.showMessage(" Got itz. I've added this task:");
            ui.showTask("", taskList.getTask(taskList.getTaskCount()));
            ui.showTaskCount(taskList.getTaskCount());
        } catch (DateTimeParseException e) {
            ui.showDateFormatHelp();
        }
    }
    
    private void handleEvent(String argument) {
        if (argument.isEmpty()) {
            ui.showEventHelp();
            return;
        }
        
        String[] parsedArgs = Parser.parseEventArgument(argument);
        if (parsedArgs == null) {
            ui.showEventHelp();
            return;
        }
        
        String description = parsedArgs[0];
        String fromString = parsedArgs[1];
        String toString = parsedArgs[2];
        
        try {
            LocalDateTime fromDateTime = Parser.parseDateTime(fromString);
            LocalDateTime toDateTime = Parser.parseDateTime(toString);
            taskList.addEvent(description, fromDateTime, toDateTime);
            ui.showMessage(" Got it. I've added this task:");
            ui.showTask("", taskList.getTask(taskList.getTaskCount()));
            ui.showTaskCount(taskList.getTaskCount());
        } catch (DateTimeParseException e) {
            ui.showDateFormatHelp();
        }
    }
}