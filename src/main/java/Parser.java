public class Parser {
    public static Command parse(String input) throws SimonExceptions.EmptyTaskException, SimonExceptions.UnknownCommandException {
        final String MARK_ERROR = " Invalid mark command. Enter an integer after \"mark \".";
        final String UNMARK_ERROR = " Invalid unmark command. Enter an integer after \"unmark \".";
        final String DELETE_ERROR = " The index of the task to delete cannot be empty. Follow the format: delete <task index>.";
        final String ON_ERROR = " Invalid date for on command. Follow the format: on <yyyy-MM-dd>.";
        final String TODO_ERROR = " The description of a todo cannot be empty. Follow the format: todo <description>.";
        final String DEADLINE_ERROR = """
             The description and deadline of a deadline task cannot be empty. Follow the format: deadline <description> /by <due date>.
             Dates can follow the formats:
             d/M/yyyy HHmm,
             d/M/yyyy,
             yyyy-MM-dd HHmm,
             yyyy-MM-dd
            """;
        final String EVENT_ERROR = """
             The description, start, and end of an event cannot be empty. Follow the format: event <description> /from <start date> /to <end date>.
             Dates can follow the formats:
             d/M/yyyy HHmm,
             d/M/yyyy,
             yyyy-MM-dd HHmm,
             yyyy-MM-dd
            """;
        final String UNKNOWN_ERROR =
            " Sorry, not trained for that. Use 'todo <description>', 'deadline <description> /by <due date>', and 'event <description> /from <start date> /to <end date>' to add a task :)";

        String[] words = input.trim().split(" ", 2);
        String command = words[0];
        switch (command) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(MARK_ERROR);
                }
                try {
                    int markIdx = Integer.parseInt(words[1]) - 1;
                    return new MarkCommand(markIdx);
                } catch (NumberFormatException e) {
                    throw new SimonExceptions.EmptyTaskException(MARK_ERROR);
                }
            case "unmark":
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(UNMARK_ERROR);
                }
                try {
                    int unmarkIdx = Integer.parseInt(words[1]) - 1;
                    return new UnmarkCommand(unmarkIdx);
                } catch (NumberFormatException e) {
                    throw new SimonExceptions.EmptyTaskException(UNMARK_ERROR);
                }
            case "delete":
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(DELETE_ERROR);
                }
                try {
                    int delIdx = Integer.parseInt(words[1]) - 1;
                    return new DeleteCommand(delIdx);
                } catch (NumberFormatException e) {
                    throw new SimonExceptions.EmptyTaskException(DELETE_ERROR);
                }
            case "on":
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(ON_ERROR);
                }
                return new OnCommand(words[1].trim());
            case "todo":
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(TODO_ERROR);
                }
                return new AddCommand(new Todo(words[1].trim()));
            case "deadline":
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(DEADLINE_ERROR);
                }
                String[] deadlineParts = words[1].split(" /by ", 2);
                if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(DEADLINE_ERROR);
                }
                return new AddCommand(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()));
            case "event":
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(EVENT_ERROR);
                }
                String[] eventParts = words[1].split(" /from | /to ", 3);
                if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
                    throw new SimonExceptions.EmptyTaskException(EVENT_ERROR);
                }
                return new AddCommand(new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()));
            default:
                throw new SimonExceptions.UnknownCommandException(UNKNOWN_ERROR);
        }
    }

    public static Task parseTaskFromFile(String line) {
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

    public static String taskToFileString(Task task) {
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
}