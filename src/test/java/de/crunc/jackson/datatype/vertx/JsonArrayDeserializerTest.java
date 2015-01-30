package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.json.JsonArray;

import java.io.IOException;

import static de.crunc.jackson.datatype.vertx.JsonArrayBuilder.array;
import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for {@link JsonArrayDeserializer}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonArrayDeserializerTest {

    private ObjectMapper om;

    @Before
    public void setUp() {
        om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());
    }

    @Test
    public void testDeserializeEmptyArray() throws IOException {
        String json = array().encode();

        JsonArray array = om.readValue(json, JsonArray.class);

        assertThat(array, is(not(nullValue())));
        assertThat(array.size(), is(0));
    }

    @Test
    public void testDeserializeArray() throws IOException {
        JsonArray expected = array()
                .add(true)
                .add(21)
                .add(false)
                .add(object()
                        .put("foo", 42)
                        .put("bar", "Hello world!"))
                .add(array()
                        .add("Halli")
                        .add("Galli")
                        .add(1)
                        .add(2)
                        .add(3))
                .addNull()
                .add(3.141)
                .build();

        String json = expected.encode();

        JsonArray array = om.readValue(json, JsonArray.class);

        assertThat(array, is(not(nullValue())));
        assertThat(array.size(), is(7));
        assertThat(array, equalTo(expected));
    }
}