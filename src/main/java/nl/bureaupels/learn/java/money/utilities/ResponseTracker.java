package nl.bureaupels.learn.java.money.utilities;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import nl.bureaupels.learn.java.money.annotations.TrackableProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static nl.bureaupels.learn.java.money.utilities.Quoter.quote;

@RequiredArgsConstructor
@Builder(builderClassName = "Builder")
public class ResponseTracker {

    private final String application;
    private final String feature;
    private final String operation;
    private final HttpMethod method;
    private final HttpStatus status;
    private final String message;
    private final Object trackable;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
            .append("TRACK:response application:").append(quote(application))
            .append(" feature:").append(quote(feature))
            .append(" operation:").append(quote(operation))
            .append(" method:").append(quote(method))
            .append(" status:").append(quote(status));
        if (StringUtils.isNotEmpty(message)) {
            sb.append(" message:").append(quote(message));
        }
        sb.append(TrackableProcessor.process(trackable));
        return sb.toString();
    }

    public static ResponseTracker.Builder from(RequestTracker requestTracker) {
        return ResponseTracker.builder()
            .application(requestTracker.getApplication())
            .feature(requestTracker.getFeature())
            .operation(requestTracker.getOperation())
            .method(requestTracker.getMethod());
    }

}
