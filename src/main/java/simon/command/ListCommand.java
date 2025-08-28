import simon.storage.Storage;
import simon.task.TaskList;
import simon.ui.Ui;

package simon.command;
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.getAll());
    }
}