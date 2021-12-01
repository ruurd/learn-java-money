package nl.bureaupels.learn.java.money.utilities;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UnquoterTest {

    @Test
    public void testUnquote() {
        assertThat("can unquote", Unquoter.unquote("\"foo\"bar\""), is("foo\"bar"));
    }

    @Test
    public void testQuoteUnquote() {
        assertThat("can quoteunquote", Unquoter.unquote(Quoter.quote("foo\"bar")), is("foo\"bar"));
    }

    @Test
    public void testUnquoteQuote() {
        assertThat("can unquotequote", Quoter.quote(Unquoter.unquote("\"foo\"bar\"")), is("\"foo\"bar\""));
    }

}
