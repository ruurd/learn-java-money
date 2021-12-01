package nl.bureaupels.learn.java.money.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class QuoterTest {

    @Test
    public void testStringQuoting() {
        assertThat("properly quoted", Quoter.quote("foo\"bar"), is("\"foo\"bar\""));
    }

    @Test
    public void testHttpMethodQuoting() {
        assertThat("properly quoted", Quoter.quote(HttpMethod.PATCH), is("\"PATCH\""));
    }

    @Test
    public void testHttpStatusQuoting() {
        assertThat("properly quoted", Quoter.quote(HttpStatus.I_AM_A_TEAPOT), is("\"418 I_AM_A_TEAPOT\""));
    }

    @Test
    public void testIntQuoting() {
        assertThat("properly quoted", Quoter.quote(42), is("\"42\""));
    }

    @Test
    public void testLongQuoting() {
        assertThat("properly quoted", Quoter.quote(42L), is("\"42\""));
    }

}
