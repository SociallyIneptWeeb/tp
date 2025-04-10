package tutorly.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorly.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalAddressBook.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Memo;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R!chel";
    private static final String INVALID_PHONE = "++651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = " ";
    private static final String INVALID_MEMO = " ";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_MEMO = BENSON.getMemo().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(-1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_MEMO);
        String expectedMessage = Person.MESSAGE_INVALID_ID;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_MEMO);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                1, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_MEMO);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_MEMO);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //    @Test
    //    public void toModelType_nullPhone_throwsIllegalValueException() {
    //        JsonAdaptedPerson person = new JsonAdaptedPerson(
    //                VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_MEMO);
    //        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
    //        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    //    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_MEMO);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //    @Test
    //    public void toModelType_nullEmail_throwsIllegalValueException() {
    //        JsonAdaptedPerson person = new JsonAdaptedPerson(
    //                VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS, VALID_MEMO);
    //        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
    //        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    //    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS, VALID_MEMO);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //    @Test
    //    public void toModelType_nullAddress_throwsIllegalValueException() {
    //        JsonAdaptedPerson person = new JsonAdaptedPerson(
    //                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS, VALID_MEMO);
    //        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
    //        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    //    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags, VALID_MEMO);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidMemo_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, INVALID_MEMO);
        String expectedMessage = Memo.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //    @Test
    //    public void toModelType_nullMemo_throwsIllegalValueException() {
    //        JsonAdaptedPerson person = new JsonAdaptedPerson(
    //                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null);
    //        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Memo.class.getSimpleName());
    //        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    //    }

}
