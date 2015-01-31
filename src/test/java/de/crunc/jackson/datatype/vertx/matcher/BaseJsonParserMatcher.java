package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.IOException;

/**
 * Base class for {@link Matcher} that match {@link JsonParser}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public abstract class BaseJsonParserMatcher extends TypeSafeDiagnosingMatcher<JsonParser> {

    @Override
    protected final boolean matchesSafely(JsonParser item, Description mismatchDescription) {
        try {
            return safeMatchesSafely(item, mismatchDescription);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected abstract boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException;
}
