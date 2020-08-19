import java.util.ArrayList;
import java.util.Scanner;

public class InputHandler {
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static final String DIVIDER = "____________________________________________________________";
    private static final String CMD_EXIT = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_DONE = "done";
    private Scanner sc;

    public InputHandler(Scanner sc) {
        this.sc = sc;
        handleStart();
    }

    private static void handleStart() {
        String startMsg =
            DIVIDER + "\n" +
            "Hello! I'm Duke\n" +
            "What can I do for you?\n" +
            DIVIDER;
        System.out.println(startMsg);
    }

    public void run() {
        String input;
        while (!(input = sc.nextLine()).equals(CMD_EXIT)) {
            handleInput(input);
        }
        handleExit();
    }

    public void handleInput(String in) {
        String[] input = in.split(" ");
        String cmdWord = input[0];
        switch (cmdWord) {
        case (CMD_LIST):
            handleList();
            break;
        case (CMD_DONE):
            handleDone(Integer.parseInt(input[1]));
            break;
        default:
            try {
                handleOthers(in, cmdWord);
            } catch (DukeException e) {
                System.out.println(
                    DIVIDER + "\n" +
                    e.getMessage() + "\n" +
                    DIVIDER
                );
            }
        }
    }

    private void handleList() {
        int len = taskList.size();
        String firstLine = len == 0
            ? "There are no tasks in your list!"
            : "Here are the tasks in your list:";
        System.out.println(DIVIDER);
        System.out.println(firstLine);
        for (int i = 1; i <= len; i++) {
            Task task = taskList.get(i - 1);
            String output = i + "." + task.toString();
            System.out.println(output);
        }
        System.out.println(DIVIDER);
    }

    private void handleDone(int index) {
        Task task = taskList.get(index - 1);
        task.markAsDone();
        System.out.println(
            DIVIDER + "\n" +
            "Nice! I've marked this task as done\n" +
            "  " + task.toString() + "\n" +
            DIVIDER
        );
    }

    private void handleOthers(String in, String cmdWord) throws DukeException {
        String taskDetails = in.replaceFirst(cmdWord, "").trim();
        switch (cmdWord) {
        case "deadline":
            addNewTask(TaskType.Deadline, taskDetails);
            break;
        case "event":
            addNewTask(TaskType.Event, taskDetails);
            break;
        case "todo":
            addNewTask(TaskType.Todo, taskDetails);
            break;
        default:
            String errMsg = "I'm sorry, but I don't know what that means :-(";
            throw new InvalidCommandException(errMsg);
        }
    }

    private void addNewTask(TaskType type, String taskDetails) throws InvalidTaskException {
        Task task = Task.createTask(type, taskDetails);
        taskList.add(task);
        handleTaskCreated(task);
    }

    private void handleTaskCreated(Task task) {
        int len = taskList.size();
        String msg =
            DIVIDER + "\n" +
            "Got it. I've added this task: \n" +
            "  " + task.toString() + "\n" +
            "Now you have " + len + " task" + (len == 1 ? "" : "s") + " in the list.\n" +
            DIVIDER;
        System.out.println(msg);
    }

    private void handleExit() {
        String exitMsg =
            DIVIDER + "\n" +
            "Bye. Hope to see you again soon!\n" +
            DIVIDER;
        System.out.println(exitMsg);
    }
}
