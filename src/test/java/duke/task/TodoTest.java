package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import duke.exception.InvalidTodoException;
import duke.util.DukeDateTime;

public class TodoTest {
    @Test
    public void toSaveString() throws InvalidTodoException {
        String description = "This is a test on toSaveString.";
        Todo todo = Todo.createTodo(description);
        String saveString = todo.toSaveString();
        String expected = "0todo " + description;
        assertEquals(expected, saveString);
    }

    @Test
    public void createTodo_invalidFormat_exceptionThrown() {
        assertThrows(InvalidTodoException.class, () -> {
            String description = "";
            Todo.createTodo(description);
        });
    }

    @Test
    public void isDueOn() throws InvalidTodoException {
        String description = "This is a test on isDueOn.";
        Todo todo = Todo.createTodo(description);
        LocalDate date1 = DukeDateTime.parseDate("2018-09-21");
        assertFalse(todo.isDueOn(date1));
    }

    @Test
    public void toStringTest() throws InvalidTodoException {
        String description = "This is a test on toString.";
        Todo todo = Todo.createTodo(description);
        String expected1 = "[T][\u2718] This is a test on toString.";
        assertEquals(expected1, todo.toString());

        todo.markAsDone();

        String expected2 = "[T][\u2713] This is a test on toString.";
        assertEquals(expected2, todo.toString());
    }
}
