package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import java.io.IOException;

import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for {@link JsonObjectSerializer}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonObjectSerializerTest {

    private ObjectMapper om;

    @Before
    public void setUp() {
        om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());
    }

    @Test
    public void testSerializeEmptyObject() throws JsonProcessingException {
        JsonObject object = JsonObjectBuilder.object().build();

        String json = om.writeValueAsString(object);

        assertThat(json, equalTo("{}"));
    }

    @Test
    public void testSerializeObject() throws IOException {
        JsonObject object = JsonObjectBuilder.object()
                .put("anObject", JsonObjectBuilder.object()
                        .put("foo", "bar")
                        .put("anInt", 7)
                        .put("aFloat", 4.669))
                .putNull("nullValue")
                .put("anArray", JsonArrayBuilder.array()
                        .add("Hello")
                        .add("World :)")
                        .add(19)
                        .add(false)
                        .addNull())
                .put("aBool", true)
                .put("anotherBool", false)
                .put("anInt", 42)
                .put("aFloat", 3.141)
                .build();

        String json = om.writeValueAsString(object);

        assertThat(json, isJsonObject()
                .prop("anObject", isJsonObject()
                        .prop("foo", "bar")
                        .prop("anInt", 7)
                        .prop("aFloat", 4.669))
                .prop("nullValue", null)
                .prop("anArray", isJsonArray()
                        .item("Hello")
                        .item("World :)")
                        .item(19)
                        .item(false)
                        .item(null))
                .prop("aBool", true)
                .prop("anotherBool", false)
                .prop("anInt", 42)
                .prop("aFloat", 3.141));
    }
}