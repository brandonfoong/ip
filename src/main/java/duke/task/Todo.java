package duke.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
        super.setTaskType('T');
    }
}
