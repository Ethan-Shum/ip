import java.util.Scanner;

public class Kiko {
    public static void main(String[] args) {
        String greeting = "____________________________________________________________\n"
                + " Hello! I'm Kiko the bunny\n"
                + " What can I do for you?\n"
                + "____________________________________________________________";
        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in);
        String input;
        
        do {
            input = scanner.nextLine();
            System.out.println("____________________________________________________________");
            System.out.println(" " + input);
            System.out.println("____________________________________________________________");
        } while (!input.equalsIgnoreCase("bye"));

        String farewell = " Bye. Hope to see you again soon!\n"
                + "____________________________________________________________";
        System.out.println(farewell);
    }
}
