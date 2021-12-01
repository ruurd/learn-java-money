package nl.bureaupels.learn.java.money.annotations;

import lombok.RequiredArgsConstructor;
import lombok.Singular;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackableProcessorTest {

    @RequiredArgsConstructor
    @Trackable
    static class TrackedSubject {
        @Tracked
        private final String foo;
    }

    @Test
    public void processTracked() {
        TrackedSubject subject = new TrackedSubject("fubar");
        String result = TrackableProcessor.process(subject);
        assertThat("contains fubar", result, containsString("fubar"));
    }

    @RequiredArgsConstructor
    @Trackable
    static class CountableSubject {
        @Countable
        @Summable
        @Singular
        private final List<String> foos;
    }

    @Test
    public void processCountable() {
        CountableSubject subject = new CountableSubject(Arrays.asList("one", "two", "three"));
        String result = TrackableProcessor.process(subject);
        assertThat("contains foos[]: 3", result, containsString("foos[]: 3 elements"));
    }

    @RequiredArgsConstructor
    @Trackable
    class SummableSubject {
        @Summable
        @Singular
        private final List<CountableSubject> countables;
    }

    @Test
    public void processSummable() {
        SummableSubject subject = new SummableSubject(Arrays.asList(
            new CountableSubject(Arrays.asList("one", "two", "three")),
            new CountableSubject(Arrays.asList("four", "five", "six")),
            new CountableSubject(Arrays.asList("seven", "eight", "nine"))
        ));
        String result = TrackableProcessor.process(subject);
        assertThat("contains totals: {foos:  9}", result, containsString("totals: {foos: 9}"));
    }
}
