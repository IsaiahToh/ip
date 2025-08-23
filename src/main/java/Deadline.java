import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected String by;
    protected LocalDateTime byDateTime;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.byDateTime = parseDateTime(by);
    }

    private LocalDateTime parseDateTime(String input) {
        String[] patterns = {
            "d/M/yyyy HHmm",
            "d/M/yyyy",
            "yyyy-MM-dd HHmm",
            "yyyy-MM-dd"
        };
        for (String pattern : patterns) {
            try {
                if (pattern.contains("H")) {
                    return LocalDateTime.parse(input, DateTimeFormatter.ofPattern(pattern));
                } else {
                    return LocalDateTime.parse(input + " 0000", DateTimeFormatter.ofPattern(pattern + " HHmm"));
                }
            } catch (DateTimeParseException e) {}
        }
        return null;
    }

    @Override
    public String toString() {
        String dateStr = by;
        if (byDateTime != null) {
            dateStr = byDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"));
        }
        return "[D]" + super.toString() + " (by: " + dateStr + ")";
    }
}