package nl.bureaupels.learn.java.money.utilities;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseTrackerTest {
    @Test
    public void passPojoTests() {
        PojoClass personPojo = PojoClassFactory.getPojoClass(ResponseTracker.class);
        Validator validator = ValidatorBuilder.create()
            // Lets make sure that we have a getter and a setter for every field defined.
            .with(new GetterMustExistRule())
            .with(new SetterMustExistRule())
            // Lets also validate that they are behaving as expected
            .with(new SetterTester())
            .with(new GetterTester())
            .build();

        // Start the Test
        validator.validate(personPojo);
    }

    @Test
    public void builderTest() {
        ResponseTracker tracker = ResponseTracker.builder()
            .application("app")
            .feature("feature")
            .message("message")
            .method(HttpMethod.GET)
            .operation("op")
            .status(HttpStatus.I_AM_A_TEAPOT)
            .build();
        assertThat("can build", tracker, not(nullValue()));

        String trackstring = tracker.toString();
        assertThat("has application", trackstring, containsString("application:\"app"));
        assertThat("has feature", trackstring, containsString("feature:\"feature"));
        assertThat("has operation", trackstring, containsString("operation:\"op"));
        assertThat("has status", trackstring, containsString("status:\"418"));
        assertThat("has message", trackstring, containsString("message:\"message"));
    }
}
