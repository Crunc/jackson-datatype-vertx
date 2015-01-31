package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonStreamContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches {@link JsonStreamContext} if {@link JsonStreamContext#getTypeDesc()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 1.0
 */
public class IsJsonStreamContextHavingTypeDesc extends TypeSafeDiagnosingMatcher<JsonStreamContext> {

    private final Matcher<? super String> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonStreamContextHavingTypeDesc(Matcher<? super String> matcher) {
        this.matcher = (Matcher<? super String>) (matcher != null ? matcher : nullValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has type desc ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean matchesSafely(JsonStreamContext context, Description mismatch) {
        
        final String value = context.getTypeDesc();
        
        if (!matcher.matches(value)) {
            mismatch.appendText("type desc was ");
            matcher.describeMismatch(value, mismatch);
            return false;
        }

        return true;
    }
}
