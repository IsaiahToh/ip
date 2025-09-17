package simon.command;

import simon.storage.Storage;
import simon.task.TaskList;
import simon.ui.Ui;

public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        setString(ui.showHelp());
    }
}