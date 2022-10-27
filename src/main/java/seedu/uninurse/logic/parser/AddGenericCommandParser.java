package seedu.uninurse.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIXES_PATIENT_ALL;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_OPTION_PATIENT_INDEX;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import seedu.uninurse.logic.commands.AddGenericCommand;
import seedu.uninurse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates either a new AddPatientCommand object or
 * a new AddTaskCommand object based on option values.
 */
public class AddGenericCommandParser implements Parser<AddGenericCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddGenericCommand
     * and returns an AddGenericCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGenericCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap options = ParserUtil.parseOptions(args, PREFIX_OPTION_PATIENT_INDEX);
        args = ParserUtil.eraseOptions(args, PREFIX_OPTION_PATIENT_INDEX);

        ArgumentMultimap parameters = ArgumentTokenizer.tokenize(args, PREFIXES_PATIENT_ALL);

        if (ParserUtil.optionsOnlyContains(options)) {
            return new AddPatientCommandParser().parse(args);
        }

        // args contain an option other than PATIENT_INDEX
        if (!ParserUtil.optionsOnlyContains(options, PREFIX_OPTION_PATIENT_INDEX)) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_OPTIONS);
        }

        String patientIndex = options.getValue(PREFIX_OPTION_PATIENT_INDEX).get();

        if (ParserUtil.parametersOnlyContains(parameters, PREFIX_TASK_DESCRIPTION)) {
            return new AddTaskCommandParser().parse(patientIndex + " " + args);
        }

        if (ParserUtil.parametersOnlyContains(parameters, PREFIX_TAG)) {
            return new AddTagCommandParser().parse(patientIndex + " " + args);
        }

        if (ParserUtil.parametersOnlyContains(parameters, PREFIX_CONDITION)) {
            return new AddConditionCommandParser().parse(patientIndex + " " + args);
        }

        if (ParserUtil.parametersOnlyContains(parameters, PREFIX_MEDICATION)) {
            return new AddMedicationCommandParser().parse(patientIndex + " " + args);
        }

        if (ParserUtil.parametersOnlyContains(parameters, PREFIX_REMARK)) {
            return new AddRemarkCommandParser().parse(patientIndex + " " + args);
        }

        throw new ParseException(ParserUtil.MESSAGE_INVALID_PARAMETERS);
    }
}
