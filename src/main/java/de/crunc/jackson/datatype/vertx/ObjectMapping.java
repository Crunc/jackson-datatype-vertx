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
public final class ObjectMapping {

    public static <T> T unmarshall(JsonElement jsonElement, Class<T> type, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(new JsonElementParser(jsonElement), type);
    }

    private ObjectMapping() {
        throw new UnsupportedOperationException(ObjectMapping.class.getName() + " may not be instantiated");
    }
}
