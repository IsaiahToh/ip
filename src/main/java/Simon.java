import java.util.Scanner;

public class Simon {
    public static void main(String[] args) {
        String logo = "   _____ _                     \n"
                + "  / ___/(_)___ ___  ____  ____ \n"
                + "  \\__ \\/ / __ `__ \\/ __ \\/ __ \\\n"
                + " ___/ / / / / / / / /_/ / / / /\n"
                + "/____/_/_/ /_/ /_/\\____/_/ /_/ \n";
        System.out.println(
            "____________________________________________________________\n" 
            + logo 
            + "\nHello! I'm Simon\n"
            + "What can I do for you?\n"
            + "____________________________________________________________\n"
        );

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("""
                    ____________________________________________________________
                     Bye. Hope to see you again soon!
                    ____________________________________________________________
                    """);
                break;
            } else {
                System.out.println(
                    "____________________________________________________________"
                    + "\n " 
                    + input
                    + "\n____________________________________________________________"
                );
            }
        }
        scanner.close();
    }
}