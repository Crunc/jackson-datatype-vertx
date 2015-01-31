package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#nextToken()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserAdvancingToNextToken extends TypeSafeMatcher<JsonParser> {

    private final Matcher<? super JsonToken> matcher;
    private JsonToken token;

    public IsJsonParserAdvancingToNextToken(JsonToken expectedToken) {
        this(new IsEqual<JsonToken>(expectedToken));
    }

    @SuppressWarnings("unchecked")
    public IsJsonParserAdvancingToNextToken(Matcher<? super JsonToken> matcher) {
        this.matcher = (Matcher<? super JsonToken>) (matcher != null ? matcher : new IsNull<JsonToken>());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("advance to next token: ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean matchesSafely(JsonParser parser) {
        try {
            token = parser.nextToken();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        return matcher.matches(token);
    }

    @Override
    protected void describeMismatchSafely(JsonParser parser, Description mismatchDescription) {
        mismatchDescription.appendText("token ");
        matcher.describeMismatch(token, mismatchDescription);
    }
}
