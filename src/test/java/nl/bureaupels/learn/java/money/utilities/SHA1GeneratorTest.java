package nl.bureaupels.learn.java.money.utilities;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SHA1GeneratorTest {

    @Mock
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

    @Mock
    BufferedReader bufferedReader = mock(BufferedReader.class);

    Vector<String> headers;
    Vector<String> parameters;
    List<String> lines;


    @BeforeEach
    public void before() throws IOException {

        when(httpServletRequest.getContextPath()).thenReturn("/context");
        when(httpServletRequest.getPathTranslated()).thenReturn("/qux/fiz");
        when(httpServletRequest.getQueryString()).thenReturn("?question=meaning&answer=42");

        // PATH/context/qux/fiz/?question=meaning&answer=42

        headers = new Vector<>();
        headers.addAll(Arrays.asList("foo", "bar"));
        when(httpServletRequest.getHeaderNames()).thenReturn(headers.elements());
        when(httpServletRequest.getHeader(eq("foo"))).thenReturn("fooval");
        when(httpServletRequest.getHeader(eq("bar"))).thenReturn("barval");

        // HEADERSfoofoovalbarbarval

        parameters = new Vector<>();
        parameters.addAll(Arrays.asList("classic", "jazz"));
        when(httpServletRequest.getParameterNames()).thenReturn(parameters.elements());
        when(httpServletRequest.getParameter(eq("classic"))).thenReturn("bach");
        when(httpServletRequest.getParameter(eq("jazz"))).thenReturn("davis");

        lines = new ArrayList<>();
        lines.addAll(Arrays.asList("line1", "line2", "line3", "line4", "line5", "line6", "line7"));
        when(httpServletRequest.getReader()).thenReturn(bufferedReader);
        when(bufferedReader.lines()).thenReturn(lines.stream());
    }

    @Test
    public void testString() {
        assertThat("digests are equal",
            SHA1Generator.generateFrom("lifetheuniverseandeverything"),
            is(DigestUtils.sha1Hex("lifetheuniverseandeverything")));
    }

    @Test
    public void testHttpServletRequest() {
        assertThat("request digest ok",
            SHA1Generator.generateFrom(httpServletRequest),
            is(DigestUtils.sha1Hex("PATH/context/qux/fiz?question=meaning&answer=42HEADERSfoofoovalbarbarvalPARAMETERSclassicbachjazzdavisBODYline1line2line3line4line5line6line7")));
    }
}
