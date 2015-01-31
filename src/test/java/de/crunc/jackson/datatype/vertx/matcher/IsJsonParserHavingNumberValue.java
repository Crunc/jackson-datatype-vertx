package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is one of {@link JsonToken#VALUE_NUMBER_INT},
 * {@link JsonToken#VALUE_NUMBER_INT}, {@link JsonToken#VALUE_STRING} and {@link JsonParser#getIntValue()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public abstract class IsJsonParserHavingNumberValue<T extends Number> extends BaseJsonParserMatcher {

    private final Matcher<? super T> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonParserHavingNumberValue(Matcher<? super T> matcher) {
        this.matcher = (Matcher<? super T>) (matcher != null ? matcher : new IsNull<T>());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has number value ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException {

        if (!matchNumericToken(parser, mismatch)) {
            return false;
        }

        final T value = getNumberValue(parser);

        if (!matcher.matches(value)) {
            matcher.describeMismatch(value, mismatch);
            return false;
        }

        return true;
    }
    
    protected abstract T getNumberValue(JsonParser parser) throws IOException;
    
    protected final boolean matchNumericToken(JsonParser parser, Description mismatch) {
        final JsonToken currentToken = parser.getCurrentToken();
        
        if (currentToken != JsonToken.VALUE_NUMBER_INT
                && currentToken != JsonToken.VALUE_NUMBER_FLOAT
                && currentToken != JsonToken.VALUE_STRING) {
            mismatch.appendText("current token ")
                    .appendValue(currentToken)
                    .appendText(" is not a token of ")
                    .appendValue(JsonToken.VALUE_NUMBER_INT)
                    .appendText(" / ")
                    .appendValue(JsonToken.VALUE_NUMBER_FLOAT)
                    .appendText(" / ")
                    .appendValue(JsonToken.VALUE_STRING)
                    .appendText(")");
            return false;
        }
        
        return true;
    }
}
