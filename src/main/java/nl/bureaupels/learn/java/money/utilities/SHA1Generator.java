package nl.bureaupels.learn.java.money.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
public final class SHA1Generator {

    private SHA1Generator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String generateFrom(String s) {
        return DigestUtils.sha1Hex(s);
    }

    public static String generateFrom(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("PATH");
        sb.append(request.getContextPath());
        sb.append(request.getPathTranslated());
        sb.append(request.getQueryString());
        sb.append("HEADERS");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = request.getHeaderNames().nextElement();
            String headerValue = request.getHeader(headerName);
            sb.append(headerName).append(headerValue);
        }
        sb.append("PARAMETERS");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = request.getParameterNames().nextElement();
            String parameterValue = request.getParameter(parameterName);
            sb.append(parameterName).append(parameterValue);
        }
        try {
            sb.append("BODY");
            request.getReader().lines().forEach(sb::append);
        } catch (IOException iox) {
            // ***NOP***
        }

        String digest = DigestUtils.sha1Hex(sb.toString());
        log.debug(digest);
        return digest;
    }
}
