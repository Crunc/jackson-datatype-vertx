package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.crunc.jackson.datatype.vertx.parser.JsonElementParser;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonElement;
import org.vertx.java.core.json.JsonObject;

import java.io.IOException;

/**
 * Provides operations that help using {@link ObjectMapper} to convert between POJOs and {@link JsonObject}
 * / {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.1
 */
public class ObjectMapping {

    private final ObjectMapper om;
    
    public ObjectMapping(ObjectMapper objectMapper) {
        om = objectMapper;
    }

    public <T> T unmarshall(JsonElement jsonElement, Class<T> type) throws IOException {
        return om.readValue(new JsonElementParser(jsonElement), type);
    }

    public <T> T unmarshall(JsonObjectBuilder builder, Class<T> type) throws IOException {
        return unmarshall(builder.build(), type);
    }

    public <T> T unmarshall(JsonArrayBuilder builder, Class<T> type) throws IOException {
        return unmarshall(builder.build(), type);
    }

    public static <T> T unmarshall(JsonElement jsonElement, Class<T> type, ObjectMapper objectMapper) throws IOException {
        return new ObjectMapping(objectMapper).unmarshall(jsonElement, type);
    }

    public static <T> T unmarshall(JsonObjectBuilder builder, Class<T> type, ObjectMapper objectMapper) throws IOException {
        return unmarshall(builder.build(), type, objectMapper);
    }

    public static <T> T unmarshall(JsonArrayBuilder builder, Class<T> type, ObjectMapper objectMapper) throws IOException {
        return unmarshall(builder.build(), type, objectMapper);
    }
}
