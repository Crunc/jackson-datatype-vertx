package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is {@link JsonToken#VALUE_TRUE} or
 * {@link JsonToken#VALUE_FALSE} and {@link JsonParser#getBooleanValue()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingNullValue extends BaseJsonParserMatcher {

    @Override
    public void describeTo(Description description) {
        description.appendText("has <null> value");
    }

    @Override
    protected boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException {

        final JsonToken currentToken = parser.getCurrentToken();

        if (currentToken != JsonToken.VALUE_NULL) {
            mismatch.appendText("current token ")
                    .appendValue(currentToken)
                    .appendText(" is not ")
                    .appendValue(JsonToken.VALUE_NULL);
            return false;
        }

        return true;
    }
}
