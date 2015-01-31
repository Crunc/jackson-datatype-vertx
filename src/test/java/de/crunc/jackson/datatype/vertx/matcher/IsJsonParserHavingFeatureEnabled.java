package de.crunc.jackson.datatype.vertx.matcher;

import com.fasterxml.jackson.core.JsonParser;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import java.io.IOException;

/**
 * Matches {@link JsonParser} if {@link JsonParser#isEnabled(JsonParser.Feature)} is true for the given feature.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class IsJsonParserHavingFeatureEnabled extends BaseJsonParserMatcher {

    private final JsonParser.Feature feature;

    public IsJsonParserHavingFeatureEnabled(JsonParser.Feature feature) {
        this.feature = feature;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has feature enabled ")
                .appendValue(feature);
    }

    @Override
    protected boolean safeMatchesSafely(JsonParser parser, Description mismatch) throws IOException {

        if (!parser.isEnabled(feature)) {
            mismatch.appendValue(feature)
                    .appendText(" is not enabled");
            return false;
        }

        return true;
    }
}
