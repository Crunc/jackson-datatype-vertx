package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is {@link JsonToken#VALUE_STRING} and accessing
 * the text value via {@link JsonParser#getText()} retrieves text equal to the text retrieved via
 * * {@link JsonParser#getTextCharacters()} / {@link JsonParser#getTextOffset()} / {@link JsonParser#getTextLength()}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingTextValue extends BaseJsonParserMatcher {

    private final Matcher<? super String> matcher;

    @SuppressWarnings("unchecked")
    public IsJsonParserHavingTextValue(Matcher<? super String> matcher) {
        this.matcher = (Matcher<? super String>) (matcher != null ? matcher : new IsNull<String>());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has <text> value ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException {

        final String text = parser.getText();

        if (!matcher.matches(text)) {
            mismatch.appendText("<text> value ");
            matcher.describeMismatch(text, mismatch);
            return false;
        }

        final int textLen = parser.getTextLength();
        final int textOffset = parser.getTextOffset();
        final char[] textChars = parser.getTextCharacters();

        if (textChars == null) {
            mismatch.appendText("<textCharacters> are <null>");
            return false;
        }

        if (textLen < 0) {
            mismatch.appendText("<textLength> is less than <0>");
            return false;
        }

        if (textOffset < 0) {
            mismatch.appendText("<textOffset> is less than <0>");
            return false;
        }

        final String bufferedText = new String(textChars, textOffset, textLen);

        if (!matcher.matches(bufferedText)) {
            mismatch.appendText("<textCharacters> value from ")
                    .appendValue(textOffset)
                    .appendText(" to ")
                    .appendValue(textOffset + textLen)
                    .appendText(" ");
            matcher.describeMismatch(text, mismatch);
            return false;
        }

        return true;
    }
}
