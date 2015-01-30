package de.crunc.jackson.datatype.vertx;


import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Fluent builder for {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.2.2
 */
public class JsonArrayBuilder {

    private final List<Object> values;

    private JsonArrayBuilder() {
        values = new ArrayList<>();
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable JsonObject jsonObject) {
        values.add(jsonObject);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable JsonObjectBuilder builder) {
        if (builder != null) {
            return add(builder.build());
        }
        return add((JsonObject) null);
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable JsonArray jsonArray) {
        values.add(jsonArray);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable JsonArrayBuilder builder) {
        if (builder != null) {
            return add(builder.build());
        }
        return add((JsonArray)null);
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable String string) {
        values.add(string);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable Number number) {
        values.add(number);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable Boolean bool) {
        values.add(bool);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder add(@Nullable byte[] bytes) {
        values.add(bytes);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArrayBuilder addNull() {
        values.add(null);
        return this;
    }

    /**
     * @since 2.2.2
     */
    public JsonArray build() {
        JsonArray array = new JsonArray();

        for (Object value: values) {
            array.add(value);
        }

        return array;
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
    public static JsonArrayBuilder array() {
        return new JsonArrayBuilder();
    }
}
