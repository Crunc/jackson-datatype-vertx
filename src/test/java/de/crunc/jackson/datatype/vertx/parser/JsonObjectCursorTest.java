package de.crunc.jackson.datatype.vertx.parser;

import org.junit.Test;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import static com.fasterxml.jackson.core.JsonToken.*;
import static de.crunc.jackson.datatype.vertx.JsonArrayBuilder.array;
import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for {@link JsonObjectCursor}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonObjectCursorTest {

    private JsonObjectCursor cursor;

    @Test
    public void getParentShouldReturnNullWithoutParent() {
        cursor = new JsonObjectCursor(object().build(), null);

        assertThat(cursor.getParent(), is(nullValue()));
    }

    @Test
    public void getParentShouldReturnParent() {
        AbstractTreeCursor<Object> parent = new JsonObjectCursor(object().build(), null);
        cursor = new JsonObjectCursor(object().build(), parent);

        assertThat(cursor.getParent(), is(sameInstance(parent)));
    }

    @Test
    public void shouldTraverseBooleanTrue() {
        cursor = new JsonObjectCursor(object()
                .put("BooleanTrue", true)
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("BooleanTrue"));
        assertThat((Boolean) cursor.currentElement(), is(true));

        assertThat(cursor.nextToken(), is(VALUE_TRUE));
        assertThat(cursor.getCurrentName(), is("BooleanTrue"));
        assertThat((Boolean) cursor.currentElement(), is(true));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseBooleanFalse() {
        cursor = new JsonObjectCursor(object()
                .put("BooleanFalse", false)
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("BooleanFalse"));
        assertThat((Boolean) cursor.currentElement(), is(false));

        assertThat(cursor.nextToken(), is(VALUE_FALSE));
        assertThat(cursor.getCurrentName(), is("BooleanFalse"));
        assertThat((Boolean) cursor.currentElement(), is(false));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseInteger() {
        cursor = new JsonObjectCursor(object()
                .put("Integer", 42)
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("Integer"));
        assertThat((Integer) cursor.currentElement(), is(42));

        assertThat(cursor.nextToken(), is(VALUE_NUMBER_INT));
        assertThat(cursor.getCurrentName(), is("Integer"));
        assertThat((Integer) cursor.currentElement(), is(42));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseLong() {
        cursor = new JsonObjectCursor(object()
                .put("Long", 42L)
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("Long"));
        assertThat((Long) cursor.currentElement(), is(42L));

        assertThat(cursor.nextToken(), is(VALUE_NUMBER_INT));
        assertThat(cursor.getCurrentName(), is("Long"));
        assertThat((Long) cursor.currentElement(), is(42L));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseFloat() {
        cursor = new JsonObjectCursor(object()
                .put("Float", 13.37f)
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("Float"));
        assertThat((Float) cursor.currentElement(), is(13.37f));

        assertThat(cursor.nextToken(), is(VALUE_NUMBER_FLOAT));
        assertThat(cursor.getCurrentName(), is("Float"));
        assertThat((Float) cursor.currentElement(), is(13.37f));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseDouble() {
        cursor = new JsonObjectCursor(object()
                .put("Double", 13.37)
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("Double"));
        assertThat((Double) cursor.currentElement(), is(13.37));

        assertThat(cursor.nextToken(), is(VALUE_NUMBER_FLOAT));
        assertThat(cursor.getCurrentName(), is("Double"));
        assertThat((Double) cursor.currentElement(), is(13.37));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseNull() {
        cursor = new JsonObjectCursor(object()
                .putNull("null")
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("null"));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(VALUE_NULL));
        assertThat(cursor.getCurrentName(), is("null"));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseObject() {
        cursor = new JsonObjectCursor(object()
                .put("object", object())
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(START_OBJECT));
        assertThat(cursor.getCurrentName(), is("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseArray() {
        cursor = new JsonObjectCursor(object()
                .put("array", array())
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("array"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonArray.class)));

        assertThat(cursor.nextToken(), is(START_ARRAY));
        assertThat(cursor.getCurrentName(), is("array"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonArray.class)));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void shouldTraverseMultipleObjects() {
        cursor = new JsonObjectCursor(object()
                .put("object1", object())
                .put("object2", object())
                .put("object3", object())
                .build(), null);

        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), startsWith("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(START_OBJECT));
        assertThat(cursor.getCurrentName(), startsWith("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), startsWith("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(START_OBJECT));
        assertThat(cursor.getCurrentName(), startsWith("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), startsWith("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(START_OBJECT));
        assertThat(cursor.getCurrentName(), startsWith("object"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
    }

    @Test
    public void currentHasChildrenShouldSucceedForNonEmptyChildObject() {
        cursor = new JsonObjectCursor(object()
                .put("childObject", object()
                        .put("foo", "bar"))
                .build(), null);
        
        assertThat(cursor.currentHasChildren(), is(false));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("childObject"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));
        assertThat(cursor.currentHasChildren(), is(true));

        assertThat(cursor.nextToken(), is(START_OBJECT));
        assertThat(cursor.getCurrentName(), is("childObject"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));
        assertThat(cursor.currentHasChildren(), is(true));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
        assertThat(cursor.currentHasChildren(), is(false));
    }

    @Test
    public void currentHasChildrenShouldSucceedForEmptyChildObject() {
        cursor = new JsonObjectCursor(object()
                .put("childObject", object())
                .build(), null);

        assertThat(cursor.currentHasChildren(), is(false));

        assertThat(cursor.nextToken(), is(FIELD_NAME));
        assertThat(cursor.getCurrentName(), is("childObject"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));
        assertThat(cursor.currentHasChildren(), is(false));

        assertThat(cursor.nextToken(), is(START_OBJECT));
        assertThat(cursor.getCurrentName(), is("childObject"));
        assertThat(cursor.currentElement(), is(instanceOf(JsonObject.class)));
        assertThat(cursor.currentHasChildren(), is(false));

        assertThat(cursor.nextToken(), is(nullValue()));
        assertThat(cursor.getCurrentName(), is(nullValue()));
        assertThat(cursor.currentElement(), is(nullValue()));
        assertThat(cursor.currentHasChildren(), is(false));
    }
}