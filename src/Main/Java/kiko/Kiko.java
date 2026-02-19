package kiko;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import kiko.command.Command;
import kiko.task.Task;
import kiko.tasklist.TaskList;
import kiko.storage.Storage;
import kiko.parser.Parser;
import kiko.history.History;

/**
 * Kiko is a task management chatbot that allows users to manage their tasks.
 * Supports adding todos, deadlines, and events, marking tasks as done/not done,
 * listing tasks, and deleting tasks.
 * The chatbot continues until the user says "bye".
 */
public class Kiko {
    
    private Parser parser;
    private TaskList taskList;
    private History history;
    
    public static final String HELP_MESSAGE = "Try these commands:\n"
                 + "  list - show all tasks\n"
                 + "  todo [description] - add a todo\n"
                 + "  deadline [description] /by [date] - add a deadline\n"
                 + "  event [description] /from [date] /to [date] - add an event\n"
                 + "  mark [number] - mark task as done\n"
                 + "  unmark [number] - mark task as not done\n"
                 + "  delete [number] - delete a task\n"
                 + "  find [keyword] - find tasks by keyword\n"
                 + "  undo - undo the last command\n"
                 + "  bye - exit";

    /**
     * Constructor for Kiko.
     * Initializes the parser and loads tasks from storage.
     */
    public Kiko() {
        this.parser = new Parser();
        ArrayList<Task> loadedTasks = Storage.loadTasks();
        this.taskList = new TaskList(loadedTasks);
        this.history = new History();
        // Save initial state
        this.history.saveState(this.taskList);
    }
    
    /**
     * Generates a response to user input for GUI interaction.
     * This method processes the input and returns a string response
     * suitable for display in the GUI.
     *
     * @param input The user's input command
     * @return A string response to be displayed in the GUI
     */
    
