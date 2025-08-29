package simon.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import simon.task.Task;
import simon.task.Todo;
import simon.task.Deadline;
import simon.task.Event;

public class ParserTest {

    @Test
    void testParseTaskFromFile_todo() {
        String line = "T | 1 | read book";
        Task task = Parser.parseTaskFromFile(line);
        Assertions.assertNotNull(task);
        Assertions.assertTrue(task instanceof Todo);
        Assertions.assertEquals("read book", task.getDescription());
        Assertions.assertTrue(task.isDone());
    }

    @Test
    void testParseTaskFromFile_deadline() {
        String line = "D | 0 | submit report | 2025-09-01";
        Task task = Parser.parseTaskFromFile(line);
        Assertions.assertNotNull(task);
        Assertions.assertTrue(task instanceof Deadline);
        Assertions.assertEquals("submit report", task.getDescription());
        Assertions.assertEquals("2025-09-01", ((Deadline) task).getBy());
        Assertions.assertFalse(task.isDone());
    }

    @Test
    void testParseTaskFromFile_event() {
        String line = "E | 1 | project meeting | 2025-09-01 10:00 | 2025-09-01 12:00";
        Task task = Parser.parseTaskFromFile(line);
        Assertions.assertNotNull(task);
        Assertions.assertTrue(task instanceof Event);
        Assertions.assertEquals("project meeting", task.getDescription());
        Assertions.assertEquals("2025-09-01 10:00", ((Event) task).getStart());
        Assertions.assertEquals("2025-09-01 12:00", ((Event) task).getEnd());
        Assertions.assertTrue(task.isDone());
    }

    @Test
    void testParseTaskFromFile_invalid() {
        String line = "X | 0 | something";
        Task task = Parser.parseTaskFromFile(line);
        Assertions.assertNull(task);
    }

    @Test
    void testParseTaskFromFile_malformed() {
        String line = "D | 1";
        Task task = Parser.parseTaskFromFile(line);
        Assertions.assertNull(task);
    }
}