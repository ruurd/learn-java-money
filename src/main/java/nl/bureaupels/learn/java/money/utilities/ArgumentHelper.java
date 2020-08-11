package nl.bureaupels.learn.java.money.utilities;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@UtilityClass
@SuppressWarnings("WeakerAccess")
public class ArgumentHelper {

    public void requireNonNull(Object value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }

    public void requireNotEmpty(Object value) {
        if (value instanceof String) {
            if (StringUtils.isEmpty(value.toString())) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public <V> void requireNotEmpty(Collection<V> collection) {
        if (collection == null) {
            throw new IllegalArgumentException();
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public <K, V> void requireNotEmpty(Map<K, V> map) {
        if (map == null) {
            throw new IllegalArgumentException();
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public <T extends Comparable<T>> void requireLessThan(T left, T rite) {
        if (left.compareTo(rite) >= 0) {
            throw new IllegalArgumentException();
        }
    }

    public <T extends Comparable<T>> void requireLessThanOrEqual(T left, T rite) {
        if (left.compareTo(rite) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public <T extends Comparable<T>> void requireEqual(T left, T rite) {
        if (left.compareTo(rite) != 0) {
            throw new IllegalArgumentException();
        }
    }

    public <T extends Comparable<T>> void requireGreaterThanOrEqual(T left, T rite) {
        if (left.compareTo(rite) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public <T extends Comparable<T>> void requireGreaterThan(T left, T rite) {
        if (left.compareTo(rite) <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public void requireGreaterThan(LocalDate left, LocalDate rite) {
        if (!left.isAfter(rite)) {
            throw new IllegalArgumentException();
        }
    }
}
