package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Matcher;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is one of {@link JsonToken#VALUE_NUMBER_INT},
 * {@link JsonToken#VALUE_NUMBER_INT}, {@link JsonToken#VALUE_STRING} and {@link JsonParser#getDecimalValue()} matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingBigDecimalValue extends IsJsonParserHavingNumberValue<BigDecimal> {

    public IsJsonParserHavingBigDecimalValue(Matcher<? super BigDecimal> matcher) {
        super(matcher);
    }

    @Override
    protected BigDecimal getNumberValue(JsonParser parser) throws IOException {
        return parser.getDecimalValue();
    }
}
