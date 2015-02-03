package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Test;
import org.vertx.java.core.json.JsonArray;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.START_ARRAY;
import static de.crunc.jackson.datatype.vertx.JsonArrayBuilder.array;
import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static de.crunc.jackson.datatype.vertx.matcher.JsonParserMatchers.hasCurrentToken;
import static de.crunc.jackson.datatype.vertx.matcher.JsonParserMatchers.nextToken;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link JsonElementParser} for parsing {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementParserArrayTest extends JsonElementParserBaseTest {

    private JsonParser jp;
    
    @Test
    public void shouldSkipChildrenOfRootArray() throws IOException {
        jp = createParser(array()
                .add(1)
                .add(3)
                .add(array()
                        .add(true)
                        .addNull())
                .add(3)
                .add(object()
                        .put("a", "b"))
                .add(array()
                        .add(array()))
                .add(object()));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_ARRAY));

        jp.skipChildren();

        assertThat(jp, hasCurrentToken(END_ARRAY));

        assertThat(jp, nextToken(nullValue()));
    }

    @Test
    public void shouldSkipChildrenOfEmptyRootArray() throws IOException {
        jp = createParser(array());

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_ARRAY));

        jp.skipChildren();

        assertThat(jp, hasCurrentToken(END_ARRAY));

        assertThat(jp, nextToken(nullValue()));
    }

    @Test
    public void shouldSkipChildrenOfChildArray() throws IOException {
        jp = createParser(array()
                .add(array()
                        .add(true)
                        .add(42)
                        .addNull()));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_ARRAY));
        {
            assertThat(jp, nextToken(START_ARRAY));
            jp.skipChildren();
            assertThat(jp, hasCurrentToken(END_ARRAY));
        }
        assertThat(jp, nextToken(END_ARRAY));

        assertThat(jp, nextToken(nullValue()));
    }

    @Test
    public void shouldSkipChildrenOfEmptyChildArray() throws IOException {
        jp = createParser(array()
                .add(array()));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_ARRAY));
        {
            assertThat(jp, nextToken(START_ARRAY));
            jp.skipChildren();
            assertThat(jp, hasCurrentToken(END_ARRAY));
        }
        assertThat(jp, nextToken(END_ARRAY));

        assertThat(jp, nextToken(nullValue()));
    }
}
