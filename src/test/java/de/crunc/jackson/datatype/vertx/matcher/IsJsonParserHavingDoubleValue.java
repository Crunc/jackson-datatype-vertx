package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is one of {@link JsonToken#VALUE_NUMBER_INT},
 * {@link JsonToken#VALUE_NUMBER_INT}, {@link JsonToken#VALUE_STRING} and {@link JsonParser#getDoubleValue()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingDoubleValue extends IsJsonParserHavingNumberValue<Double> {

    public IsJsonParserHavingDoubleValue(Matcher<? super Double> matcher) {
        super(matcher);
    }

    @Override
    protected Double getNumberValue(JsonParser parser) throws IOException {
        return parser.getDoubleValue();
    }
}
