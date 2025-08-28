package simon;
import simon.command.Command;
import simon.exceptions.SimonExceptions;
import simon.parser.Parser;
import simon.storage.Storage;
import simon.task.TaskList;
import simon.ui.Ui;

public class Simon {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Simon(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
    String logo =
        "   _____ _\n"
          + "  / ___/(_)___ ___  ____  ____\n"
          + "  \\__ \\/ / __ `__ \\/ __ \\/ __ \\\n"
          + " ___/ / / / / / / /_/ / / / /\n"
          + "/____/_/_/ /_/ /_/\\____/_/ /_/\n";
        ui.showWelcome(logo);
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (SimonExceptions.EmptyTaskException | SimonExceptions.UnknownCommandException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("An error occurred: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Simon("./data/simon.txt").run();
    }
}