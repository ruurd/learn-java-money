package nl.bureaupels.learn.java.money.annotations;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class can process classes annotated with @Trackable at runtime.
 */
@UtilityClass
public class TrackableProcessor {

    /**
     * Inspect toTrack and process Countable, Summable and Tracked fields
     *
     * @param toTrack the object to process
     * @return a string containing the results.
     */
    public String process(Object toTrack) {
        StringBuilder sb = new StringBuilder();
        if (toTrack != null) {
            sb.append(" trackable: ")
                .append(toTrack.getClass().getSimpleName())
                .append(": {");
            if (toTrack.getClass().isAnnotationPresent(Trackable.class)) {
                // Output name of class
                // Iterate over all Tracked fields
                Field[] fields = toTrack.getClass().getDeclaredFields();
                Set<String> terms = new LinkedHashSet<>();
                for (Field field : fields) {

                    StringBuilder termBuilder = new StringBuilder();
                    try {
                        field.setAccessible(true);
                        Object value = field.get(toTrack);

                        if (field.isAnnotationPresent(Tracked.class)) {
                            termBuilder.append(field.getName()).append(": ").append(value);
                        } else if (field.isAnnotationPresent(Countable.class)) {
                            if (value instanceof Collection) {
                                termBuilder.append(field.getName())
                                    .append("[]: ")
                                    .append(((Collection<?>) value).size())
                                    .append(" elements");

                            }
                        } else if (field.isAnnotationPresent(Summable.class)) {
                            if (value instanceof Collection) {
                                termBuilder.append(field.getName())
                                    .append("[]: ")
                                    .append(((Collection<?>) value).size())
                                    .append(" elements")
                                    .append(summable((Collection<?>) value));
                            }
                        } else if (field.isAnnotationPresent(Trackable.class)) {
                            termBuilder.append(TrackableProcessor.process(value));
                        }
                    } catch (IllegalAccessException iax) {
                        termBuilder.append(field.getName()).append(" cannot be accessed");
                    }
                    String term = termBuilder.toString();
                    if (StringUtils.isNotEmpty(term)) {
                        terms.add(termBuilder.toString());
                    }
                }
                sb.append(String.join(", ", terms));
            } else {
                sb.append(toTrack);
            }
            sb.append("}");
        }
        return sb.toString();
    }

    /**
     * Iterate over c adding all countables of the same name to each other then print the sums
     *
     * @param collection the collection of values to check for countables
     * @return a string containing the results.
     */
    private String summable(Collection<?> collection) {
        StringBuilder sb = new StringBuilder(" with totals: {");
        Map<String, Long> sums = new HashMap<>();
        for (Object object : collection) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {

                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if ((field.isAnnotationPresent(Summable.class)) && (value instanceof Collection)) {
                        String key = field.getName();
                        long val = ((Collection<?>) value).size();

                        if (sums.containsKey(key)) {
                            val = Long.sum(val, sums.get(key));
                        }
                        sums.put(field.getName(), val);
                    }
                } catch (IllegalAccessException iax) {
                    sb.append(field.getName()).append(" cannot be accessed.");
                }
            }
        }
        sb.append(sums.entrySet().stream().map(e -> String.format("%s: %d", e.getKey(), e.getValue())).collect(Collectors.joining(", ")));
        sb.append("}");
        return sb.toString();
    }
}
