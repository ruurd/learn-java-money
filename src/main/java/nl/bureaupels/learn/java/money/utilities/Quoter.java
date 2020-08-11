package nl.bureaupels.learn.java.money.utilities;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

@UtilityClass
public class Quoter {

    private final Pattern PATTERN = Pattern.compile("\"");
    private final String QUOTE = "\"";
    private final String BSQUOTE = "\\\"";

    public String quote(final String string) {
        return QUOTE + PATTERN.matcher(string).replaceAll(BSQUOTE) + QUOTE;
    }

    public String quote(final HttpMethod httpMethod) {
        return quote(httpMethod.toString());
    }

    public String quote(final HttpStatus httpStatus) {
        return quote(httpStatus.toString());
    }

    public String quote(final int i) {
        return quote(String.valueOf(i));
    }

    public String quote(final long l) {
        return quote(String.valueOf(l));
    }
}
