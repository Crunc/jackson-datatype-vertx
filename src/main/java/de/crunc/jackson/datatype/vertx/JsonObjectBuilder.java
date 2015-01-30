package de.crunc.jackson.datatype.vertx;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Fluent builder for {@link JsonObject}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.2.2
 */
public class JsonObjectBuilder {

    private final Map<String, Object> values;

    private JsonObjectBuilder() {
        values = new HashMap<>();
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable JsonObject object) {
        values.put(field, object);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable JsonObjectBuilder objectBuilder) {
        if (objectBuilder != null) {
            return put(field, objectBuilder.build());
        }
        return put(field, (JsonObject)null);
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable JsonArray array) {
        values.put(field, array);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable JsonArrayBuilder arrayBuilder) {
        if (arrayBuilder != null) {
            return put(field, arrayBuilder.build());
        }
        return put(field, (JsonArray)null);
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable String string) {
        values.put(field, string);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable Number number) {
        values.put(field, number);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable Boolean bool) {
        values.put(field, bool);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder put(String field, @Nullable byte[] bytes) {
        values.put(field, bytes);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObjectBuilder putNull(String field) {
        values.put(field, null);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonObject build() {
        JsonObject object = new JsonObject();

        for (Map.Entry<String, Object> entry: values.entrySet()) {
            object.putValue(entry.getKey(), entry.getValue());
        }

        return object;
    }

    /**
     * @since 2.2.2
     */
    public String encode() {
        return build().encode();
    }

    /**
     * @since 2.2.2
     */
    public static JsonObjectBuilder object() {
        return new JsonObjectBuilder();
    }
}
