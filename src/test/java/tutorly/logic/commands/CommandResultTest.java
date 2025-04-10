package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tutorly.ui.Tab;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + commandResult.getFeedbackToUser()
                + ", shouldShowHelp=" + commandResult.shouldShowHelp()
                + ", shouldExit=" + commandResult.shouldExit()
                + ", shouldReverseLast=" + commandResult.shouldReverseLast()
                + ", tab=null"
                + ", reverseCommand=null}";
        assertEquals(expected, commandResult.toString());

        commandResult = new CommandResult.Builder("feedback").withTab(Tab.student()).showHelp().build();
        expected = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + commandResult.getFeedbackToUser()
                + ", shouldShowHelp=" + commandResult.shouldShowHelp()
                + ", shouldExit=" + commandResult.shouldExit()
                + ", shouldReverseLast=" + commandResult.shouldReverseLast()
                + ", tab=" + commandResult.getTab()
                + ", reverseCommand=null}";
        assertEquals(expected, commandResult.toString());
    }
}
