package kiko;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import kiko.command.Command;
import kiko.task.Task;
import kiko.tasklist.TaskList;
import kiko.storage.Storage;
import kiko.ui.Ui;
import kiko.parser.Parser;

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
        Ui ui = new Ui();
        Parser parser = new Parser();
        
        ui.showGreeting();

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
                    handleList(taskList, ui);
                    break;
                    
                case MARK:
                    handleMark(taskList, argument, ui, parser);
                    break;
                    
                case UNMARK:
                    handleUnmark(taskList, argument, ui, parser);
                    break;
                    
                case DELETE:
                    handleDelete(taskList, argument, ui, parser);
                    break;
                    
                case TODO:
                    handleTodo(taskList, argument, ui);
                    break;
                    
                case DEADLINE:
                    handleDeadline(taskList, argument, ui, parser);
                    break;
                    
                case EVENT:
                    handleEvent(taskList, argument, ui, parser);
                    break;
                    
                case FIND:
                    handleFind(taskList, argument, ui);
                    break;
                    
                case BYE:
                    break;
                    
                case UNKNOWN:
                default:
                    ui.showUnknownCommand();
                    break;
            }
        } while (!input.equalsIgnoreCase("bye"));

        ui.showFarewell();
    }
    
    private static void handleList(TaskList taskList, Ui ui) {
        Task[] tasks = taskList.getAllTasks();
        String[] taskStrings = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            taskStrings[i] = tasks[i].toString();
        }
        ui.showTaskList(taskStrings);
    }
    
    private static void handleMark(TaskList taskList, String argument, Ui ui, Parser parser) {
        int taskNumber = parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            ui.showInvalidNumberFormat("mark");
            return;
        }
        
        if (taskList.markTask(taskNumber)) {
            Task task = taskList.getTask(taskNumber);
            ui.showTaskMarked(task.toString());
        } else {
            ui.showInvalidTaskNumber(taskList.getTaskCount());
        }
    }
    
    private static void handleUnmark(TaskList taskList, String argument, Ui ui, Parser parser) {
        int taskNumber = parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            ui.showInvalidNumberFormat("unmark");
            return;
        }
        
        if (taskList.unmarkTask(taskNumber)) {
            Task task = taskList.getTask(taskNumber);
            ui.showTaskUnmarked(task.toString());
        } else {
            ui.showInvalidTaskNumber(taskList.getTaskCount());
        }
    }
    
    private static void handleDelete(TaskList taskList, String argument, Ui ui, Parser parser) {
        int taskNumber = parser.parseTaskNumber(argument);
        if (taskNumber == -1) {
            ui.showInvalidNumberFormat("delete");
            return;
        }
        
        Task deletedTask = taskList.deleteTask(taskNumber);
        if (deletedTask != null) {
            ui.showTaskDeleted(deletedTask.toString(), taskList.getTaskCount());
        } else {
            ui.showInvalidTaskNumber(taskList.getTaskCount());
        }
    }
    
    private static void handleTodo(TaskList taskList, String argument, Ui ui) {
        if (argument.isEmpty()) {
            ui.showEmptyTodoDescription();
            return;
        }
        
        taskList.addTodo(argument);
        ui.showTodoAdded(taskList.getTask(taskList.getTaskCount()).toString(), taskList.getTaskCount());
    }
    
    private static void handleDeadline(TaskList taskList, String argument, Ui ui, Parser parser) {
        if (argument.isEmpty()) {
            ui.showDeadlineUsage();
            return;
        }
        
        String[] parsedArgs = parser.parseDeadlineArgument(argument);
        if (parsedArgs == null) {
            if (!argument.contains("/by ")) {
                ui.showDeadlineMissingBy();
            } else {
                ui.showDeadlineMissingFields();
            }
            return;
        }
        
        String description = parsedArgs[0];
        String dateString = parsedArgs[1];
        
        try {
            LocalDateTime dateTime = parser.parseDateTime(dateString);
            taskList.addDeadline(description, dateTime);
            ui.showDeadlineAdded(taskList.getTask(taskList.getTaskCount()).toString(), taskList.getTaskCount());
        } catch (DateTimeParseException e) {
            ui.showInvalidDateFormat();
        }
    }
    
    private static void handleEvent(TaskList taskList, String argument, Ui ui, Parser parser) {
        if (argument.isEmpty()) {
            ui.showEventUsage();
            return;
        }
        
        String[] parsedArgs = parser.parseEventArgument(argument);
        if (parsedArgs == null) {
            ui.showEventMissingTimeMarkers();
            return;
        }
        
        String description = parsedArgs[0];
        String fromString = parsedArgs[1];
        String toString = parsedArgs[2];
        
        if (description.isEmpty() || fromString.isEmpty() || toString.isEmpty()) {
            ui.showEventMissingFields();
            return;
        }
        
        try {
            LocalDateTime fromDateTime = parser.parseDateTime(fromString);
            LocalDateTime toDateTime = parser.parseDateTime(toString);
            taskList.addEvent(description, fromDateTime, toDateTime);
            ui.showEventAdded(taskList.getTask(taskList.getTaskCount()).toString(), taskList.getTaskCount());
        } catch (DateTimeParseException e) {
            ui.showInvalidDateFormat();
        }
    }
    
    private static void handleFind(TaskList taskList, String argument, Ui ui) {
        if (argument.isEmpty()) {
            ui.showEmptyFindKeyword();
            return;
        }
        
        Task[] matchingTasks = taskList.findTasks(argument);
        String[] taskStrings = new String[matchingTasks.length];
        for (int i = 0; i < matchingTasks.length; i++) {
            taskStrings[i] = matchingTasks[i].toString();
        }
        ui.showFindResults(taskStrings, argument);
    }
}