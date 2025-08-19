import java.util.Scanner;

public class Simon {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
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
                System.out.println("____________________________________________________________\n Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark ")) {
                try {
                    int taskIdx = Integer.parseInt(input.substring(5)) - 1;
                    if (taskIdx < 0 || taskIdx >= taskCount) {
                        throw new IndexOutOfBoundsException();
                    }
                    tasks[taskIdx].markAsDone();
                    System.out.println(
                        "____________________________________________________________\n"
                        + " Nice! I've marked this task as done:\n"
                        + "   " + tasks[taskIdx]
                        + "\n____________________________________________________________"
                    );
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("""
                        ____________________________________________________________
                         There is no task at this index.
                        ____________________________________________________________
                        """);
                } catch (NumberFormatException e) {
                    System.out.println("""
                        ____________________________________________________________
                         Invalid mark command. Enter an integer after "mark ".
                        ____________________________________________________________
                        """);
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int taskIdx = Integer.parseInt(input.substring(7)) - 1;
                    if (taskIdx < 0 || taskIdx >= taskCount) {
                        throw new IndexOutOfBoundsException();
                    }
                    tasks[taskIdx].markAsNotDone();
                    System.out.println(
                        "____________________________________________________________\n"
                        + " OK, I've marked this task as not done yet:\n"
                        + "   " + tasks[taskIdx]
                        + "\n____________________________________________________________"
                    );
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("""
                        ____________________________________________________________
                         There is no task at this index.
                        ____________________________________________________________
                        """);
                } catch (NumberFormatException e) {
                    System.out.println("""
                        ____________________________________________________________
                         Invalid mark command. Enter an integer after "mark ".
                        ____________________________________________________________
                        """);
                }
            } else {
                tasks[taskCount] = new Task(input);
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