package duke;

import duke.task.Task;

import java.util.ArrayList;

/* This class handles printing outputs to screen */
public class DukePrinter {

    private static final String LINE_DIVIDER = "\t____________________________________________________________";

    private static void printMessage(String[] messageSentences) {
        /* Print sentences at the correct indentation level, with a line divider at the start and end */
        System.out.println(LINE_DIVIDER);
        for (int i = 0; i < messageSentences.length; i++) {
            System.out.println("\t " + messageSentences[i]);
        }
        System.out.println(LINE_DIVIDER);
    }

    private static String getNumTasksString(int numberOfTasks) {
        if (numberOfTasks == 1) {
            return "You have " + Integer.toString(numberOfTasks) + " task in the list.";
        }
        return "You have " + Integer.toString(numberOfTasks) + " tasks in the list.";
    }

    public static void printWelcomeMessage() {
        String[] welcomeMessage = {
                "Hello! I'm Duke",
                "What can I do for you?",
                "(Type 'help' for a list of commands)"};
        printMessage(welcomeMessage);
    }

    public static void printExitMessage() {
        String[] exitMessage = {
                "Bye. Hope to see you again soon!"};
        printMessage(exitMessage);
    }

    public static void printHelpMessage() {
        String[] helpMessage = {
                "List of valid commands:",
                "bye - Exits program",
                "list - Lists all tasks",
                "done <task_number> - Mark the specified task as done",
                "todo <task_description> - Create a new task with the specified description",
                "deadline <task_description> /by <deadline_date> - " +
                        "Create a new task with the specified description and deadline",
                "event <task_description> /at <event_date> - " +
                        "Create a new task with the specified description and event date"
        };
        printMessage(helpMessage);
    }

    public static void printTasks(ArrayList<Task> tasks) {
        String[] taskMessage = new String[tasks.size() + 1];
        if (tasks.size() == 0) {
            taskMessage[0] = "You have no tasks right now";
        } else {
            taskMessage[0] = "Here is a list of all your tasks:";
            for (int i = 0; i < tasks.size(); i++) {
                taskMessage[i + 1] = Integer.toString(i + 1) + ". " + tasks.get(i);
            }
        }
        printMessage(taskMessage);
    }

    public static void printTaskAdded(ArrayList<Task> tasks, Task task) {
        String[] taskAddedMessage = {
                "added: " + task.toString(),
                getNumTasksString(tasks.size())
        };
        printMessage(taskAddedMessage);
    }

    public static void printFallbackMessage() {
        String[] fallbackMessage = {
                "I didn't quite catch what you were saying. Please try again.",
                "Try using \"help\" for a list of commands."
        };
        printMessage(fallbackMessage);
    }

    public static void printInvalidArgumentsMessage() {
        String[] invalidArgumentsMessage = {
                "That's an invalid task number!"
        };
        printMessage(invalidArgumentsMessage);
    }

    public static void printErrorMessage(String errorMessage) {
        String[] errorMessages = {
                errorMessage,
        };
        printMessage(errorMessages);
    }
}