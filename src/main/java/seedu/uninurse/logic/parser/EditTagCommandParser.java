package seedu.uninurse.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.uninurse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.NoSuchElementException;

import seedu.uninurse.commons.core.index.Index;
import seedu.uninurse.logic.commands.EditTagCommand;
import seedu.uninurse.logic.parser.exceptions.ParseException;
import seedu.uninurse.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditTagCommand object.
 */
public class EditTagCommandParser implements Parser<EditTagCommand> {
    public static final String MESSAGE_MISSING_TAG = "Tag to edit must be provided.";

    /**
     * Parses the given {@code String} of arguments in the context of the EditTagCommand
     * and returns an EditTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        try {
            List<Index> indices = ParserUtil.parseTwoIndex(argMultimap.getPreamble());
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).orElseThrow());
            return new EditTagCommand(indices.get(0), indices.get(1), tag);
            // TODO: display specific error message for invalid tag
        } catch (ParseException pe) {
            // handles invalid indices and tags
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTagCommand.MESSAGE_USAGE), pe);
        } catch (NoSuchElementException nse) {
            // Handles missing prefix
            throw new ParseException(MESSAGE_MISSING_TAG);
        }
    }
}
