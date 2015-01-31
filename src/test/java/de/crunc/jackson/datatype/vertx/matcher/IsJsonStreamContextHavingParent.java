package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonStreamContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches {@link JsonStreamContext} if {@link JsonStreamContext#getParent()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 1.0
 */
public class IsJsonStreamContextHavingParent extends TypeSafeDiagnosingMatcher<JsonStreamContext> {

    private final Matcher<? super JsonStreamContext> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonStreamContextHavingParent(Matcher<? super JsonStreamContext> matcher) {
        this.matcher = (Matcher<? super JsonStreamContext>) (matcher != null ? matcher : nullValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has parent ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean matchesSafely(JsonStreamContext context, Description mismatch) {
        
        final JsonStreamContext value = context.getParent();
        
        if (!matcher.matches(value)) {
            mismatch.appendText("parent ");
            matcher.describeMismatch(value, mismatch);
            return false;
        }

        return true;
    }
}
