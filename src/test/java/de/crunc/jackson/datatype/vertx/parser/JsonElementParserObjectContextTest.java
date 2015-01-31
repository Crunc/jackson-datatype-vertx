package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
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
public class JsonElementParserObjectContextTest extends JsonElementParserBaseTest {

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
                .put("key2", array()));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(startToken()));
            assertThat(jp, nextToken(endToken()));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(startToken()));
            assertThat(jp, nextToken(endToken()));
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
    public void shouldBeInObjectWhenAtEmptyArrayFieldName() throws IOException {
        jp = createParser(object()
                .put("ChildArray", array()));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(inObject()));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(START_ARRAY));
            assertThat(jp, context(inArray()));
            assertThat(jp, nextToken(END_ARRAY));
            assertThat(jp, context(inArray()));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(inObject()));
    }

    @Test
    public void shouldBeInObjectWhenAtArrayFieldName() throws IOException {
        jp = createParser(object()
                .put("ChildArray", array()
                        .add(1)
                        .add(2)));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(inObject()));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(inObject()));

            assertThat(jp, nextToken(START_ARRAY));
            assertThat(jp, context(inArray()));
            {
                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(inArray()));

                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(inArray()));
            }
            assertThat(jp, nextToken(END_ARRAY));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(inObject()));
    }

    @Test
    public void shouldCountEmptyObjectEntries() {
        jp = createParser(object());

        assertThat(jp, context(entryCount(0)));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(entryCount(0)));
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(entryCount(0)));

        assertThat(jp, nextToken(nullValue()));
        assertThat(jp, context(entryCount(1)));
    }
                
    @Test
    public void shouldCountObjectEntries() {
        jp = createParser(object()
                .put("key1", 1)
                .put("key2", "foobar")
                .put("key3", 17.78)
                .put("key4", true)
                .put("key5", false)
                .putNull("key6"));

        assertThat(jp, context(entryCount(0)));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(entryCount(0)));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(1)));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(entryCount(1)));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(2)));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(entryCount(2)));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(3)));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(entryCount(3)));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(4)));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(entryCount(4)));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(5)));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(entryCount(5)));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(6)));
            assertThat(jp, nextToken(valueToken()));
            assertThat(jp, context(entryCount(6)));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(entryCount(6)));

        assertThat(jp, nextToken(nullValue()));
        assertThat(jp, context(entryCount(1)));
    }

    @Test
    public void shouldCountEmptyChildObjectEntries() {
        jp = createParser(object()
                .put("ChildObject", object()));

        assertThat(jp, context(entryCount(0)));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(entryCount(0)));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(1)));
            assertThat(jp, nextToken(START_OBJECT));
            assertThat(jp, context(entryCount(0)));
            assertThat(jp, nextToken(END_OBJECT));
            assertThat(jp, context(entryCount(0)));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(entryCount(1)));

        assertThat(jp, nextToken(nullValue()));
        assertThat(jp, context(entryCount(1)));
    }

    @Test
    public void shouldCountChildObjectEntries() {
        jp = createParser(object()
                .put("ChildObject", object()
                        .put("key1", 3)
                        .put("key2", 6)
                        .put("key3", 9)));

        assertThat(jp, context(entryCount(0)));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(entryCount(0)));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(1)));
            assertThat(jp, nextToken(START_OBJECT));
            assertThat(jp, context(entryCount(0)));
            {
                assertThat(jp, nextToken(FIELD_NAME));
                assertThat(jp, context(entryCount(1)));
                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(entryCount(1)));

                assertThat(jp, nextToken(FIELD_NAME));
                assertThat(jp, context(entryCount(2)));
                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(entryCount(2)));

                assertThat(jp, nextToken(FIELD_NAME));
                assertThat(jp, context(entryCount(3)));
                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(entryCount(3)));
                
            }
            assertThat(jp, nextToken(END_OBJECT));
            assertThat(jp, context(entryCount(3)));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(entryCount(1)));

        assertThat(jp, nextToken(nullValue()));
        assertThat(jp, context(entryCount(1)));
    }

    @Test
    public void shouldCountEmptyChildArrayEntries() {
        jp = createParser(object()
                .put("ChildArray", array()));

        assertThat(jp, context(entryCount(0)));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(entryCount(0)));
        {
            assertThat(jp, context(entryCount(0)));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(1)));
            assertThat(jp, nextToken(START_ARRAY));
            assertThat(jp, context(entryCount(0)));
            assertThat(jp, nextToken(END_ARRAY));
            assertThat(jp, context(entryCount(0)));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(entryCount(1)));

        assertThat(jp, nextToken(nullValue()));
        assertThat(jp, context(entryCount(1)));
    }

    @Test
    public void shouldCountChildArrayEntries() {
        jp = createParser(object()
                .put("ChildArray", array()
                        .add(3)
                        .add(6)
                        .add(9)));

        assertThat(jp, context(entryCount(0)));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, context(entryCount(0)));
        {
            assertThat(jp, context(entryCount(0)));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, context(entryCount(1)));
            assertThat(jp, nextToken(START_ARRAY));
            assertThat(jp, context(entryCount(0)));
            {
                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(entryCount(1)));

                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(entryCount(2)));

                assertThat(jp, nextToken(valueToken()));
                assertThat(jp, context(entryCount(3)));
            }
            assertThat(jp, nextToken(END_ARRAY));
            assertThat(jp, context(entryCount(3)));
        }
        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, context(entryCount(1)));

        assertThat(jp, nextToken(nullValue()));
        assertThat(jp, context(entryCount(1)));
    }
}
