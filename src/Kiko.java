import java.util.Scanner;

public class Kiko {
    public static void main(String[] args) {
        String greeting = " Hello! I'm Kiko the bunny\n"
                + " What can I do for you? >.<";
        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        String input;
        
        do {
            input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("list")) {
                Task[] tasks = taskList.getAllTasks();
                if (tasks.length == 0) {
                    System.out.println(" No tasks added yet");
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.length; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i]);
                    }
                }
            } else if (input.toLowerCase().startsWith("mark ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(5).trim());
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
            } else if (input.toLowerCase().startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(7).trim());
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
            } else if (input.toLowerCase().startsWith("delete ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(7).trim());
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
            } else if (input.equalsIgnoreCase("todo") || input.toLowerCase().startsWith("todo ")) {
                // Check if input is exactly "todo" without description
                if (input.equalsIgnoreCase("todo")) {
                    System.out.println(" Oi provide a description for the todo task");
                    continue;
                }
                
                String description = input.substring(5).trim();
                if (description.isEmpty()) {
                    System.out.println(" Oi provide a description for the todo task");
                } else {
                    taskList.addTodo(description);
                    System.out.println(" Got itz. I've added this task:");
                    System.out.println("   " + taskList.getTask(taskList.getTaskCount()));
                    System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
                }
            } else if (input.equalsIgnoreCase("deadline") || input.toLowerCase().startsWith("deadline ")) {
                if (input.equalsIgnoreCase("deadline")) {
                    System.out.println(" OIII use the format: deadline <description> /by <date>");
                    continue;
                }
                
                String rest = input.substring(9).trim();
                int byIndex = rest.indexOf("/by ");
                if (byIndex == -1) {
                    System.out.println(" OIII use the format: deadline <description> /by <date>");
                } else {
                    String description = rest.substring(0, byIndex).trim();
                    String by = rest.substring(byIndex + 4).trim();
                    if (description.isEmpty() || by.isEmpty()) {
                        System.out.println(" OIIIIII provide both description and deadline date");
                    } else {
                        taskList.addDeadline(description, by);
                        System.out.println(" Got itz. I've added this task:");
                        System.out.println("   " + taskList.getTask(taskList.getTaskCount()));
                        System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
                    }
                }
            } else if (input.equalsIgnoreCase("event") || input.toLowerCase().startsWith("event ")) {
                // Check if input is exactly "event" without description
                if (input.equalsIgnoreCase("event")) {
                    System.out.println(" OIIII use the format: event <description> /from <start> /to <end>");
                    continue;
                }
                
                String rest = input.substring(6).trim();
                int fromIndex = rest.indexOf("/from ");
                int toIndex = rest.indexOf("/to ");
                
                if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
                    System.out.println(" OIIII use the format: event <description> /from <start> /to <end>");
                } else {
                    String description = rest.substring(0, fromIndex).trim();
                    String from = rest.substring(fromIndex + 6, toIndex).trim();
                    String to = rest.substring(toIndex + 4).trim();
                    
                    if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        System.out.println(" OIIII provide description, start time, and end time");
                    } else {
                        taskList.addEvent(description, from, to);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + taskList.getTask(taskList.getTaskCount()));
                        System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
                    }
                }
            } else if (input.equalsIgnoreCase("bye")) {
            } else {
                System.out.println(" Walao i dunno what that means ;<<");
            }
        } while (!input.equalsIgnoreCase("bye"));

        String farewell = " Bye. Hope to see you again soon! Not rlly.";
        System.out.println(farewell);
    }
}
