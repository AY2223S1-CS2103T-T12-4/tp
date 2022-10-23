package seedu.uninurse.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.uninurse.commons.util.StringUtil;
import seedu.uninurse.model.person.Patient;

/**
 * Tests that a {@code Patient}'s {@code Task} matches any of the keywords given.
 */
public class TaskContainsKeywordsPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient person) {
        return keywords.stream().anyMatch(keyword ->
                person.getTasks().getInternalList().stream().anyMatch(
                        task -> StringUtil.containsWordIgnoreCase(task.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TaskContainsKeywordsPredicate) other).keywords)); // state check
    }
}
