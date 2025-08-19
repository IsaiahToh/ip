import java.util.Scanner;

public class Simon {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskCount = 0;

        String logo = "   _____ _                     \n"
                + "  / ___/(_)___ ___  ____  ____ \n"
                + "  \\__ \\/ / __ `__ \\/ __ \\/ __ \\\n"
                + " ___/ / / / / / / / /_/ / / / /\n"
                + "/____/_/_/ /_/ /_/\\____/_/ /_/ \n";
        System.out.println(
            "____________________________________________________________\n" 
            + logo 
            + "\nHello! I'm Simon\n"
            + "What can I do for you?\n"
            + "____________________________________________________________\n"
        );

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("""
                    ____________________________________________________________
                     Bye. Hope to see you again soon!
                    ____________________________________________________________
                    """);
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println(
                    "____________________________________________________________\n" 
                    + " added: "
                    + input
                    + "\n____________________________________________________________"
                );
            }
        }
        scanner.close();
    }
}