package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Test;
import io.vertx.core.json.JsonArray;

import static com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE;
import static de.crunc.jackson.datatype.vertx.matcher.JsonParserMatchers.hasFeatureEnabled;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link JsonElementParser} for enabling/disabling features.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementParserConfigurationTest extends JsonElementParserBaseTest {

    private JsonParser jp;

    @Test
    public void enableShouldSetFeatureToTrue() {
        jp = createParser(new JsonArray());

        jp.enable(AUTO_CLOSE_SOURCE);
        assertThat(jp, hasFeatureEnabled(AUTO_CLOSE_SOURCE));
    }

    @Test
    public void disableShouldSetFeatureToFalse() {
        jp = createParser(new JsonArray());
        jp.enable(AUTO_CLOSE_SOURCE);

        jp.disable(AUTO_CLOSE_SOURCE);
        assertThat(jp, not(hasFeatureEnabled(AUTO_CLOSE_SOURCE)));
    }

    @Test
    public void configureTrueShouldEnableFeature() {
        jp = createParser(new JsonArray());

        jp.configure(AUTO_CLOSE_SOURCE, true);
        assertThat(jp, hasFeatureEnabled(AUTO_CLOSE_SOURCE));
    }

    @Test
    public void configureFalseShouldDisableFeature() {
        jp = createParser(new JsonArray());

        jp.configure(AUTO_CLOSE_SOURCE, false);
        assertThat(jp, not(hasFeatureEnabled(AUTO_CLOSE_SOURCE)));
    }
}
