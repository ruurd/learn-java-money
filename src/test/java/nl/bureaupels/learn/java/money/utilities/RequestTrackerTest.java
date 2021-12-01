package nl.bureaupels.learn.java.money.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestTrackerTest {
    @Test
    public void passPojoTests() {
    }

    @Test
    public void builderTest() {
        RequestTracker tracker = RequestTracker.builder()
            .application("app")
            .feature("feature")
            .message("message")
            .method(HttpMethod.GET)
            .operation("op")
            .build();
        assertThat("can build", tracker, not(nullValue()));
        assertThat("has application", tracker.toString(), containsString("application:\"app"));
        assertThat("has feature", tracker.toString(), containsString("feature:\"feature"));
        assertThat("has operation", tracker.toString(), containsString("operation:\"op"));
        assertThat("has message", tracker.toString(), containsString("message:\"message"));
    }
}
