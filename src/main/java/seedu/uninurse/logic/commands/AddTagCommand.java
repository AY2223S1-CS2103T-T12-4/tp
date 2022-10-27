package seedu.uninurse.logic.commands;

import static seedu.uninurse.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.uninurse.commons.core.Messages;
import seedu.uninurse.commons.core.index.Index;
import seedu.uninurse.logic.commands.exceptions.CommandException;
import seedu.uninurse.model.Model;
import seedu.uninurse.model.person.Patient;
import seedu.uninurse.model.tag.Tag;
import seedu.uninurse.model.tag.TagList;
import seedu.uninurse.model.tag.exceptions.DuplicateTagException;

/**
 * Add a tag to an existing patient in the person list.
 */
public class AddTagCommand extends AddGenericCommand {
    // tentative syntax; TODO: integrate with AddGenericCommand
    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a tag to the patient identified "
            + "by the index number used in the last patient listing.\n"
            + "Parameters: PATIENT_INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD
            + " 2 " + PREFIX_TAG + "high-risk";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "New tag added to %1$s: %2$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in %1$s's tag list";

    public static final CommandType ADD_TAG_COMMAND_TYPE = CommandType.EDIT_PATIENT;

    private final Index index;
    private final Tag tag;

    /**
     * Creates an AddTagCommand to add a {@code Tag} to the specified person.
     * @param index The index of the person in the filtered person list to add the tag.
     * @param tag The tag of the person to be added to.
     */
    public AddTagCommand(Index index, Tag tag) {
        requireAllNonNull(index, tag);

        this.index = index;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());
        TagList updatedTagList;

        try {
            updatedTagList = patientToEdit.getTags().add(tag);
        } catch (DuplicateTagException dte) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, patientToEdit.getName()));
        }

        Patient editedPatient = new Patient(patientToEdit, updatedTagList);

        model.setPerson(patientToEdit, editedPatient);
        model.setPatientOfInterest(editedPatient);

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, editedPatient.getName(), tag),
                ADD_TAG_COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand command = (AddTagCommand) other;
        return index.equals(command.index) && tag.equals((command.tag));
    }
}
