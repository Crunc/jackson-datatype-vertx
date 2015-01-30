package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Common base class for all serializers.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 1.0
 */
public abstract class JsonBaseSerializer<T> extends StdSerializer<T> {
    protected JsonBaseSerializer(Class<T> cls) {
        super(cls);
    }
}
