package nl.bureaupels.learn.java.money.utilities;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentHelperTest {

    @Test
    public void requireNonNull() {
        ArgumentHelper.requireNonNull("x");
        assertThat("no exception", true, is(true));
    }

    @Test
    public void requireNonNullNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireNonNull(null);
        });
    }

    @Test
    public void requireNotEmpty() {
        ArgumentHelper.requireNotEmpty("x");
        assertThat("no exception", true, is(true));
    }

    @Test
    public void requireNotEmptyNullMap() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireNotEmpty((Map) null);
        });
    }

    @Test
    public void requireNotEmptyNullCollection() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireNotEmpty((Collection) null);
        });
    }

    @Test
    public void requireNotEmptyBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireNotEmpty("");
        });
    }

    @Test
    public void requireLessThan() {
        ArgumentHelper.<String>requireLessThan("x", "y");
        assertThat("no exception", true, is(true));
    }

    @Test
    public void requireLessThanNope() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireLessThan(2, 1);
        });
    }

    @Test
    public void requireLessThanOrEqual() {
        ArgumentHelper.requireLessThanOrEqual("x", "y");
        ArgumentHelper.requireLessThanOrEqual("y", "y");
        assertThat("no exception", true, is(true));
    }

    @Test
    public void requireLessThanOrEqualNope() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireLessThanOrEqual(2, 1);
        });
    }

    @Test
    public void requireLessThanOrEqualNopeEqual() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireLessThanOrEqual(3, 2);
        });
    }

    @Test
    public void requireGreaterThanOrEqual() {
        ArgumentHelper.requireGreaterThanOrEqual(7, 4);
        ArgumentHelper.requireGreaterThanOrEqual(7, 7);
        assertThat("no exception", true, is(true));
    }

    @Test
    public void requireGreaterThanOrEqualNope() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireGreaterThanOrEqual(1, 2);
        });
    }

    @Test
    public void requireGreaterThan() {
        ArgumentHelper.requireGreaterThan(7, 4);
        assertThat("no exception", true, is(true));
    }

    @Test
    public void requireGreaterThanNopeEqual() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireGreaterThan(5, 5);
        });
    }

    @Test
    public void requireGreaterThanNope() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentHelper.requireGreaterThan(1, 2);
        });
    }
}
