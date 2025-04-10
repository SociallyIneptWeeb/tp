package tutorly.model.uniquelist;

import static java.util.Objects.requireNonNull;
import static tutorly.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import tutorly.commons.util.ObservableListUtil;
import tutorly.model.uniquelist.exceptions.DuplicateElementException;
import tutorly.model.uniquelist.exceptions.ElementNotFoundException;

/**
 * A list that enforces uniqueness between its elements and does not allow nulls.
 * An element is considered unique by comparing using {@code UniqueList<T>#isEquivalent(T, T)}. As such, adding and
 * updating of elements uses {@code UniqueList<T>#isEquivalent(T, T)} for equivalence so as to ensure that the element
 * being added or updated is unique in the UniqueList. However, the removal of an element uses {@code T#equals(Object)}
 * so as to ensure that the exact element will be removed.
 * Order can be enforced by implementing {@code UniqueList<T>#compare(T, T)}. This guarantees that the list will always
 * be sorted in the defined order.
 * <p>
 * Supports a minimal set of list operations.
 */
public class UniqueList<T> implements Iterable<T> {

    protected final ObservableList<T> internalList = ObservableListUtil.arrayList();
    protected final ObservableList<T> internalUnmodifiableList = ObservableListUtil.unmodifiableList(internalList);

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(element -> isEquivalent(element, toCheck));
    }

    /**
     * Returns the equivalent element in the list.
     */
    public Optional<T> find(T toFind) {
        requireNonNull(toFind);
        return internalList.stream()
                .filter(element -> isEquivalent(element, toFind))
                .findFirst();
    }

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateElementException();
        }

        internalList.add(toAdd);
        internalList.sort(this::compare);
    }

    /**
     * Replaces the element {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list.
     * The edited element must not be equivalent to another existing element in the list.
     */
    public void set(T target, T edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ElementNotFoundException();
        }

        if (!isEquivalent(target, edited) && contains(edited)) {
            throw new DuplicateElementException();
        }

        internalList.set(index, edited);
        internalList.sort(this::compare);
    }

    /**
     * Removes the matching element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ElementNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * {@code replacement} must not contain duplicate elements.
     */
    public void setAll(UniqueList<T> replacement) {
        setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * {@code replacement} must not contain duplicate elements.
     */
    public void setAll(List<T> replacement) {
        requireAllNonNull(replacement);
        if (!elementsAreUnique(replacement)) {
            throw new DuplicateElementException();
        }

        internalList.setAll(replacement);
        internalList.sort(this::compare);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the number of elements in the list. If the list contains more than {@code Integer.MAX_VALUE} elements,
     * returns {@code Integer.MAX_VALUE}.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueList otherUniqueList)) {
            return false;
        }

        return internalList.equals(otherUniqueList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if the list contains only unique elements.
     */
    private boolean elementsAreUnique(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (isEquivalent(list.get(i), list.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if two elements are equivalent, and false otherwise.
     */
    protected boolean isEquivalent(T element1, T element2) {
        return element1.equals(element2);
    }

    /**
     * Compares two elements and returns an integer indicating their order.
     *
     * @return A negative integer, zero, or a positive integer as the first argument is less than,
     *         equal to, or greater than the second.
     */
    protected int compare(T element1, T element2) {
        return 0;
    }
}
