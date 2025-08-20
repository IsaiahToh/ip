import java.util.Scanner;
import java.util.ArrayList;

public class Simon {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
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
            + "\n Hello! I'm Simon\n"
            + " What can I do for you?\n"
            + "____________________________________________________________\n"
        );

        while (true) {
            try {
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
                        System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("mark ")) {
                    try {
                        int taskIdx = Integer.parseInt(input.substring(5)) - 1;
                        if (taskIdx < 0 || taskIdx >= taskCount) {
                            throw new IndexOutOfBoundsException();
                        }
                        tasks.get(taskIdx).markAsDone();
                        System.out.println(
                            "____________________________________________________________\n"
                            + " Nice! I've marked this task as done:\n"
                            + "   " + tasks.get(taskIdx)
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
                        tasks.get(taskIdx).markAsNotDone();
                        System.out.println(
                            "____________________________________________________________\n"
                            + " OK, I've marked this task as not done yet:\n"
                            + "   " + tasks.get(taskIdx)
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
                             Invalid mark command. Enter an integer after "unmark ".
                            ____________________________________________________________
                            """);
                    }
                } else if (input.startsWith("todo")) {
                    String description = input.length() > 4 ? input.substring(4).trim() : "";
                    if (description.isEmpty()) {
                        throw new SimonExceptions.EmptyTaskException(" The description of a todo cannot be empty. Follow the format: todo <description>.");
                    }
                    tasks.add(new Todo(description));
                    taskCount++;
                    System.out.println(
                        "____________________________________________________________\n" 
                        + " Got it. I've added this task:\n"
                        + "   " + tasks.get(taskCount - 1)
                        + "\n Now you have " + taskCount + " tasks in the list.\n"
                        + "____________________________________________________________"
                    );
                } else if (input.startsWith("deadline")) {
                    String rest = input.length() > 8 ? input.substring(8).trim() : "";
                    String[] parts = rest.split(" /by ", 2);
                    String description = parts[0].trim();
                    String by = (parts.length > 1) ? parts[1] : "";
                    if (description.isEmpty() || by.isEmpty()) {
                        throw new SimonExceptions.EmptyTaskException(" The description and deadline of a deadline task cannot be empty. Follow the format: deadline <description> /by <due date>.");
                    }
                    tasks.add(new Deadline(description, by));
                    taskCount++;
                    System.out.println(
                        "____________________________________________________________\n" 
                        + " Got it. I've added this task:\n"
                        + "   " + tasks.get(taskCount - 1)
                        + "\n Now you have " + taskCount + " tasks in the list.\n"
                        + "____________________________________________________________"
                    );
                } else if (input.startsWith("event")) {
                    String rest = input.length() > 5 ? input.substring(5).trim() : "";
                    String[] parts = rest.split(" /from | /to ", 3);
                    String description = parts.length > 0 ? parts[0].trim() : "";
                    String start = parts.length > 1 ? parts[1].trim() : "";
                    String end = parts.length > 2 ? parts[2].trim() : "";
                    if (description.isEmpty() || start.isEmpty() || end.isEmpty()) {
                        throw new SimonExceptions.EmptyTaskException(" The description, start, and end of an event cannot be empty. Follow the format: event <description> /from <start date> /to <end date>.");
                    }
                    tasks.add(new Event(description, start, end));
                    taskCount++;
                    System.out.println(
                        "____________________________________________________________\n" 
                        + " Got it. I've added this task:\n"
                        + "   " + tasks.get(taskCount - 1)
                        + "\n Now you have " + taskCount + " tasks in the list.\n"
                        + "____________________________________________________________"
                    );
                } else if (input.startsWith("delete")) {
                    try {
                        String arg = input.length() > 6 ? input.substring(6).trim() : "";
                        if (arg.isEmpty()) {
                            throw new SimonExceptions.EmptyTaskException(" The index of the task to delete cannot be empty. Follow the format: delete <task index>.");
                        }
                        int taskIdx = Integer.parseInt(arg) - 1;
                        if (taskIdx < 0 || taskIdx >= taskCount) {
                            throw new IndexOutOfBoundsException();
                        }
                        Task removedTask = tasks.remove(taskIdx);
                        taskCount--;
                        System.out.println(
                            "____________________________________________________________\n"
                            + " Noted. I've removed this task:\n"
                            + "   " + removedTask
                            + "\n Now you have " + taskCount + " tasks in the list.\n"
                            + "____________________________________________________________"
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
                             Invalid delete command. Enter an integer after "delete ".
                            ____________________________________________________________
                            """);
                    }
                } else {
                    throw new SimonExceptions.UnknownCommandException(" Sorry, not trained for that. Use 'todo <description>', 'deadline <description> /by <due date>', and 'event <description> /from <start date> /to <end date>' to add a task :)");
                }
            } catch (SimonExceptions.EmptyTaskException e) {
                System.out.println(
                    "____________________________________________________________\n"
                    + e.getMessage()
                    + "\n____________________________________________________________"
                );
            } catch (SimonExceptions.UnknownCommandException e) {
                System.out.println(
                    "____________________________________________________________\n"
                    + e.getMessage()
                    + "\n____________________________________________________________"
                );
            }
        }
        scanner.close();
    }
}