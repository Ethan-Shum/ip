import java.util.Scanner;

public class Kiko {
    public static void main(String[] args) {
        String greeting = " Hello! I'm Kiko the bunny\n"
                + " What can I do for you?";
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
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + task);
                    } else {
                        System.out.println(" Invalid task number. Please enter a number between 1 and " + taskList.getTaskCount());
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter a valid task number after 'mark'");
                }
            } else if (input.toLowerCase().startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(7).trim());
                    if (taskList.unmarkTask(taskNumber)) {
                        Task task = taskList.getTask(taskNumber);
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + task);
                    } else {
                        System.out.println(" Invalid task number. Please enter a number between 1 and " + taskList.getTaskCount());
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter a valid task number after 'unmark'");
                }
            } else if (!input.equalsIgnoreCase("bye")) {
                taskList.addTask(input);
                System.out.println(" added: " + input);
            }
        } while (!input.equalsIgnoreCase("bye"));

        String farewell = " Bye. Hope to see you again soon!";
        System.out.println(farewell);
    }
}
