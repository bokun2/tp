package hobbylist.logic.parser;

import hobbylist.commons.core.index.Index;
import hobbylist.commons.exceptions.IllegalValueException;
import hobbylist.logic.commands.FindCommand;
import hobbylist.logic.commands.RemarkCommand;
import hobbylist.logic.parser.exceptions.ParseException;
import hobbylist.model.activity.NameContainsKeywordsPredicate;
import hobbylist.model.activity.Remark;

import java.util.Arrays;

import static hobbylist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hobbylist.logic.parser.CliSyntax.PREFIX_REMARK;
import static java.util.Objects.requireNonNull;

public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), ive);
        }

        Remark remark = new Remark(argMultimap.getValue(PREFIX_REMARK).orElse(""));

        return new RemarkCommand(index, remark);
    }
}
