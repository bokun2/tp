package hobbylist.logic.parser;

import static hobbylist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hobbylist.commons.core.Messages.MESSAGE_INVALID_RATING;

import java.util.Arrays;

import hobbylist.logic.commands.FindCommand;
import hobbylist.logic.parser.exceptions.ParseException;
import hobbylist.model.activity.NameOrDescContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        checkForFindRate(trimmedArgs);
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindCommand(new NameOrDescContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
    /**
     * @throws ParseException if the input is empty, or containing strings, or rate value above 5 or below 0.
     */
    public void checkForFindRate(String trimmedArgs) throws ParseException {
        if (trimmedArgs.contains("rate/")) {
            String[] temp = trimmedArgs.split("/");
            if (temp.length != 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            try {
                Integer.valueOf(temp[1]);
            } catch (NumberFormatException e) {
                throw new ParseException(
                        MESSAGE_INVALID_RATING);
            }
            int rateValue = Integer.valueOf(temp[1]);
            if (rateValue > 5 || rateValue < 0) {
                throw new ParseException(
                        MESSAGE_INVALID_RATING);
            }
        }
    }
}
