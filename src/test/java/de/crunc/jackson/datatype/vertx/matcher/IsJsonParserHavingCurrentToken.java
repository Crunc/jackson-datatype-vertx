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
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingCurrentToken extends TypeSafeDiagnosingMatcher<JsonParser> {

    private final Matcher<? super JsonToken> matcher;

    public IsJsonParserHavingCurrentToken(JsonToken expectedToken) {
        this(new IsEqual<JsonToken>(expectedToken));
    }

    @SuppressWarnings("unchecked")
    public IsJsonParserHavingCurrentToken(Matcher<? super JsonToken> matcher) {
        this.matcher = (Matcher<? super JsonToken>) (matcher != null ? matcher : new IsNull<JsonToken>());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("have next token ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean matchesSafely(JsonParser item, Description mismatchDescription) {

        final JsonToken nextToken = item.getCurrentToken();

        if (!matcher.matches(nextToken)) {
            matcher.describeMismatch(nextToken, mismatchDescription);
            return false;
        }

        return true;
    }
}
