package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Provides matchers for {@link JsonParser}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public final class JsonParserMatchers {

    // ----------------------------------------------------------------------------------------------------------------
    // <JsonParser>
    // ----------------------------------------------------------------------------------------------------------------

    public static Matcher<JsonParser> nextToken(JsonToken expectedToken) {
        return new IsJsonParserAdvancingToNextToken(expectedToken);
    }

    public static Matcher<JsonParser> nextToken(Matcher<? super JsonToken> matcher) {
        return new IsJsonParserAdvancingToNextToken(matcher);
    }

    public static Matcher<JsonParser> hasCurrentToken(JsonToken expectedToken) {
        return new IsJsonParserHavingCurrentToken(expectedToken);
    }

    public static Matcher<JsonParser> hasCurrentToken(Matcher<? super JsonToken> matcher) {
        return new IsJsonParserHavingCurrentToken(matcher);
    }

    public static Matcher<JsonParser> hasBoolValue(Boolean expectedValue) {
        return hasBoolValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasBoolValue(Matcher<? super Boolean> matcher) {
        return new IsJsonParserHavingBooleanValue(matcher);
    }

    public static Matcher<JsonParser> hasIntValue(Integer expectedValue) {
        return hasIntValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasIntValue(Matcher<? super Integer> matcher) {
        return new IsJsonParserHavingIntValue(matcher);
    }

    public static Matcher<JsonParser> hasLongValue(Long expectedValue) {
        return hasLongValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasLongValue(Matcher<? super Long> matcher) {
        return new IsJsonParserHavingLongValue(matcher);
    }

    public static Matcher<JsonParser> hasBigIntegerValue(BigInteger expectedValue) {
        return hasBigIntegerValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasBigIntegerValue(Matcher<? super BigInteger> matcher) {
        return new IsJsonParserHavingBigIntegerValue(matcher);
    }

    public static Matcher<JsonParser> hasFloatValue(Float expectedValue) {
        return hasFloatValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasFloatValue(Matcher<? super Float> matcher) {
        return new IsJsonParserHavingFloatValue(matcher);
    }

    public static Matcher<JsonParser> hasDoubleValue(Double expectedValue) {
        return hasDoubleValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasDoubleValue(Matcher<? super Double> matcher) {
        return new IsJsonParserHavingDoubleValue(matcher);
    }

    public static Matcher<JsonParser> hasBigDecimalValue(BigDecimal expectedValue) {
        return hasBigDecimalValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasBigDecimalValue(Matcher<? super BigDecimal> matcher) {
        return new IsJsonParserHavingBigDecimalValue(matcher);
    }

    public static Matcher<JsonParser> hasNullValue() {
        return new IsJsonParserHavingNullValue();
    }

    public static Matcher<JsonParser> hasFieldName(String expectedName) {
        return hasFieldName(equalTo(expectedName));
    }

    public static Matcher<JsonParser> hasFieldName(Matcher<? super String> matcher) {
        return Matchers.allOf(
                new IsJsonParserHavingCurrentName(matcher),
                new IsJsonParserHavingTextValue(matcher)
        );
    }

    public static Matcher<JsonParser> hasCurrentName(String expectedName) {
        return hasCurrentName(equalTo(expectedName));
    }

    public static Matcher<JsonParser> hasCurrentName(Matcher<? super String> matcher) {
        return new IsJsonParserHavingCurrentName(matcher);
    }

    public static Matcher<JsonParser> hasFeatureEnabled(JsonParser.Feature feature) {
        return new IsJsonParserHavingFeatureEnabled(feature);
    }

    public static Matcher<JsonParser> hasTextValue(String expectedValue) {
        return hasTextValue(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> hasTextValue(Matcher<? super String> matcher) {
        return new IsJsonParserHavingTextValue(matcher);
    }

    public static Matcher<JsonParser> context(JsonStreamContext expectedValue) {
        return context(equalTo(expectedValue));
    }

    public static Matcher<JsonParser> context(Matcher<? super JsonStreamContext> matcher) {
        return new IsJsonParserHavingParsingContext(matcher);
    }

    // ----------------------------------------------------------------------------------------------------------------
    // <JsonStreamContext>
    // ----------------------------------------------------------------------------------------------------------------

    public static Matcher<JsonStreamContext> inRoot() {
        return new IsJsonStreamContextInRoot();
    }

    public static Matcher<JsonStreamContext> inObject() {
        return new IsJsonStreamContextInObject();
    }

    public static Matcher<JsonStreamContext> inArray() {
        return new IsJsonStreamContextInArray();
    }

    public static Matcher<JsonStreamContext> entryCount(Integer expectedValue) {
        return entryCount(equalTo(expectedValue));
    }

    public static Matcher<JsonStreamContext> entryCount(Matcher<? super Integer> matcher) {
        return new IsJsonStreamContextHavingEntryCount(matcher);
    }

    public static Matcher<JsonStreamContext> currentIndex(Integer expectedValue) {
        return currentIndex(equalTo(expectedValue));
    }

    public static Matcher<JsonStreamContext> currentIndex(Matcher<? super Integer> matcher) {
        return new IsJsonStreamContextHavingCurrentIndex(matcher);
    }

    public static Matcher<JsonStreamContext> currentName(String expectedValue) {
        return currentName(equalTo(expectedValue));
    }

    public static Matcher<JsonStreamContext> currentName(Matcher<? super String> matcher) {
        return new IsJsonStreamContextHavingCurrentName(matcher);
    }

    public static Matcher<JsonStreamContext> typeDesc(String expectedValue) {
        return typeDesc(equalTo(expectedValue));
    }

    public static Matcher<JsonStreamContext> typeDesc(Matcher<? super String> matcher) {
        return new IsJsonStreamContextHavingTypeDesc(matcher);
    }

    public static Matcher<JsonStreamContext> parent(JsonStreamContext expectedValue) {
        return parent(equalTo(expectedValue));
    }

    public static Matcher<JsonStreamContext> parent(Matcher<? super JsonStreamContext> matcher) {
        return new IsJsonStreamContextHavingParent(matcher);
    }

    // ----------------------------------------------------------------------------------------------------------------
    // <JsonToken>
    // ----------------------------------------------------------------------------------------------------------------

    public static Matcher<JsonToken> valueToken() {
        return isOneOf(
                JsonToken.VALUE_NULL,
                JsonToken.VALUE_FALSE,
                JsonToken.VALUE_TRUE,
                JsonToken.VALUE_NUMBER_INT,
                JsonToken.VALUE_NUMBER_FLOAT,
                JsonToken.VALUE_STRING,
                JsonToken.VALUE_EMBEDDED_OBJECT
        );
    }

    private JsonParserMatchers() {
        throw new UnsupportedOperationException(JsonParserMatchers.class.getName() + " may not be instantiated");
    }
}
