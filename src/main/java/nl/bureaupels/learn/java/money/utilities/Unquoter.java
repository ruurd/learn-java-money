package nl.bureaupels.learn.java.money.utilities;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class Unquoter {

    private final Pattern PATTERN = Pattern.compile("\\\\\"");
    private final String QUOTE = "\"";

    public String unquote(final String s) {
        String u = removeQuotes(s);
        return PATTERN.matcher(u).replaceAll(QUOTE);
    }

    private String removeQuotes(String s) {
        String result = s;
        if ((s.startsWith(QUOTE)) && (s.endsWith(QUOTE))) {
            result = s.substring(1, s.length() - 1);
        }
        return result;
    }
}
