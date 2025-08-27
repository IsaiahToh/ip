import java.util.ArrayList;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task removed = tasks.remove(index);
        storage.save(new ArrayList<>(tasks.getAll()));
        ui.showDeleteTask(removed, tasks.size());
    }
}