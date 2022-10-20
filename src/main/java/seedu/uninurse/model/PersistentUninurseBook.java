package seedu.uninurse.model;

import java.util.ArrayList;
import java.util.List;

import seedu.uninurse.commons.core.Config;
import seedu.uninurse.logic.commands.CommandResult;

/**
 * Versions of UninurseBookSnapshot after every command.
 */
public class PersistentUninurseBook {
    private final UninurseBook workingCopy;
    private final List<UninurseBookSnapshot> uninurseBookVersions;
    private int currentVersion;

    /**
     * Creates an UninurseBookSnapshot using the Persons in the {@code toBeCopied}
     */
    public PersistentUninurseBook(ReadOnlyUninurseBook toBeCopied) {
        this.workingCopy = new UninurseBook(toBeCopied);
        this.uninurseBookVersions = new ArrayList<UninurseBookSnapshot>();
        this.uninurseBookVersions.add(new UninurseBookSnapshot(toBeCopied));
        this.currentVersion = 0;
        this.handleChange();
    }

    /**
     * Returns the current working copy of UninurseBook.
     */
    public UninurseBook getWorkingCopy() {
        return workingCopy;
    }

    private void handleChange() {
        workingCopy.setPersons(uninurseBookVersions.get(currentVersion).getPersonList());
    }

    /**
     * Returns whether you can revert to an earlier version of UninurseBook.
     */
    public boolean canUndo() {
        return currentVersion > 0;
    }

    /**
     * Returns whether you can revert to a later version of UninurseBook.
     */
    public boolean canRedo() {
        return currentVersion + 1 < uninurseBookVersions.size();
    }

    /**
     * Reverts to an earlier version of UninurseBook.
     */
    public void undo() {
        if (canUndo()) {
            currentVersion--;
            handleChange();
        }
    }

    /**
     * Reverts to a later version of UninurseBook.
     */
    public void redo() {
        if (canRedo()) {
            currentVersion++;
            handleChange();
        }
    }

    /**
     * Makes a snapshot of the current UninurseBook.
     */
    public void makeSnapshot(CommandResult commandResult) {
        while (uninurseBookVersions.size() > currentVersion + 1) {
            uninurseBookVersions.remove(uninurseBookVersions.size() - 1);
        }
        while (uninurseBookVersions.size() > Config.UNDO_LIMIT) {
            uninurseBookVersions.remove(0);
        }
        uninurseBookVersions.add(new UninurseBookSnapshot(workingCopy, commandResult));
        currentVersion = uninurseBookVersions.size() - 1;
        handleChange();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersistentUninurseBook // instanceof handles nulls
                && workingCopy.equals(((PersistentUninurseBook) other).workingCopy)
                && uninurseBookVersions.equals(((PersistentUninurseBook) other).uninurseBookVersions)
                && currentVersion == ((PersistentUninurseBook) other).currentVersion);
    }
}
