package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Test;

import java.io.IOException;

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
public class JsonElementParserArrayContextTest extends JsonElementParserBaseTest {

    private JsonParser jp;

    @Test
    public void shouldBeInRootAfterCreation() {
        jp = createParser(array());

        assertThat(jp, context(inRoot()));
    }

    @Test
    public void shouldBeInRootAfterFullIteration() {
        jp = createParser(array()
                .add(object())
                .add(array()));

        assertThat(jp, nextToken(START_ARRAY));
        {
            assertThat(jp, nextToken(startToken()));
            assertThat(jp, nextToken(endToken()));

            assertThat(jp, nextToken(startToken()));
            assertThat(jp, nextToken(endToken()));
        }
        assertThat(jp, nextToken(END_ARRAY));
        assertThat(jp, nextToken(nullValue()));

        assertThat(jp, context(inRoot()));
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

    @Test
    public void shouldBeInObjectWhenAtEmptyObjectItem() {
        jp = createParser(array()
                .add(object()));

        assertThat(jp, nextToken(JsonToken.START_ARRAY));
        assertThat(jp, context(inArray()));
        {
            assertThat(jp, nextToken(START_OBJECT));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(END_OBJECT));
            assertThat(jp, context(inObject()));
        }
        assertThat(jp, nextToken(JsonToken.END_ARRAY));
        assertThat(jp, context(inArray()));
    }

    @Test
    public void shouldBeInObjectWhenAtObjectItem() {
        jp = createParser(array()
                .add(object()
                        .put("Foo", "Bar")));

        assertThat(jp, nextToken(JsonToken.START_ARRAY));
        assertThat(jp, context(inArray()));
        {
            assertThat(jp, nextToken(START_OBJECT));
            assertThat(jp, context(inObject()));
            {
                assertThat(jp, nextToken(FIELD_NAME));
                assertThat(jp, context(inObject()));

                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(inObject()));
            }
            assertThat(jp, nextToken(END_OBJECT));
            assertThat(jp, context(inObject()));
        }
        assertThat(jp, nextToken(JsonToken.END_ARRAY));
        assertThat(jp, context(inArray()));
    }
}
