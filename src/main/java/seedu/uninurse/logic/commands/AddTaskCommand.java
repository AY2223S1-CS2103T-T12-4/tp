package seedu.uninurse.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.uninurse.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_OPTION_PATIENT_INDEX;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.List;

import seedu.uninurse.commons.core.Messages;
import seedu.uninurse.commons.core.index.Index;
import seedu.uninurse.logic.commands.exceptions.CommandException;
import seedu.uninurse.model.Model;
import seedu.uninurse.model.person.Patient;
import seedu.uninurse.model.task.Task;
import seedu.uninurse.model.task.TaskList;

/**
 * Add a task to an existing person in the patient list.
 */
public class AddTaskCommand extends AddGenericCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_OPTION_PATIENT_INDEX
            + ": Adds a task to a patient.\n"
            + "Format: " + COMMAND_WORD + " " + PREFIX_OPTION_PATIENT_INDEX + " PATIENT_INDEX "
            + PREFIX_TASK_DESCRIPTION + "TASK_DESCRIPTION | <DATE TIME> | <INTERVAL TIME_PERIOD>\n"
            + "Examples:\n" + COMMAND_WORD + " " + PREFIX_OPTION_PATIENT_INDEX + " 2 "
            + PREFIX_TASK_DESCRIPTION + "Change dressing\n"
            + COMMAND_WORD + " " + PREFIX_OPTION_PATIENT_INDEX + " 2 "
            + PREFIX_TASK_DESCRIPTION + "Change dressing | 22-4-22 1345\n"
            + COMMAND_WORD + " " + PREFIX_OPTION_PATIENT_INDEX + " 2 "
            + PREFIX_TASK_DESCRIPTION + "Change dressing | 22-4-22 1345 | 3 weeks\n";

    public static final String MESSAGE_ADD_TASK_SUCCESS = "New task added to %1$s: %2$s";

    public static final CommandType ADD_TASK_COMMAND_TYPE = CommandType.TASK;

    private final Index index;
    private final Task task;

    /**
     * Creates an AddTaskCommand to add a {@code Task} to the specified person.
     * @param index index of the person in the filtered person list to add the task
     * @param task task of the person to be added to
     */
    public AddTaskCommand(Index index, Task task) {
        requireAllNonNull(index, task);

        this.index = index;
        this.task = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Patient personToEdit = lastShownList.get(index.getZeroBased());


        if (personToEdit.getTasks().hasTask(task)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_TASK);
        }

        TaskList updatedTaskList = personToEdit.getTasks().add(task);

        Patient editedPerson = new Patient(personToEdit, updatedTaskList);

        model.setPerson(personToEdit, editedPerson);
        model.setPatientOfInterest(editedPerson);

        return new CommandResult(String.format(MESSAGE_ADD_TASK_SUCCESS, editedPerson.getName().toString(), task),
                ADD_TASK_COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        // state check
        AddTaskCommand e = (AddTaskCommand) other;
        return index.equals(e.index) && task.equals((e.task));
    }
}
