package com.bobocode.se;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * If no field is available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 *<p><p>
 *  <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 *  <p>
 *
 * @author Stanislav Zabramnyi
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    private static final Predicate<Field> IS_COMPARABLE_OR_PRIMITIVE =
            f -> f.getType().isPrimitive() || Comparable.class.isAssignableFrom(f.getType());

    private static final String TO_STRING_TEMPLATE = "Random field comparator of class '%s' is comparing '%s'";

    private final Class<T> targetClass;
    private final Field field;

    public RandomFieldComparator(Class<T> targetType) {
        this.targetClass = targetType;
        this.field = getComparableField(targetClass);
    }

    private Field getComparableField(final Class<T> targetClass) {
        return Arrays.stream(targetClass.getDeclaredFields())
                .filter(IS_COMPARABLE_OR_PRIMITIVE)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("There is no primitive or Comparable fields"));
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value greater than a non-null value.
     *
     * @param o1
     * @param o2
     * @return positive int in case of first parameter {@param o1} is greater than second one {@param o2},
     *         zero if objects are equals,
     *         negative int in case of first parameter {@param o1} is less than second one {@param o2}.
     */
    @Override
    public int compare(T o1, T o2) {
        Objects.requireNonNull(o1);
        Objects.requireNonNull(o2);
        return compareFields(o1, o2);
    }

    private <C extends Comparable<? super C>> int compareFields(T o1, T o2) {
        final Comparator<C> comparator = Comparator.nullsLast(Comparator.naturalOrder());
        this.field.setAccessible(true);
        final C value1;
        final C value2;
        try {
            value1 = (C) this.field.get(o1);
            value2 = (C) this.field.get(o2);
        } catch(IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return comparator.compare(value1, value2);
    }

    /**
     * Returns the name of the randomly-chosen comparing field.
     */
    public String getComparingFieldName() {
        return this.field.getName();
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format(TO_STRING_TEMPLATE, this.targetClass.getSimpleName(), this.getComparingFieldName());
    }
}
