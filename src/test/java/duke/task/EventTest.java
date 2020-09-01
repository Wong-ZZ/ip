package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import duke.exception.InvalidEventException;
import duke.parser.DateTimeParsing;

public class EventTest {
    @Test
    public void toSaveString() throws InvalidEventException {
        String description = "This is a test on toSaveString. /at 2020-03-20 12:00";
        Event event = Event.createEvent(description);
        String saveString = event.toSaveString();
        String expected = "0event " + description;
        assertEquals(expected, saveString);
    }

    @Test
    public void createEvent_invalidFormat_exceptionThrown() {
        assertThrows(InvalidEventException.class, () -> {
            String description = "This is an invalid format. /at 2018/09/21 15:00";
            Event.createEvent(description);
        });
    }

    @Test
    public void isDueOn() throws InvalidEventException {
        String description = "This is a test on isDueOn. /at 2018-09-21 15:00";
        Event event = Event.createEvent(description);
        LocalDate date1 = DateTimeParsing.parseDate("2018-09-21");
        LocalDate date2 = DateTimeParsing.parseDate("2019-09-21");
        assertTrue(event.isDueOn(date1));
        assertFalse(event.isDueOn(date2));
    }

    @Test
    public void toStringTest() throws InvalidEventException {
        String description = "This is a test on toString. /at 2022-02-03 08:00";
        Event event = Event.createEvent(description);
        String expected1 = "[E][\u2718] This is a test on toString. (at: Feb 3 2022 08:00 AM)";
        assertEquals(expected1, event.toString());

        event.markAsDone();

        String expected2 = "[E][\u2713] This is a test on toString. (at: Feb 3 2022 08:00 AM)";
        assertEquals(expected2, event.toString());
    }
}
