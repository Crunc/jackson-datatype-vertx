package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.crunc.hamcrest.json.JsonMatchers;
import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.json.JsonArray;

import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for {@link JsonArraySerializer}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonArraySerializerTest {

    private ObjectMapper om;

    @Before
    public void setUp() {
        om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());
    }

    @Test
    public void testSerializeEmptyArray() throws JsonProcessingException {
        JsonArray array = JsonArrayBuilder.array().build();

        String json = om.writeValueAsString(array);

        assertThat(json, equalTo("[]"));
    }

    @Test
    public void testSerializeArray() throws JsonProcessingException {
        JsonArray array = JsonArrayBuilder.array()
                .add(true)
                .add(21)
                .add(false)
                .add(JsonObjectBuilder.object()
                        .put("foo", 42)
                        .put("bar", "Hello world!"))
                .add(JsonArrayBuilder.array()
                        .add("Halli")
                        .add("Galli")
                        .add(1)
                        .add(2)
                        .add(3))
                .addNull()
                .add(3.141)
                .build();

        String json = om.writeValueAsString(array);

        assertThat(json, isJsonArray()
                .item(true)
                .item(21)
                .item(false)
                .item(isJsonObject()
                        .prop("foo", 42)
                        .prop("bar", "Hello world!"))
                .item(isJsonArray()
                        .item("Halli")
                        .item("Galli")
                        .item(1)
                        .item(2)
                        .item(3))
                .item(null)
                .item(3.141));
    }
}