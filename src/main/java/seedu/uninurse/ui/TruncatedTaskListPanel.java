package seedu.uninurse.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.uninurse.model.person.Patient;
import seedu.uninurse.model.person.Person;

/**
 * Panel containing the list of all patients with a truncated task list.
 */
public class TruncatedTaskListPanel extends UiPart<Region> {
    private static final String FXML = "TruncatedTaskListPanel.fxml";

    @FXML
    private ListView<Person> truncatedTaskListView;
    @FXML
    private Label header;

    /**
     * Creates a TruncatedTaskListPanel with the given list of persons.
     */
    public TruncatedTaskListPanel(ObservableList<Person> persons) {
        super(FXML);
        this.truncatedTaskListView.setItems(persons);
        this.truncatedTaskListView.setCellFactory(listview -> new TruncatedTaskListViewCell());

        this.header.setText("All Patient's Tasks:");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code TruncatedTaskListCard}.
     */
    class TruncatedTaskListViewCell extends ListCell<Person> {
        TruncatedTaskListViewCell() {
            super();
            setStyle("-fx-padding: 0 5 0 0");
            prefWidthProperty().bind(truncatedTaskListView.widthProperty().subtract(20.0));
        }

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (person instanceof Patient) {
                    setGraphic(new TruncatedTaskListCard((Patient) person).getRoot());
                }
            }
        }
    }
}
