package simon.command;

import simon.storage.Storage;
import simon.task.TaskList;
import simon.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        setString(ui.showGoodbye());
    }

    @Override
    public boolean isExit() {
        return true;
    }
}