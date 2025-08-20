import java.util.Scanner;

public class Simon {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        String logo = """
           _____ _                     
          / ___/(_)___ ___  ____  ____ 
          \\__ \\/ / __ `__ \\/ __ \\/ __ \\
         ___/ / / / / / / / /_/ / / / /
        /____/_/_/ /_/ /_/\\____/_/ /_/ 
        """;
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
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                taskCount++;
                System.out.println(
                    "____________________________________________________________\n" 
                    + " Got it. I've added this task:\n"
                    + "   " + tasks[taskCount - 1]
                    + "\n Now you have " + taskCount + " tasks in the list.\n"
                    + "____________________________________________________________"
                );
            } else if (input.startsWith("deadline")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                String description = parts[0];
                String by = parts[1];
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                System.out.println(
                    "____________________________________________________________\n" 
                    + " Got it. I've added this task:\n"
                    + "   " + tasks[taskCount - 1]
                    + "\n Now you have " + taskCount + " tasks in the list.\n"
                    + "____________________________________________________________"
                );
            } else if (input.startsWith("event")) {
                String[] parts = input.substring(6).split(" /from | /to ", 3);
                String description = parts[0];
                String start = parts[1];
                String end = parts[2];
                tasks[taskCount] = new Event(description, start, end);
                taskCount++;
                System.out.println(
                    "____________________________________________________________\n" 
                    + " Got it. I've added this task:\n"
                    + "   " + tasks[taskCount - 1]
                    + "\n Now you have " + taskCount + " tasks in the list.\n"
                    + "____________________________________________________________"
                );
            } else {
                System.out.println("""
                    ____________________________________________________________
                     Sorry, not trained for that. Use todo, deadline, and event to add a task :)
                    ____________________________________________________________
                    """);
            }
        }
        scanner.close();
    }
}