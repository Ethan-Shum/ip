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
                String[] tasks = taskList.getAllTasks();
                if (tasks.length == 0) {
                    System.out.println(" No tasks added yet");
                } else {
                    for (int i = 0; i < tasks.length; i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i]);
                    }
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
