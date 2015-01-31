package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Matcher;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Matches {@link JsonParser} if {@link JsonParser#getCurrentToken()} is one of {@link JsonToken#VALUE_NUMBER_INT},
 * {@link JsonToken#VALUE_NUMBER_INT}, {@link JsonToken#VALUE_STRING} and {@link JsonParser#getBigIntegerValue()}
 * matches.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingBigIntegerValue extends IsJsonParserHavingNumberValue<BigInteger> {

    public IsJsonParserHavingBigIntegerValue(Matcher<? super BigInteger> matcher) {
        super(matcher);
    }

    @Override
    protected BigInteger getNumberValue(JsonParser parser) throws IOException {
        return parser.getBigIntegerValue();
    }
}
