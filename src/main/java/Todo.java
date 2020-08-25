import java.time.LocalDate;

public class Todo extends Task {
    private Todo(String desc) {
        super(desc);
    }

    protected static Todo createTodo(String details) throws InvalidTodoException {
        if (details.equals("")) {
            throw new InvalidTodoException();
        }
        return new Todo(details);
    }

    @Override
    public boolean isDueOn(LocalDate date) {
        return false;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
