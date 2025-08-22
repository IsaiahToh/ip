import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Simon {
    private static final String FILE_PATH = "./data/simon.txt";
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasks(tasks);

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
                String input = s.nextLine();
                if (input.equals("bye")) {
                    System.out.println("""
                        ____________________________________________________________
                         Bye. Hope to see you again soon!
                        ____________________________________________________________
                        """);
                    break;
                } else if (input.equals("list")) {
                    System.out.println("____________________________________________________________\n Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("mark ")) {
                    try {
                        int taskIdx = Integer.parseInt(input.substring(5)) - 1;
                        if (taskIdx < 0 || taskIdx >= tasks.size()) {
                            throw new IndexOutOfBoundsException();
                        }
                        tasks.get(taskIdx).markAsDone();
                        saveTasks(tasks);
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
                        if (taskIdx < 0 || taskIdx >= tasks.size()) {
                            throw new IndexOutOfBoundsException();
                        }
                        tasks.get(taskIdx).markAsNotDone();
                        saveTasks(tasks);
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
                    saveTasks(tasks);
                    System.out.println(
                        "____________________________________________________________\n" 
                        + " Got it. I've added this task:\n"
                        + "   " + tasks.get(tasks.size() - 1)
                        + "\n Now you have " + tasks.size() + " tasks in the list.\n"
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
                    saveTasks(tasks);
                    System.out.println(
                        "____________________________________________________________\n" 
                        + " Got it. I've added this task:\n"
                        + "   " + tasks.get(tasks.size() - 1)
                        + "\n Now you have " + tasks.size() + " tasks in the list.\n"
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
                    saveTasks(tasks);
                    System.out.println(
                        "____________________________________________________________\n" 
                        + " Got it. I've added this task:\n"
                        + "   " + tasks.get(tasks.size() - 1)
                        + "\n Now you have " + tasks.size() + " tasks in the list.\n"
                        + "____________________________________________________________"
                    );
                } else if (input.startsWith("delete")) {
                    try {
                        String arg = input.length() > 6 ? input.substring(6).trim() : "";
                        if (arg.isEmpty()) {
                            throw new SimonExceptions.EmptyTaskException(" The index of the task to delete cannot be empty. Follow the format: delete <task index>.");
                        }
                        int taskIdx = Integer.parseInt(arg) - 1;
                        if (taskIdx < 0 || taskIdx >= tasks.size()) {
                            throw new IndexOutOfBoundsException();
                        }
                        Task removedTask = tasks.remove(taskIdx);
                        saveTasks(tasks);
                        System.out.println(
                            "____________________________________________________________\n"
                            + " Noted. I've removed this task:\n"
                            + "   " + removedTask
                            + "\n Now you have " + tasks.size() + " tasks in the list.\n"
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
        s.close();
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File dir = new File("./data"); 
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(taskToFileString(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(
                "____________________________________________________________\n"
                + " An error occurred while saving tasks: " + e.getMessage()
                + "\n____________________________________________________________"
            );
        }
    }

    private static void loadTasks(ArrayList<Task> tasks) {
        File f = new File(FILE_PATH);
        if (!f.exists()) {
            return;
        }
        try {
            Scanner fs = new Scanner(f);
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            fs.close();
        } catch (IOException e) {
            System.out.println(
                "____________________________________________________________\n"
                + " An error occurred while loading tasks: " + e.getMessage()
                + "\n____________________________________________________________"
            );
        }
    }

    private static String taskToFileString(Task task) {
        String type = null;
        String done = task.isDone ? "1" : "0";
        if (task instanceof Todo) {
            type = "T";
            return type + " | " + done + " | " + task.description;
        } else if (task instanceof Deadline) {
            type = "D";
            return type + " | " + done + " | " + task.description + " | " + ((Deadline) task).by;
        } else if (task instanceof Event) {
            type = "E";
            return type + " | " + done + " | " + task.description + " | " + ((Event) task).start + " | " + ((Event) task).end;
        }
        return "";
    }

    private static Task parseTaskFromFile(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];
            Task task = null;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    String by = parts.length > 3 ? parts[3] : "";
                    task = new Deadline(description, by);
                    break;
                case "E":
                    String start = parts.length > 3 ? parts[3] : "";
                    String end = parts.length > 4 ? parts[4] : "";
                    task = new Event(description, start, end);
                    break;
                default:
                    return null;
            }
            if (isDone && task != null) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            return null;
        }
    }
}