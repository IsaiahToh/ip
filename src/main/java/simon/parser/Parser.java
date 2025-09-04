package simon.parser;

import simon.command.*;

import simon.exceptions.SimonException;

import simon.task.Deadline;
import simon.task.Event;
import simon.task.Task;
import simon.task.Todo;

/**
 * Handles the parsing of user input commands and file lines into Task objects.
 */
public class Parser {
    /**
     * Parses user input string and returns corresponding Command object.
     *
     * @param input User input string.
     * @return Corresponding Command object.
     * @throws SimonException.EmptyTaskException      If the command is missing required arguments.
     * @throws SimonException.UnknownCommandException If the command is not recognized.
     */
    public static Command parse(String input) throws SimonException.EmptyTaskException,
            SimonException.UnknownCommandException {
        final String ERROR_MARK = " Invalid mark command. Enter an integer after \"mark \".";
        final String ERROR_UNMARK = " Invalid unmark command. Enter an integer after \"unmark \".";
        final String ERROR_DELETE = " The index of the task to delete cannot be empty. Follow the format:"
                + " delete <task index>.";
        final String ERROR_ON = " Invalid date for on command. Follow the format: on <yyyy-MM-dd>.";
        final String ERROR_FIND = " Invalid find command. Follow the format: find <keyword>.";
        final String ERROR_TODO = " The description of a todo cannot be empty. Follow the format:"
                + " todo <description>.";
        final String ERROR_DEADLINE = " The description and deadline of a deadline task cannot be empty."
                + " Follow the format: deadline <description> /by <due date>.\n"
                + " Dates can follow the formats:\n"
                + " d/M/yyyy HHmm,\n"
                + " d/M/yyyy,\n"
                + " yyyy-MM-dd HHmm,\n"
                + " yyyy-MM-dd\n";
        final String ERROR_EVENT = " The description, start, and end of an event cannot be empty."
                + " Follow the format: event <description> /from <start date> /to <end date>.\n"
                + " Dates can follow the formats:\n"
                + " d/M/yyyy HHmm,\n"
                + " d/M/yyyy,\n"
                + " yyyy-MM-dd HHmm,\n"
                + " yyyy-MM-dd\n";
        final String ERROR_UNKNOWN = " Sorry, not trained for that. Use\n"
                + " 'todo <description>',\n"
                + " 'deadline <description> /by <due date>',\n"
                + " and 'event <description> /from <start date> /to <end date>'\n"
                + " to add a task :)";

        String[] words = input.trim().split(" ", 2);
        String command = words[0];
        switch (command) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "mark":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_MARK);
            }
            try {
                int markIdx = Integer.parseInt(words[1]) - 1;
                return new MarkCommand(markIdx);
            } catch (NumberFormatException e) {
                throw new SimonException.EmptyTaskException(ERROR_MARK);
            }
        case "unmark":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_UNMARK);
            }
            try {
                int unmarkIdx = Integer.parseInt(words[1]) - 1;
                return new UnmarkCommand(unmarkIdx);
            } catch (NumberFormatException e) {
                throw new SimonException.EmptyTaskException(ERROR_UNMARK);
            }
        case "delete":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_DELETE);
            }
            try {
                int delIdx = Integer.parseInt(words[1]) - 1;
                return new DeleteCommand(delIdx);
            } catch (NumberFormatException e) {
                throw new SimonException.EmptyTaskException(ERROR_DELETE);
            }
        case "on":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_ON);
            }
            return new OnCommand(words[1].trim());
        case "find":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_FIND);
            }
            return new FindCommand(words[1].trim());
        case "todo":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_TODO);
            }
            return new AddCommand(new Todo(words[1].trim()));
        case "deadline":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_DEADLINE);
            }
            String[] deadlineParts = words[1].split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()
                    || deadlineParts[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_DEADLINE);
            }
            return new AddCommand(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()));
        case "event":
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_EVENT);
            }
            String[] eventParts = words[1].split(" /from | /to ", 3);
            if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty()
                    || eventParts[2].trim().isEmpty()) {
                throw new SimonException.EmptyTaskException(ERROR_EVENT);
            }
            return new AddCommand(new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()));
        default:
            throw new SimonException.UnknownCommandException(ERROR_UNKNOWN);
        }
    }

    /**
     * Parses a line from the save file and returns the corresponding Task object.
     * Returns null if the the type of task is not recognised.
     *
     * @param line Line from the save file.
     * @return Corresponding Task object.
     */
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

    /**
     * Converts Task object to string representation for saving to file.
     *
     * @param task Task object to convert.
     * @return String representation of the task for file storage.
     */
    public static String taskToFileString(Task task) {
        String type = null;
        String done = task.isDone() ? "1" : "0";
        if (task instanceof Todo) {
            type = "T";
            return type + " | " + done + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            type = "D";
            return type + " | " + done + " | " + task.getDescription() + " | " + ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            type = "E";
            return type + " | " + done + " | " + task.getDescription() + " | " + ((Event) task).getStart() + " | "
                    + ((Event) task).getEnd();
        }
        return "";
    }
}