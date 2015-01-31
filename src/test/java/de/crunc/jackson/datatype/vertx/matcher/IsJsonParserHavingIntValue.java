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
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is one of {@link JsonToken#VALUE_NUMBER_INT},
 * {@link JsonToken#VALUE_NUMBER_INT}, {@link JsonToken#VALUE_STRING} and {@link JsonParser#getIntValue()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingIntValue extends IsJsonParserHavingNumberValue<Integer> {

    public IsJsonParserHavingIntValue(Matcher<? super Integer> matcher) {
        super(matcher);
    }

    @Override
    protected Integer getNumberValue(JsonParser parser) throws IOException {
        return parser.getIntValue();
    }
}
