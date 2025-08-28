package simon.command;

import simon.storage.Storage;
import simon.task.TaskList;
import simon.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;
    public boolean isExit() {
        return false;
    }
}