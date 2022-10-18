package seedu.uninurse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.uninurse.model.Model;
import seedu.uninurse.model.person.Patient;

/**
 * Clears the uninurse book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Listed patients have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Patient> lastShownList = new ArrayList<Patient>(model.getFilteredPersonList());
        for (Patient patientToDelete : lastShownList) {
            model.deletePerson(patientToDelete);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
