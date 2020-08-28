package duke.task;

import duke.exception.InvalidDeadlineException;

import duke.parser.DateTimeParsing;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {
    @Test
    public void toSaveString() throws InvalidDeadlineException {
        String description = "This is a test on toSaveString. /by 2020-03-20 12:00";
        Deadline deadline = Deadline.createDeadline(description);
        String saveString = deadline.toSaveString();
        String expected = "0deadline " + description;
        assertEquals(expected, saveString);
    }

    @Test
    public void createDeadLine_invalidFormat_exceptionThrown() {
        assertThrows(InvalidDeadlineException.class, () -> {
            String description = "This is an invalid format. /by 2018/09/21 15:00";
            Deadline.createDeadline(description);
        });
    }

    @Test
    public void isDueOn() throws InvalidDeadlineException {
        String description = "This is a test on isDueOn. /by 2018-09-21 15:00";
        Deadline deadline = Deadline.createDeadline(description);
        LocalDate date1 = DateTimeParsing.parseDate("2018-09-21");
        LocalDate date2 = DateTimeParsing.parseDate("2019-09-21");
        assertTrue(deadline.isDueOn(date1));
        assertFalse(deadline.isDueOn(date2));
    }

    @Test
    public void toStringTest() throws InvalidDeadlineException {
        String description = "This is a test on toString. /by 2022-02-03 08:00";
        Deadline deadline = Deadline.createDeadline(description);
        String expected1 = "[D][\u2718] This is a test on toString. (by: Feb 3 2022 08:00 AM)";
        assertEquals(expected1, deadline.toString());

        deadline.markAsDone();

        String expected2 = "[D][\u2713] This is a test on toString. (by: Feb 3 2022 08:00 AM)";
        assertEquals(expected2, deadline.toString());
    }
}