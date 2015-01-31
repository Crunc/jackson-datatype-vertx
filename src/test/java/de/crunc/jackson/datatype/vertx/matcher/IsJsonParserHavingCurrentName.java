package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentName()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingCurrentName extends BaseJsonParserMatcher {

    private final Matcher<? super String> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonParserHavingCurrentName(Matcher<? super String> matcher) {
        this.matcher = (Matcher<? super String>) (matcher != null ? matcher : new IsNull<Integer>());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has currentName ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException {

        final String currentName = parser.getCurrentName();

        if (!matcher.matches(currentName)) {
            mismatch.appendText("currentName ");
            matcher.describeMismatch(currentName, mismatch);
            return false;
        }

        return true;
    }
}
