package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

/**
 * Provides Serializers and Deserializers for {@link JsonObject} and {@link JsonArray}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.2.2
 */
public class VertxJsonModule extends SimpleModule {
    
    private static final long serialVersionUID = 1;

    private final static String NAME = "VertxJsonModule";

    public VertxJsonModule() {
        super(NAME, VersionUtil.parseVersion("2.2.2-SNAPSHOT", "de.jegair", "jackson-datatype-vertx"));
        addDeserializer(JsonArray.class, JsonArrayDeserializer.instance);
        addDeserializer(JsonObject.class, JsonObjectDeserializer.instance);
        addSerializer(JsonArraySerializer.instance);
        addSerializer(JsonObjectSerializer.instance);
    }
}
