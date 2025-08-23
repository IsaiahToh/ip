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
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        };
        for (DateTimeFormatter f : formatters) {
            try {
                if (f.toString().contains("H")) {
                    return LocalDateTime.parse(input, f);
                } else {
                    return LocalDateTime.parse(input + " 0000", DateTimeFormatter.ofPattern(f.toString() + " HHmm"));
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