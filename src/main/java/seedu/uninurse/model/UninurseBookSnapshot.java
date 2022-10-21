package seedu.uninurse.model;

import java.util.List;

import seedu.uninurse.logic.commands.CommandResult;
import seedu.uninurse.model.person.Patient;

/**
 * A snapshot of a UninurseBook after a command.
 */
public class UninurseBookSnapshot {
    private final ReadOnlyUninurseBook uninurseBook;
    private final CommandResult commandResult;

    /**
     * Creates an UninurseBookSnapshot using the Persons in the {@code toBeCopied}
     */
    public UninurseBookSnapshot(ReadOnlyUninurseBook toBeCopied) {
        this.uninurseBook = new UninurseBook(toBeCopied);
        this.commandResult = new CommandResult("");
    }

    /**
     * Creates an UninurseBookSnapshot using the Persons in the {@code toBeCopied}
     */
    public UninurseBookSnapshot(ReadOnlyUninurseBook toBeCopied, CommandResult commandResult) {
        this.uninurseBook = new UninurseBook(toBeCopied);
        this.commandResult = commandResult;
    }

    /**
     * Returns commandResult.
     */
    public CommandResult getCommandResult() {
        return commandResult;
    }

    /**
     * Returns the person list stored in uninurseBook.
     */
    public List<Patient> getPersonList() {
        return uninurseBook.getPersonList();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UninurseBookSnapshot // instanceof handles nulls
                && uninurseBook.equals(((UninurseBookSnapshot) other).uninurseBook)
                && commandResult.equals(((UninurseBookSnapshot) other).commandResult));
    }
}
