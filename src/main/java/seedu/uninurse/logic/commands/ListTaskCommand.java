package seedu.uninurse.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.uninurse.model.Model;

/**
 * Lists all tasks associated to all patients in the uninurse book to the user.
 */
public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "listTask";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final CommandType LIST_TASK_COMMAND_TYPE = CommandType.LIST_TASK;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // In case the previous command filters the task list
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.getFilteredPersonList().forEach(p -> p.getTasks().showAllTasks());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        model.updateFilteredPersonList(p -> !(p.getTasks().isEmpty()));
        return new CommandResult(MESSAGE_SUCCESS, LIST_TASK_COMMAND_TYPE);
    }
}
