package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.IOException;

import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link VertxJsonModule}
 * 
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com  
 */
public class VertxJsonModuleTest {

    @Test
    public void shouldAddJsonObjectSerializer() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());

        String json = om.writeValueAsString(JsonObjectBuilder.object()
                .build());

        assertThat(json, isJsonObject());
    }

    @Test
    public void shouldAddJsonObjectDeserializer() throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());

        JsonObject object = om.readValue("{}", JsonObject.class);

        assertThat(object, isJsonObject());
    }

    @Test
    public void shouldAddJsonArraySerializer() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());

        String json = om.writeValueAsString(JsonArrayBuilder.array()
                .build());

        assertThat(json, isJsonArray());
    }

    @Test
    public void shouldAddJsonArrayDeserializer() throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new VertxJsonModule());

        JsonArray object = om.readValue("[]", JsonArray.class);

        assertThat(object, isJsonArray());
    }
}
