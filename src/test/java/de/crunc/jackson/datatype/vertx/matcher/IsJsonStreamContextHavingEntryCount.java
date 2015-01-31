package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonStreamContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches {@link JsonStreamContext} if {@link JsonStreamContext#getEntryCount()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 1.0
 */
public class IsJsonStreamContextHavingEntryCount extends TypeSafeDiagnosingMatcher<JsonStreamContext> {

    private final Matcher<? super Integer> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonStreamContextHavingEntryCount(Matcher<? super Integer> matcher) {
        this.matcher = (Matcher<? super Integer>) (matcher != null ? matcher : nullValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has entry count ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean matchesSafely(JsonStreamContext context, Description mismatch) {
        
        final int value = context.getEntryCount();
        
        if (!matcher.matches(value)) {
            mismatch.appendText("entry count was ");
            matcher.describeMismatch(value, mismatch);
            return false;
        }

        return true;
    }
}
