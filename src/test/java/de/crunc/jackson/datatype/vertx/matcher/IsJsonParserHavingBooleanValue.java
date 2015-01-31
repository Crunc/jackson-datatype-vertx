package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is {@link JsonToken#VALUE_TRUE} or
 * {@link JsonToken#VALUE_FALSE} and {@link JsonParser#getBooleanValue()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingBooleanValue extends BaseJsonParserMatcher {

    private final Matcher<? super Boolean> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonParserHavingBooleanValue(Matcher<? super Boolean> matcher) {
        this.matcher = (Matcher<? super Boolean>) (matcher != null ? matcher : new IsNull<Boolean>());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has boolean value ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException {

        final JsonToken currentToken = parser.getCurrentToken();

        if (currentToken != JsonToken.VALUE_TRUE 
                && currentToken != JsonToken.VALUE_FALSE) {
            mismatch.appendText("current token ")
                    .appendValue(currentToken)
                    .appendText(" is none of ")
                    .appendValue(JsonToken.VALUE_TRUE)
                    .appendText(" / ")
                    .appendValue(JsonToken.VALUE_FALSE);
            return false;
        }

        final boolean boolValue = parser.getBooleanValue();

        if (!matcher.matches(boolValue)) {
            matcher.describeMismatch(boolValue, mismatch);
            return false;
        }

        return true;
    }
}
