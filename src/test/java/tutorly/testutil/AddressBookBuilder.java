package tutorly.testutil;

import tutorly.model.AddressBook;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Session} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withSession(Session session) {
        addressBook.addSession(session);
        return this;
    }

    /**
     * Adds a new {@code AttendanceRecord} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withAttendanceRecord(AttendanceRecord attendanceRecord) {
        addressBook.addAttendanceRecord(attendanceRecord);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
