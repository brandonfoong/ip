package duke;

import duke.command.*;
import duke.task.Task;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Scanner;

public class DukeParser {
    /* List of all commands that Duke accepts */
    public static final String BYE_COMMAND = "bye";
    public static final String LIST_COMMAND = "list";
    public static final String HELP_COMMAND = "help";
    public static final String DONE_COMMAND = "done";
    public static final String TODO_COMMAND = "todo";
    public static final String DEADLINE_COMMAND = "deadline";
    public static final String EVENT_COMMAND = "event";
    public static final String DELETE_COMMAND = "delete";
    public static final String CLEAR_COMMAND = "clear";
    public static final String FIND_COMMAND = "find";

    /* List of command delimiters, e.g. "/by" for "deadline" */
    public static final String DEADLINE_DELIMITER = "/by";
    public static final String EVENT_DELIMITER = "/at";

    private static final int ADDITIONAL_ARGUMENTS_LIMIT = 2;

    private static Scanner scanner = new Scanner(System.in);

    private static ArrayList<String> tokenizeInput(String userCommand) {
        String[] commandTokens = userCommand.split(" ", 2);

        if (commandTokens.length == 0) {
            return new ArrayList<String>();
        }

        commandTokens[0] = commandTokens[0].toLowerCase();
        ArrayList<String> commandParsed = new ArrayList<String>();
        commandParsed.add(commandTokens[0]);

        ArrayList<String> additionalArgs = new ArrayList<String>();
        switch (commandTokens[0]) {
        case DEADLINE_COMMAND:
            if (commandTokens.length < 2) {
                break;
            }
            additionalArgs = splitAdditonalArgs(commandTokens[1], DEADLINE_DELIMITER);
            break;
        case EVENT_COMMAND:
            if (commandTokens.length < 2) {
                break;
            }
            additionalArgs = splitAdditonalArgs(commandTokens[1], EVENT_DELIMITER);
            break;
        default:
            for (int i = 1; i < commandTokens.length; i++) {
                additionalArgs.add(commandTokens[i]);
            }
            break;
        }

        for (String additionalArg : additionalArgs) {
            String trimmedArg = additionalArg.trim();
            commandParsed.add(trimmedArg);
        }
        return commandParsed;
    }

    private static ArrayList<String> splitAdditonalArgs(String commandToken, String delimiter) {
        String[] additionalArgs = commandToken.split(delimiter, ADDITIONAL_ARGUMENTS_LIMIT);
        ArrayList<String> additionalArgsList = new ArrayList<String>(Arrays.asList(additionalArgs));
        return additionalArgsList;
    }

    public static ArrayList<String> readUserInput() {
        String userInput = scanner.nextLine();
        ArrayList<String> userInputTokenized = tokenizeInput(userInput);
        return userInputTokenized;
    }

    public static ArrayList<ArrayList<String>> parseCSV(ArrayList<String> taskInfo) {
        ArrayList<ArrayList<String>> parsedTaskInfo = new ArrayList<ArrayList<String>>();
        for (String taskString : taskInfo) {
            ArrayList<String> parsedTaskString = new ArrayList<String>();
            String[] tokenizedTaskString = taskString.split(",");
            for (String token : tokenizedTaskString) {
                parsedTaskString.add(token);
            }
            parsedTaskInfo.add(parsedTaskString);
        }
        return parsedTaskInfo;
    }

    public static boolean executeCommand(
            DukeTaskList dukeTaskList, ArrayList<String> commandTokens)
            throws DukeException {
        if (commandTokens.isEmpty()) {
            throw new DukeException("Please enter a command.\n"
                    + "Try using \"help\" for a list of commands."
            );
        }

        boolean isDoneReadingInputs = false;
        Command command = null;
        String baseCommand = commandTokens.get(0);
        switch (baseCommand) {
        case BYE_COMMAND:
            isDoneReadingInputs = true;
            break;
        case CLEAR_COMMAND:
            command = new ClearCommand(commandTokens, dukeTaskList);
            break;
        case LIST_COMMAND:
            command = new ListCommand(commandTokens, dukeTaskList);
            break;
        case HELP_COMMAND:
            command = new HelpCommand(commandTokens, dukeTaskList);
            break;
        case DONE_COMMAND:
            command = new DoneCommand(commandTokens, dukeTaskList);
            break;
        case TODO_COMMAND:
            command = new TodoCommand(commandTokens, dukeTaskList, true, false);
            break;
        case DEADLINE_COMMAND:
            command = new DeadlineCommand(commandTokens, dukeTaskList, true, false);
            break;
        case EVENT_COMMAND:
            command = new EventCommand(commandTokens, dukeTaskList, true, false);
            break;
        case DELETE_COMMAND:
            command = new DeleteCommand(commandTokens, dukeTaskList);
            break;
        case FIND_COMMAND:
            command = new FindCommand(commandTokens, dukeTaskList);
            break;
        default:
            command = new DefaultCommand(commandTokens, dukeTaskList);
            break;
        }
        if (command != null) {
            command.execute();
        }
        return isDoneReadingInputs;
    }
}
