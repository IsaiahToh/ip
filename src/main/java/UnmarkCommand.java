import java.util.ArrayList;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task t = tasks.get(index);
        t.markAsNotDone();
        storage.save(new ArrayList<>(tasks.getAll()));
        ui.showUnmarkTask(t);
    }
}