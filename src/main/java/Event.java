import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task{
    protected String start;
    protected String end;
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
        this.startDateTime = parseDateTime(start);
        this.endDateTime = parseDateTime(end);
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
        String startStr = start;
        String endStr = end;
        if (startDateTime != null) {
            startStr = startDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"));
        }
        if (endDateTime != null) {
            endStr = endDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"));
        }
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}