package seedu.uninurse.model.condition;

import java.util.List;
import java.util.function.Predicate;

import seedu.uninurse.commons.util.StringUtil;
import seedu.uninurse.model.person.Patient;

/**
 * Tests that a {@code Patient}'s {@code Condition} matches any of the keywords given.
 */
public class ConditionContainsKeywordsPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public ConditionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient person) {
        return keywords.stream().anyMatch(keyword ->
                person.getConditions().getInternalList().stream().anyMatch(
                        condition -> StringUtil.containsWordIgnoreCase(condition.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConditionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ConditionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
