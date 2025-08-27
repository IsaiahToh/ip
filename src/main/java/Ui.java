import java.util.Scanner;
import java.util.List;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome(String logo) {
        System.out.println("____________________________________________________________\n"
                + logo
                + "\n Hello! I'm Simon\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println(" An error occurred while loading tasks.");
    }

    public void showAddTask(Task task, int size) {
        System.out.println(
                " Got it. I've added this task:\n"
                + "   " + task
                + "\n Now you have " + size + " tasks in the list."
            );
    }

    public void showDeleteTask(Task task, int size) {
        System.out.println(
                " Noted. I've removed this task:\n"
                + "   " + task
                + "\n Now you have " + size + " tasks in the list."
            );
    }

    public void showTaskList(List<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
    }

    public void showMarkTask(Task task) {
        System.out.println(
                " Nice! I've marked this task as done:\n"
                + "   " + task
            );
    }

    public void showUnmarkTask(Task task) {
        System.out.println(
                " OK, I've marked this task as not done yet:\n"
                + "   " + task
            );
    }

    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }
}