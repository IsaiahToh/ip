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
        String logo = """
           _____ _
          / ___/(_)___ ___  ____  ____
          \\__ \\/ / __ `__ \\/ __ \\/ __ \\
         ___/ / / / / / / / /_/ / / / /
        /____/_/_/ /_/ /_/\\____/_/ /_/
        """;
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