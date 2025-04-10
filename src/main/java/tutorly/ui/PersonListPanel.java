package tutorly.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import tutorly.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends ListPanel<Person> {

    public PersonListPanel(ObservableList<Person> personList) {
        super(personList);
    }

    @Override
    protected UiPart<Region> getItemGraphic(Person person) {
        return new PersonCard(person, getSelected().contains(person));
    };

}
