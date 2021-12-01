package nl.bureaupels.learn.java.money.utilities;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

public final class Quoter {

    private static final Pattern PATTERN = Pattern.compile("\"");
    private static final String QUOTE = "\"";
    private static final String BSQUOTE = "\\\"";

    private Quoter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String quote(final String string) {
        return QUOTE + PATTERN.matcher(string).replaceAll(BSQUOTE) + QUOTE;
    }

    public static String quote(final HttpMethod httpMethod) {
        return quote(httpMethod.toString());
    }

    public static String quote(final HttpStatus httpStatus) {
        return quote(httpStatus.toString());
    }

    public static String quote(final int i) {
        return quote(String.valueOf(i));
    }

    public static String quote(final long l) {
        return quote(String.valueOf(l));
    }
}
