package scruffles;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * This class is where the tasks are stored while the program is running
 *
 * @author Shamus Tan
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private int taskCount;

    /**
     * Constructor for TaskList
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.taskCount = 0;
    }

    /**
     * Constructor for TaskList
     *
     * @param tasks the ArrayList that contains all saved Tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.taskCount = tasks.size();
    }

    /**
     * Adds the tasks into the TaskList
     *
     * @param str the input string of the Task
     * @param type the enumerated type of Task (TODO, DEADLINE, EVENT)
     * @return the respective output messages
     */
    public String add(String str, Scruffles.Type type) {
        switch (type) {
        case TODO:
            return todo(str);
        case DEADLINE:
            return deadline(str);
        case EVENT:
            return event(str);
        case DOWITHINPERIOD:
            return doWithinPeriod(str);
        default:
            assert false : "is not a correct type";
            return "";
        }
    }

    /**
     * Adds a Todo into the TaskList
     *
     * @param str the input string of the Todo
     * @return the respective output messages
     */
    private String todo(String str) {
        if (str.equals("todo") || str.equals("todo ")) {
            return "grrrr >:( there is no task name woof woof!";
        }
        tasks.add(new Todo(str.replace("todo ", "")));
        taskCount++;
        return String.format("woof! the task is added woof!\n"
                        + "%s\n"
                        + "you now have %d tasks in the list woof!",
                tasks.get(taskCount - 1).toString(), taskCount);
    }

    /**
     * Adds a Deadline into the TaskList
     *
     * @param str the input string of the Deadline
     * @return the respective output messages
     */
    private String deadline(String str) {
        try {
            if (str.equals("deadline") || str.equals("deadline ")) {
                return "grrrr >:( there is no task name woof woof!";
            }
            String[] input = str.split(" /by ");
            String name = input[0].replace("deadline ", "");
            LocalDate date = LocalDate.parse(input[1]);
            tasks.add(new Deadline(name, date));
            taskCount++;
            return String.format("woof! the task is added woof!\n"
                            + "%s\n"
                            + "you now have %d tasks in the list woof!",
                    tasks.get(taskCount - 1).toString(),
                    taskCount);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "grrrr >:( when is your deadline?? woof woof!";
        } catch (DateTimeParseException e) {
            return "grrrr >:( please input deadline in yyyy-mm-dd format woof woof!";
        }
    }

    /**
     * Adds a Event into the TaskList
     *
     * @param str the input string of the Event
     * @return the respective output messages
     */
    private String event(String str) {
        try {
            if (str.equals("event") || str.equals("event ")) {
                return "grrrr >:( there is no task name woof woof!";
            }
            String[] input = str.split(" /at ");
            String name = input[0].replace("event ", "");
            tasks.add(new Event(name, input[1]));
            taskCount++;
            return String.format("Got it. I've added this task:\n"
                            + "%s\n"
                            + "you now have %d tasks in the list woof!",
                    tasks.get(taskCount - 1).toString(),
                    taskCount);
        } catch (TimeErrorException e) {
            return e.getMessage();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "grrrr >:( when is your event?? woof woof!";
        } catch (DateTimeParseException e) {
            return "grrrr >:( please input event date as 'yyyy-mm-dd from hh:mm to hh:mm' format woof woof!";
        }
    }

    /**
     * Adds a task that has to be done within a certain period into the TaskList
     *
     * @param str the input string of the DoWithinPeriod
     * @return the respective output messages
     */
    private String doWithinPeriod(String str) {
        try {
            if (str.equals("dowithinperiod") || str.equals("dowithinperiod ")) {
                return "grrrr >:( there is no task name woof woof!";
            }
            String[] input = str.split(" /btw ");
            String name = input[0].replace("dowithinperiod ", "");
            String[] dates = input[1].split(" and ");
            LocalDate startDate = LocalDate.parse(dates[0]);
            LocalDate endDate = LocalDate.parse(dates[1]);
            if (startDate.isAfter(endDate)) {
                return "grrrr >:( end date must be after start date woof woof!";
            }
            tasks.add(new DoWithinPeriod(name, startDate, endDate));
            taskCount++;
            return String.format("woof! the task is added woof!\n"
                            + "%s\n"
                            + "you now have %d tasks in the list woof!",
                    tasks.get(taskCount - 1).toString(),
                    taskCount);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "grrrr >:( when do you need to do this within?? woof woof!";
        } catch (DateTimeParseException e) {
            return "grrrr >:( please input dates in yyyy-mm-dd format woof woof!";
        }
    }

    /**
     * Displays the TaskList in the program
     *
     * @return the list of tasks as a string
     */
    public String list() {
        StringBuilder output = new StringBuilder();
        if (tasks.isEmpty()) {
            output = new StringBuilder("you have no tasks woof woof!");
        }

        for (int i = 0; i < tasks.size(); i++) {
            output.append(i + 1).append(".").append(tasks.get(i).toString()).append("\n");
        }

        return output.toString();
    }

    /**
     * Deletes the task of given number from the TaskList
     *
     * @param input the input string of the message
     * @return the respective output messages
     */
    public String delete(String input) {
        try {
            if (input.equals("delete") || input.equals("delete ")) {
                return "grrrr >:( you need to delete something woof woof!";
            }

            if (input.equals("delete all")) {
                tasks.clear();
                taskCount = 0;
                return "woof! all tasks are now deleted woof!";
            }

            int i = Integer.parseInt(input.replace("delete ", ""));
            if (i > tasks.size() || i <= 0) {
                return String.format("grrrr >:( there is no number %d item in the list woof woof!", i);
            }

            Task t = tasks.remove(i - 1);
            taskCount--;
            return String.format("woof! the task is now deleted woof!\n"
                            + "%s\n"
                            + "you now have %d tasks in the list woof!",
                    t.toString(),
                    taskCount);

        } catch (NumberFormatException e) {
            return "grrrr >:( you need to input an integer or delete all woof woof!";
        }
    }

    /**
     * Marks or unmarks the task of given number from the TaskList
     *
     * @param input the input string of the message
     * @return the respective output messages
     */
    public String mark(String input) {
        try {
            if (input.equals("mark") || input.equals("mark ")) {
                return "grrrr >:( you need to mark something woof woof!";
            }

            String str = input.replace("mark ", "");
            int j = Integer.parseInt(str);
            if (j > tasks.size() || j <= 0) {
                return String.format("grrrr >:( there is no number %d item in the list woof woof!", j);
            }

            tasks.get(j - 1).setDone();
            return "woof! the task is now marked as done woof!\n"
                    + tasks.get(j - 1).toString();

        } catch (NumberFormatException e) {
            return "grrrr >:( you need to input an integer woof woof!";
        }
    }

    /**
     * Finds a task whose name contains the given keyword in the TaskList
     *
     * @param input the input string of the message
     * @return the respective output messages
     */
    public String find(String input) {
        if (input.equals("find") || input.equals("find ")) {
            return "grrrr >:( what do you want to find woof woof!";
        }

        String keyword = input.replace("find ", "");
        StringBuilder output = new StringBuilder("woof here are the tasks i found that have this keyword woof:");
        for (Task task : tasks) {
            if (task.taskName.contains(keyword)) {
                output.append("\n").append(task);
            }
        }
        return output.toString();
    }

    /**
     * Saves the data of the TaskList into the file and displays a goodbye message
     *
     * @return a goodbye message
     */
    public String bye() {
        Storage.save(this);
        return Ui.bye();
    }

    /**
     * Converts the current TaskList into a string format that can be easily read by the program after saving
     *
     * @return the TaskList in the required string format for saving to file
     */
    public String saveToFile() {
        StringBuilder output = new StringBuilder();
        for (Task task : tasks) {
            String textInput = "";
            String isDone = "O / ";

            if (task.isDone) {
                isDone = "X / ";
            }

            if (task instanceof Todo) {
                textInput = "T / " + isDone + task.taskName + "\n";
            } else if (task instanceof Deadline) {
                textInput = "D / " + isDone + task.taskName + " / " + ((Deadline) task).by + "\n";
            } else if (task instanceof Event) {
                textInput = "E / " + isDone + task.taskName + " / " + ((Event) task).at + " / "
                        + ((Event) task).startTime + " / " + ((Event) task).endTime + "\n";
            } else if (task instanceof DoWithinPeriod) {
                textInput = "P / " + isDone + task.taskName + " / " + ((DoWithinPeriod) task).startDate + " / "
                        + ((DoWithinPeriod) task).endDate + "\n";
            }
            output.append(textInput);
        }
        return output.toString();
    }
}
