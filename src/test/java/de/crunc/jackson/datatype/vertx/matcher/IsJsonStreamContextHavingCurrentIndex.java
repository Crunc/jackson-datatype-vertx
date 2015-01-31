package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonStreamContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches {@link JsonStreamContext} if {@link JsonStreamContext#getCurrentIndex()}  matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 1.0
 */
public class IsJsonStreamContextHavingCurrentIndex extends TypeSafeDiagnosingMatcher<JsonStreamContext> {

    private final Matcher<? super Integer> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonStreamContextHavingCurrentIndex(Matcher<? super Integer> matcher) {
        this.matcher = (Matcher<? super Integer>) (matcher != null ? matcher : nullValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has current index ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean matchesSafely(JsonStreamContext context, Description mismatch) {
        
        final int value = context.getCurrentIndex();
        
        if (!matcher.matches(value)) {
            mismatch.appendText("current index was ");
            matcher.describeMismatch(value, mismatch);
            return false;
        }

        return true;
    }
}
