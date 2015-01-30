package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import java.io.IOException;

import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for {@link JsonObjectDeserializer}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonObjectDeserializerTest {

    private ObjectMapper om;

    @Before
    public void setUp() {
        om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());
    }

    @Test
    public void testDeserializeEmptyObject() throws IOException {
        JsonObject expected = object().build();

        String json = expected.encode();

        JsonObject object = om.readValue(json, JsonObject.class);

        assertThat(object, equalTo(expected));
    }

    @Test
    public void testDeserializeObject() throws IOException {
        JsonObject expected = object()
                .put("anObject", object()
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

        String json = expected.encode();

        JsonObject object = om.readValue(json, JsonObject.class);

        assertThat(object, equalTo(expected));
        assertThat(object.size(), equalTo(7));
    }
}