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
        String startStr = start;
        String endStr = end;
        if (startDateTime != null) {
            startStr = startDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"));
        }
        if (endDateTime != null) {
            endStr = endDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"));
        }
        return "[E]" + super.toString() + " (from: " + startStr + " to: " + endStr + ")";
    }
}