package tutorly.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorly.logic.parser.Prefix;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student already exists in the address book.";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_SESSION_ID = "The session ID provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_SESSIONS_LISTED_OVERVIEW = "%1$d sessions listed!";
    public static final String MESSAGE_PERSONS_SHOWN = "Showing persons";
    public static final String MESSAGE_SESSIONS_SHOWN = "Showing sessions";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("id: ")
                .append(person.getId())
                .append("; Name: ")
                .append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ")
                .append(person.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()))
                .append("; Memo: ")
                .append(person.getMemo());
        return builder.toString();
    }

    /**
     * Formats the {@code session} for display to the user.
     */
    public static String format(Session session) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(session.getDate())
                .append("; Subject: ")
                .append(session.getSubject());
        return builder.toString();
    }
}
