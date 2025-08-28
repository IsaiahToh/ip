package simon.exceptions;
public class SimonExceptions extends Exception {
    public SimonExceptions(String message) {
        super(message);
    }

    public static class EmptyTaskException extends SimonExceptions {
        public EmptyTaskException(String message) {
            super(message);
        }
    }

    public static class UnknownCommandException extends SimonExceptions {
        public UnknownCommandException(String message) {
            super(message);
        }
    }
}
