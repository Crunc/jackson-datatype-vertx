package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonToken;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonElement;
import org.vertx.java.core.json.JsonObject;

/**
 * Cursor for traversing a {@link JsonElement} root.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.1
 */
public class JsonElementRootCursor extends AbstractRootCursor<JsonElement, Object> {

    /**
     * Creates a new root level cursor.
     *
     * @param rootElement  The root element of the JSON structure.
     * @since 2.1
     */
    JsonElementRootCursor(JsonElement rootElement) {
        super(rootElement);
    }

    @Override
    protected JsonToken getRootToken(JsonElement root) {
        if (root.isObject()) {
            return JsonToken.START_OBJECT;
        } else if (root.isArray()) {
            return JsonToken.START_ARRAY;
        } else {
            throw new IllegalArgumentException("unknown JSON element type <" + root + ">");
        }
    }

    @Override
    protected Object getRootValue(JsonElement root) {
        return root;
    }

    @Override
    protected int getNumberOfChildren(Object element) {
        if (element instanceof JsonObject) {
            return ((JsonObject) element).size();
        } else if (element instanceof JsonArray) {
            return ((JsonArray) element).size();
        } else {
            return -1;
        }
    }

    @Override
    protected AbstractTreeCursor<Object> newObjectCursor(Object object) {
        return new JsonObjectCursor((JsonObject)object, this);
    }

    @Override
    protected AbstractTreeCursor<Object> newArrayCursor(Object array) {
        return new JsonArrayCursor((JsonArray)array, this);
    }

    @Override
    protected JsonToken getToken(Object element) {
        return JsonElementTokens.getToken(element);
    }
}
