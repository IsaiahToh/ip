import java.util.ArrayList;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        tasks.add(task);
        storage.save(new ArrayList<>(tasks.getAll()));
        ui.showAddTask(task, tasks.size());
    }
}