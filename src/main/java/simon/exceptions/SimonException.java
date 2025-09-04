package simon.exceptions;

public class SimonException extends Exception {
    public SimonException(String message) {
        super(message);
    }

    public static class EmptyTaskException extends SimonException {
        public EmptyTaskException(String message) {
            super(message);
        }
    }

    public static class UnknownCommandException extends SimonException {
        public UnknownCommandException(String message) {
            super(message);
        }
    }
}
