package duke.exception;

public abstract class DukeException extends Exception {
    protected DukeException(String errorMessage) {
        super(errorMessage);
    }
}

