package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

/**
 * Serializes values of type {@link JsonObject}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.2.2
 */
class JsonObjectSerializer extends JsonBaseSerializer<JsonObject> {
    public final static JsonObjectSerializer instance = new JsonObjectSerializer();

    JsonObjectSerializer() {
        super(JsonObject.class);
    }

    @Override
    public void serialize(JsonObject value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        serializeContents(value, jgen, provider);
        jgen.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonObject value, JsonGenerator jgen, SerializerProvider provider,
                                  TypeSerializer typeSer)
            throws IOException {
        typeSer.writeTypePrefixForObject(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForObject(value, jgen);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
            throws JsonMappingException {
        return createSchemaNode("object", true);
    }

    protected void serializeContents(JsonObject value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Iterator<?> it = value.getFieldNames().iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            Object ob = value.getField(key);
            if (ob == null) {
                if (provider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES)) {
                    jgen.writeNullField(key);
                }
                continue;
            }
            jgen.writeFieldName(key);
            Class<?> cls = ob.getClass();
            if (cls == JsonObject.class) {
                serialize((JsonObject) ob, jgen, provider);
            } else if (cls == JsonArray.class) {
                JsonArraySerializer.instance.serialize((JsonArray) ob, jgen, provider);
            } else if (cls == String.class) {
                jgen.writeString((String) ob);
            } else if (cls == Integer.class) {
                jgen.writeNumber(((Integer) ob).intValue());
            } else if (cls == Long.class) {
                jgen.writeNumber(((Long) ob).longValue());
            } else if (cls == Boolean.class) {
                jgen.writeBoolean((Boolean) ob);
            } else if (cls == Double.class) {
                jgen.writeNumber((Double) ob);
            } else if (cls == JsonArray.class) {
                JsonArraySerializer.instance.serialize((JsonArray) ob, jgen, provider);
            } else if (JsonObject.class.isAssignableFrom(cls)) { // sub-class
                serialize((JsonObject) ob, jgen, provider);
            } else if (JsonArray.class.isAssignableFrom(cls)) { // sub-class
                JsonArraySerializer.instance.serialize((JsonArray) ob, jgen, provider);
            } else {
                provider.defaultSerializeValue(ob, jgen);
            }
        }
    }
}
