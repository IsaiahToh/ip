package simon.command;

import java.util.ArrayList;

import simon.storage.Storage;
import simon.task.Task;
import simon.task.TaskList;
import simon.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task removed = tasks.remove(index);
        storage.save(new ArrayList<>(tasks.getTasks()));
        setString(ui.showDeleteTask(removed, tasks.size()));
    }
}