package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonStreamContext;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matches {@link JsonStreamContext} if {@link JsonStreamContext#inObject()} is {@code true}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 1.0
 */
public class IsJsonStreamContextInObject extends TypeSafeDiagnosingMatcher<JsonStreamContext> {

    @Override
    public void describeTo(Description description) {
        description.appendText("of type OBJECT");
    }

    @Override
    protected boolean matchesSafely(JsonStreamContext context, Description mismatch) {

        if (context.inArray()) {
            mismatch.appendText("was of type ARRAY");
            return false;
        }

        if (context.inRoot()) {
            mismatch.appendText("was of type ROOT");
            return false;
        }

        if (!context.inObject()) {
            mismatch.appendText("was not of type OBJECT");
            return false;
        }

        return true;
    }
}
