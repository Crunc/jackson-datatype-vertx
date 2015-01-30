package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.vertx.java.core.json.JsonObject;

import java.io.IOException;

/**
 * Deserializer which produces {@link JsonObject}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.2.2
 */
class JsonObjectDeserializer extends StdDeserializer<JsonObject> {
    private static final long serialVersionUID = 1L;

    public final static JsonObjectDeserializer instance = new JsonObjectDeserializer();

    JsonObjectDeserializer() {
        super(JsonObject.class);
    }

    @Override
    public JsonObject deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonObjectBuilder builder = JsonObjectBuilder.object();

        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        for (; t == JsonToken.FIELD_NAME; t = jp.nextToken()) {
            String fieldName = jp.getCurrentName();
            t = jp.nextToken();

            switch (t) {
                case START_ARRAY:
                    builder.put(fieldName, JsonArrayDeserializer.instance.deserialize(jp, ctxt));
                    continue;
                case START_OBJECT:
                    builder.put(fieldName, deserialize(jp, ctxt));
                    continue;
                case VALUE_STRING:
                    builder.put(fieldName, jp.getText());
                    continue;
                case VALUE_NULL:
                    builder.putNull(fieldName);
                    continue;
                case VALUE_TRUE:
                    builder.put(fieldName, Boolean.TRUE);
                    continue;
                case VALUE_FALSE:
                    builder.put(fieldName, Boolean.FALSE);
                    continue;
                case VALUE_NUMBER_INT:
                    builder.put(fieldName, jp.getNumberValue());
                    continue;
                case VALUE_NUMBER_FLOAT:
                    builder.put(fieldName, jp.getNumberValue());
                    continue;
                default:
                    throw ctxt.mappingException("Unrecognized or unsupported JsonToken type: " + t);
            }
        }

        return builder.build();
    }
}
