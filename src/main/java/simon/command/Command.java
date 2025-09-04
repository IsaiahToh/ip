package simon.command;

import simon.storage.Storage;
import simon.task.TaskList;
import simon.ui.Ui;

public abstract class Command {
    private String string;
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    public void setString(String s) {
        this.string = s;
    }

    public String getString() {
        return string;
    }

    public boolean isExit() {
        return false;
    }
}