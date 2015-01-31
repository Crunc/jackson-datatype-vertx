package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Test;

import static com.fasterxml.jackson.core.JsonToken.*;
import static de.crunc.jackson.datatype.vertx.JsonArrayBuilder.array;
import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static de.crunc.jackson.datatype.vertx.matcher.JsonParserMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

/**
 * Unit test for {@link JsonElementParser}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementParserContextTest extends JsonElementParserBaseTest {

    private JsonParser jp;

    @Test
    public void shouldBeInRootAfterCreation() {
        jp = createParser(object());

        assertThat(jp, context(inRoot()));
    }

    @Test
    public void shouldBeInRootAfterFullIteration() {
        jp = createParser(object()
                .put("key1", object())
                .put("key2", object()));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_OBJECT));
            assertThat(jp, nextToken(END_OBJECT));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_OBJECT));
            assertThat(jp, nextToken(END_OBJECT));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, nextToken(nullValue()));

        assertThat(jp, context(inRoot()));
    }

    @Test
    public void shouldBeInObjectWhenAtEmptyObjectStart() {
        jp = createParser(object());

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(inObject()));
    }

    @Test
    public void shouldBeInObjectWhenAtEmptyObjectEnd() {
        jp = createParser(object());

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(inObject()));
    }

    @Test
    public void shouldBeInObjectWhenAtObjectStart() {
        jp = createParser(object()
                .put("key1", 1)
                .put("key2", "foobar")
                .put("key3", 17.78)
                .put("key4", true)
                .put("key5", false)
                .putNull("key6"));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(inObject()));
    }

    @Test
    public void shouldBeInObjectWhenWithinObjectAndAtObjectEnd() {
        jp = createParser(object()
                .put("key1", 1)
                .put("key2", "foobar")
                .put("key3", 17.78)
                .put("key4", true)
                .put("key5", false)
                .putNull("key6"));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inObject()));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(inObject()));
    }

    @Test
    public void shouldBeInArrayWhenAtEmptyArrayStart() {
        jp = createParser(array());

        assertThat(jp, nextToken(JsonToken.START_ARRAY));
        assertThat(jp, context(inArray()));
    }

    @Test
    public void shouldBeInArrayWhenAtEmptyArrayEnd() {
        jp = createParser(array());

        assertThat(jp, nextToken(JsonToken.START_ARRAY));
        assertThat(jp, nextToken(JsonToken.END_ARRAY));
        assertThat(jp, context(inArray()));
    }

    @Test
    public void shouldBeInArrayWhenAtArrayStart() {
        jp = createParser(array()
                .add(1)
                .add("B")
                .add(3.141)
                .add(true)
                .add(false)
                .addNull());

        assertThat(jp, nextToken(JsonToken.START_ARRAY));
        assertThat(jp, context(inArray()));
    }

    @Test
    public void shouldBeInArrayWhenWithinArrayAndAtArrayEnd() {
        jp = createParser(array()
                .add(1)
                .add("B")
                .add(3.141)
                .add(true)
                .add(false)
                .addNull());

        assertThat(jp, nextToken(JsonToken.START_ARRAY));
        {
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inArray()));
            
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inArray()));

            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inArray()));

            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inArray()));

            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inArray()));

            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(inArray()));
        }
        assertThat(jp, nextToken(JsonToken.END_ARRAY));
        assertThat(jp, context(inArray()));
    }
}
