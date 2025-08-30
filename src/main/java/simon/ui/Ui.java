package simon.ui;

import java.util.Scanner;
import java.util.List;

import simon.task.Task;

/**
 * Handles user interface for the Simon chatbot. A <code>Ui</code> object manages input and output to the user.
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the welcome message and logo to the user.
     *
     * @param logo ASCII logo to be displayed.
     */
    public void showWelcome(String logo) {
        System.out.println("____________________________________________________________\n"
                + logo
                + "\n Hello! I'm Simon\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n");
    }

    /**
     * Displays a divider line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads a command from the user input.
     *
     * @return Command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message Error message to be displayed.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a loading error message to the user.
     */
    public void showLoadingError() {
        System.out.println(" An error occurred while loading tasks.");
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task Task that was added.
     * @param size New number of tasks in the list.
     */
    public void showAddTask(Task task, int size) {
        System.out.println(
                " Got it. I've added this task:\n"
                        + "   " + task
                        + "\n Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task Task that was deleted.
     * @param size New number of tasks in the list.
     */
    public void showDeleteTask(Task task, int size) {
        System.out.println(
                " Noted. I've removed this task:\n"
                        + "   " + task
                        + "\n Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Displays the list of all the tasks to the user.
     *
     * @param tasks List of tasks to be displayed.
     */
    public void showTaskList(List<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task Task that was marked as done.
     */
    public void showMarkTask(Task task) {
        System.out.println(
                " Nice! I've marked this task as done:\n"
                        + "   " + task
        );
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param task Task that was marked as not done.
     */
    public void showUnmarkTask(Task task) {
        System.out.println(
                " OK, I've marked this task as not done yet:\n"
                        + "   " + task
        );
    }

    /**
     * Displays the list of matching tasks to the user.
     *
     * @param tasks List of tasks to be displayed.
     */
    public void showFindTasks(List<Task> tasks) {
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays the goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }
}