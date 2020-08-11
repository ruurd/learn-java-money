package nl.bureaupels.learn.java.money.utilities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.bureaupels.learn.java.money.annotations.TrackableProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import static nl.bureaupels.learn.java.money.utilities.Quoter.quote;

@Getter
@RequiredArgsConstructor
@Builder(builderClassName = "Builder")
public class RequestTracker {

    private final String application;
    private final String feature;
    private final String operation;
    private final HttpMethod method;
    private final String message;
    private final Object trackable;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
            .append("TRACK:request application:").append(quote(application))
            .append(" feature:").append(quote(feature))
            .append(" operation:").append(quote(operation))
            .append(" method:").append(quote(method));
        if (StringUtils.isNotEmpty(message)) {
            sb.append(" message:").append(quote(message));
        }
        sb.append(TrackableProcessor.process(trackable));
        return sb.toString();
    }
}
