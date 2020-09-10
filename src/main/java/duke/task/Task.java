package duke.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// Parent class for all types of tasks that can be created by the user.
public abstract class Task {
    private static final String SYMBOL_DONE = "O";
    private static final String SYMBOL_NOT_DONE = "X";

    protected String description;
    protected boolean isDone = false;
    protected ArrayList<String> tags = new ArrayList<>(Collections.singletonList(""));

    public Task(String description) {
        this.description = description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns an icon that represents the task status.
     *
     * @return String x if not done and o if done.
     */
    public String getStatusIcon() {
        return isDone ? SYMBOL_DONE : SYMBOL_NOT_DONE;
    }

    public boolean containsKeyword(String keyword) {
        return description.toLowerCase().contains(keyword.toLowerCase());
    }

    public void addTags(String[] tagsToAdd) {
        if (tagsToAdd.length == 0) {
            return;
        }

        tags.addAll(Arrays.asList(tagsToAdd));
    }

    protected String stringifyTags() {
        return String.join(" #", tags);
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "]" + " " + description;
    }

    /**
     * Returns true or false based on whether the task is due on the date provided.
     *
     * @param date LocalDate the date to check.
     * @return boolean Whether the task is due on the specified date.
     */
    public abstract boolean isDueOn(LocalDate date);

    /**
     * Converts the task into a string format that will be stored in the save file.
     *
     * @return String storage information of task.
     */
    public abstract String toSaveString();

    /**
     * Returns true if the task is due in n number of days else return false.
     *
     * @param n int time period in days.
     * @return boolean Whether the task is due in the specified period.
     */
    public abstract boolean isDueInNDays(int n);
}
