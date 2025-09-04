package simon.command;

import java.util.ArrayList;

import simon.storage.Storage;
import simon.task.Task;
import simon.task.TaskList;
import simon.ui.Ui;

public class FindCommand extends Command{
    private final String taskName;
    private final ArrayList<Task> matchingTasks = new ArrayList<Task>();

    public FindCommand(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        for (Task t : tasks.getTasks()) {
            String[] words = t.getDescription().trim().split(" ");
            for (String word : words) {
                if (word.toLowerCase().contains(taskName.toLowerCase())) {
                    matchingTasks.add(t);
                    break;
                }
            }
        }
        setString(ui.showFindTasks(new TaskList(matchingTasks)));
    }
}
