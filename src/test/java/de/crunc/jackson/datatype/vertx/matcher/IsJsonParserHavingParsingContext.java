package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import java.io.IOException;

import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getParsingContext()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingParsingContext extends BaseJsonParserMatcher {

    private final Matcher<? super JsonStreamContext> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonParserHavingParsingContext(Matcher<? super JsonStreamContext> matcher) {
        this.matcher = (Matcher<? super JsonStreamContext>) (matcher != null ? matcher : nullValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has parsing context ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException {

        final JsonStreamContext context = parser.getParsingContext();
        
        if (!matcher.matches(context)) {
            mismatch.appendText("context ");
            matcher.describeMismatch(context, mismatch);
            return false;
        }

        return true;
    }
}
