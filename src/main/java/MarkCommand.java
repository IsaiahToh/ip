import java.util.ArrayList;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task t = tasks.get(index);
        t.markAsDone();
        storage.save(new ArrayList<>(tasks.getAll()));
        ui.showMarkTask(t);
    }
}