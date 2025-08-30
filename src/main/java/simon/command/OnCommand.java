package simon.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import simon.storage.Storage;
import simon.task.Deadline;
import simon.task.Event;
import simon.task.Task;
import simon.task.TaskList;
import simon.ui.Ui;

public class OnCommand extends Command {
    private final String dateStr;

    public OnCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            LocalDate queryDate = LocalDate.parse(dateStr);
            ui.showLine();
            System.out.println(" Events and deadlines on "
                    + queryDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");
            boolean found = false;
            for (Task t : tasks.getAll()) {
                if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    if (d.getByDateTime() != null && d.getByDateTime().toLocalDate().equals(queryDate)) {
                        System.out.println(" " + t);
                        found = true;
                    }
                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    if ((e.getStartDateTime() != null && e.getStartDateTime().toLocalDate().equals(queryDate))
                            || (e.getEndDateTime() != null && e.getEndDateTime().toLocalDate().equals(queryDate))) {
                        System.out.println(" " + t);
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println(" No events or deadlines found on this date.");
            }
        } catch (DateTimeParseException e) {
            ui.showError(
                    " Invalid date for on command. Follow the format: on <yyyy-MM-dd>."
            );
        }
    }
}