    /**
     * Generates a response to user input for GUI interaction.
     * This method processes the input and returns a string response
     * suitable for display in the GUI.
     *
     * @param input The user's input command
     * @return A string response to be displayed in the GUI
     */
    public String getResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a command!";
        }
        
        Command command = Command.fromInput(input);
        assert command != null : "Command should not be null after parsing";
        
        String argument = command.getArgument(input.toLowerCase());
        
        switch (command) {
        case LIST:
            assert taskList != null : "TaskList should be initialized";
            return handleListGui();
            
        case MARK:
            history.saveState(taskList);
            return handleMarkGui(argument);
            
        case UNMARK:
            history.saveState(taskList);
            return handleUnmarkGui(argument);
            
        case DELETE:
            history.saveState(taskList);
            return handleDeleteGui(argument);
            
        case TODO:
            history.saveState(taskList);
            return handleTodoGui(argument);
            
        case DEADLINE:
            history.saveState(taskList);
            return handleDeadlineGui(argument);
            
        case EVENT:
            history.saveState(taskList);
            return handleEventGui(argument);
            
        case FIND:
            return handleFindGui(argument);
            
        case UNDO:
            return handleUndoGui();
            
        case BYE:
            return "CLOSE_WINDOW:Goodbye! Hope to see you again soon!";
            
        case UNKNOWN:
        default:
            return "I'm sorry, but I don't know what that means. " + HELP_MESSAGE;
        }
    }
    
    private String handleListGui() {
        Task[] tasks = taskList.getAllTasks();
        if (tasks.length == 0) {
            return "You have no tasks in your list!";
        }
        
        StringBuilder response = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.length; i++) {
            response.append((i + 1)).append(". ").append(tasks[i].toString()).append("\n");
        }
        return response.toString().trim();
    }
    
    private String handleMarkGui(String argument) {
        int taskNumber = parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            return "OI!! Please provide a valid task number to mark!";
        }
        
        if (taskList.markTask(taskNumber)) {
            Task task = taskList.getTask(taskNumber);
            return "Nicee! I've marked this task as done:\n  " + task.toString();
        } else {
            return "Task number " + taskNumber + " does not exist. You have " + taskList.getTaskCount() + " tasks.";
        }
    }
    
    private String handleUnmarkGui(String argument) {
        int taskNumber = parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            return "Heyy!!! Please provide a valid task number to unmark!";
        }
        
        if (taskList.unmarkTask(taskNumber)) {
            Task task = taskList.getTask(taskNumber);
            return "OK, I've marked this task as not done yet:\n  " + task.toString();
        } else {
            return "Task number " + taskNumber + " does not exist. You have " + taskList.getTaskCount() + " tasks.";
        }
    }
    
    private String handleDeleteGui(String argument) {
        int taskNumber = parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            return "HEY! Please provide a valid task number to delete!";
        }
        
        Task deletedTask = taskList.deleteTask(taskNumber);
        if (deletedTask != null) {
            return "Okieee. I've removed this task:\n  " + deletedTask.toString() + "\nNow you have " + taskList.getTaskCount() + " tasks in the list.";
        } else {
            return "Task number " + taskNumber + " does not exist. You have " + taskList.getTaskCount() + " tasks.";
        }
    }
    
    private String handleTodoGui(String argument) {
        if (argument.isEmpty()) {
            return "OIIII!!! The description of a todo cannot be empty!";
        }
        
        taskList.addTodo(argument);
        return "Got it. I've added this task:\n  " + taskList.getTask(taskList.getTaskCount()).toString() + "\nNow you have " + taskList.getTaskCount() + " tasks in the list.";
    }
    
    private String handleDeadlineGui(String argument) {
        if (argument.isEmpty()) {
            return "Please provide a description and deadline! Usage: deadline [description] /by [date]";
        }
        
        String[] parsedArgs = parser.parseDeadlineArgument(argument);
        if (parsedArgs == null) {
            if (!argument.contains("/by ")) {
                return "HEY!!! Please include '/by' followed by the deadline date!";
            } else {
                return "OIII!!!Please provide both description and deadline!";
            }
        }
        
        String description = parsedArgs[0];
        String dateString = parsedArgs[1];
        
        try {
            LocalDateTime dateTime = parser.parseDateTime(dateString);
            taskList.addDeadline(description, dateTime);
            return "Got itz. I've added this task:\n  " + taskList.getTask(taskList.getTaskCount()).toString() + "\nNow you have " + taskList.getTaskCount() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            return "OIII!!! Invalid date format! Please use: dd/MM/yyyy HHmm";
        }
    }
    
    private String handleEventGui(String argument) {
        if (argument.isEmpty()) {
            return "Hey!!! Please provide a description and time range! Usage: event [description] /from [date] /to [date]";
        }
        
        String[] parsedArgs = parser.parseEventArgument(argument);
        if (parsedArgs == null) {
            return "Please include both '/from' and '/to' with dates!";
        }
        
        String description = parsedArgs[0];
        String fromString = parsedArgs[1];
        String toString = parsedArgs[2];
        
        if (description.isEmpty() || fromString.isEmpty() || toString.isEmpty()) {
            return "Please provide description, start time, and end time!";
        }
        
        try {
            LocalDateTime fromDateTime = parser.parseDateTime(fromString);
            LocalDateTime toDateTime = parser.parseDateTime(toString);
            taskList.addEvent(description, fromDateTime, toDateTime);
            return "Got it. I've added this task:\n  " + taskList.getTask(taskList.getTaskCount()).toString() + "\nNow you have " + taskList.getTaskCount() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            return "Invalid date format! Please use: dd/MM/yyyy HHmm";
        }
    }
    
    private String handleUndoGui() {
        if (!history.canUndo()) {
            return "Oh No!! Nothing to undo!";
        }
        
        TaskList previousState = history.undo();
        if (previousState == null) {
            return "Nothing to undo!";
        }
        
        this.taskList = previousState;
        // Also save the restored state to file so it persists
        Storage.saveTasks(this.taskList.getAllTasksArrayList());
        
        return "YAYY!!! Undo successful! Restored previous state.";
    }

    private String handleFindGui(String argument) {
        if (argument.isEmpty()) {
            return "OIII! Please provide a keyword to search for!";
        }
        
        Task[] matchingTasks = taskList.findTasks(argument);
        if (matchingTasks.length == 0) {
            return "Walao!! No matching tasks found for keyword: " + argument;
        }
        
        StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.length; i++) {
            response.append((i + 1)).append(". ").append(matchingTasks[i].toString()).append("\n");
        }
        return response.toString().trim();
    }
}