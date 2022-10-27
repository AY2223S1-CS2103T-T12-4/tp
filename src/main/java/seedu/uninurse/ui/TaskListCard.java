package seedu.uninurse.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.uninurse.model.task.DateTime;
import seedu.uninurse.model.task.Task;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class TaskListCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label taskname;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label recurrence;

    /**
     * Creates a {@code TaskListCard} with the given {@code TaskList} to display.
     */
    public TaskListCard(Task task, int displayedIndex) {
        super(FXML);
        this.cardPane.setSpacing(1);

        if (task.getDateTime().isPastDate()) {
            this.cardPane.setStyle("-fx-background-color: #F17A7E;"
                    + "-fx-padding: 1;" + "-fx-border-style: dashed inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 1;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        } else {
            this.cardPane.setStyle("-fx-background-color: #95b3e8;"
                    + "-fx-padding: 1;" + "-fx-border-style: dashed inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 1;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        }
        this.task = task;
        id.setText(displayedIndex + ". ");
        taskname.setText(task.getTaskDescription());
        DateTime dateTime = task.getDateTime();
        date.setText(getDateString(dateTime));
        time.setText(String.format("%s", dateTime.getTime()));
        recurrence.setText(task.getRecurrenceString());
    }

    /**
     * Returns the date and the numbers of days from today based on {@code dateTime}.
     */
    private String getDateString(DateTime dateTime) {
        String dateString = dateTime.getDate();
        int daysFromToday = dateTime.getDaysFromToday();
        if (daysFromToday == 0) {
            return String.format("%s (Today)", dateString);
        }
        if (daysFromToday == 1) {
            return String.format("%s (Tommorow)", dateString);
        }
        if (daysFromToday < 0) {
            if (daysFromToday == -1) {
                return String.format("%s (Overdue by 1 day)", dateString);
            } else {
                return String.format("%s (Overdue by %s days)", dateString, Math.abs(daysFromToday));
            }
        }
        return String.format("%s (in %s days)", dateString, daysFromToday);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskListCard)) {
            return false;
        }

        // state check
        TaskListCard card = (TaskListCard) other;
        return taskname.getText().equals(card.taskname.getText())
                && date.equals(card.date)
                && time.equals(card.time);
    }
}
