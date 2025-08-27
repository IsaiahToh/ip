import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return tasks;
        }
        Scanner fs = new Scanner(f);
        while (fs.hasNextLine()) {
            String line = fs.nextLine();
            Task task = Parser.parseTaskFromFile(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        fs.close();
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        File dir = new File(filePath).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileWriter writer = new FileWriter(filePath);
        for (Task task : tasks) {
            writer.write(Parser.taskToFileString(task) + "\n");
        }
        writer.close();
    }
}