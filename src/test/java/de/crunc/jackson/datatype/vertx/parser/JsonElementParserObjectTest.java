package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.fasterxml.jackson.core.JsonToken.*;
import static de.crunc.jackson.datatype.vertx.JsonArrayBuilder.array;
import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static de.crunc.jackson.datatype.vertx.matcher.JsonParserMatchers.*;
import static de.crunc.jackson.datatype.vertx.matcher.MoreMatchers.closeTo;
import static de.crunc.jackson.datatype.vertx.matcher.MoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class JsonElementParserObjectTest extends JsonElementParserBaseTest {

    private JsonParser jp;

    @Test
    public void shouldParseObjectWithBooleanTrue() {
        jp = createParser(object()
                .put("BooleanTrue", true));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_TRUE));
        assertThat(jp, hasBoolValue(true));
        assertThat(jp, hasTextValue("true"));
    }

    @Test
    public void shouldParseObjectWithBooleanFalse() {
        jp = createParser(object()
                .put("BooleanFalse", false));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_FALSE));
        assertThat(jp, hasBoolValue(false));
        assertThat(jp, hasTextValue("false"));
    }

    @Test
    public void shouldParseObjectWithIntegerValue() {
        jp = createParser(object()
                .put("Integer", 42));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_NUMBER_INT));
        assertThat(jp, hasIntValue(42));
        assertThat(jp, hasLongValue(42L));
        assertThat(jp, hasBigIntegerValue(new BigInteger("42")));
        assertThat(jp, hasFloatValue(closeTo(42.0f, 0.001f)));
        assertThat(jp, hasDoubleValue(closeTo(42.0, 0.001)));
        assertThat(jp, hasBigDecimalValue(closeTo(new BigDecimal("42.0"), new BigDecimal("0.001"))));
        assertThat(jp, hasTextValue("42"));
    }

    @Test
    public void shouldParseObjectWithLongValue() {
        jp = createParser(object()
                .put("Long", 42L));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_NUMBER_INT));
        assertThat(jp, hasIntValue(42));
        assertThat(jp, hasLongValue(42L));
        assertThat(jp, hasBigIntegerValue(new BigInteger("42")));
        assertThat(jp, hasFloatValue(closeTo(42.0f, 0.001f)));
        assertThat(jp, hasDoubleValue(closeTo(42.0, 0.001)));
        assertThat(jp, hasBigDecimalValue(closeTo(new BigDecimal("42.0"), new BigDecimal("0.001"))));
        assertThat(jp, hasTextValue("42"));
    }

    @Test
    public void shouldParseObjectWithFloatValue() {
        jp = createParser(object()
                .put("Float", 17.82f));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_NUMBER_FLOAT));
        assertThat(jp, hasIntValue(17));
        assertThat(jp, hasLongValue(17L));
        assertThat(jp, hasBigIntegerValue(new BigInteger("17")));
        assertThat(jp, hasFloatValue(closeTo(17.82f, 0.001f)));
        assertThat(jp, hasDoubleValue(closeTo(17.82, 0.001)));
        assertThat(jp, hasBigDecimalValue(closeTo(new BigDecimal("17.82"), new BigDecimal("0.001"))));
        assertThat(jp, hasTextValue("17.82"));
    }

    @Test
    public void shouldParseObjectWithDoubleValue() {
        jp = createParser(object()
                .put("Float", 17.82));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_NUMBER_FLOAT));
        assertThat(jp, hasIntValue(17));
        assertThat(jp, hasLongValue(17L));
        assertThat(jp, hasBigIntegerValue(new BigInteger("17")));
        assertThat(jp, hasFloatValue(closeTo(17.82f, 0.001f)));
        assertThat(jp, hasDoubleValue(closeTo(17.82, 0.001)));
        assertThat(jp, hasBigDecimalValue(closeTo(new BigDecimal("17.82"), new BigDecimal("0.001"))));
        assertThat(jp, hasTextValue("17.82"));
    }

    @Test
    public void shouldParseObjectWithNumericStringValue() {
        jp = createParser(object()
                .put("NumericString", "13.37"));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_STRING));
        assertThat(jp, hasIntValue(13));
        assertThat(jp, hasLongValue(13L));
        assertThat(jp, hasBigIntegerValue(new BigInteger("13")));
        assertThat(jp, hasFloatValue(closeTo(13.37f, 0.001f)));
        assertThat(jp, hasDoubleValue(closeTo(13.37, 0.001)));
        assertThat(jp, hasBigDecimalValue(closeTo(new BigDecimal("13.37"), new BigDecimal("0.001"))));
        assertThat(jp, hasTextValue("13.37"));
    }

    @Test
    public void shouldParseObjectWithNullValue() {
        jp = createParser(object()
                .putNull("NullValue"));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(VALUE_NULL));
        assertThat(jp, hasNullValue());
        assertThat(jp, hasTextValue("null"));
    }

    @Test
    public void shouldParseObjectWithEmptyChildObject() {
        jp = createParser(object()
                .put("EmptyObject", object()));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(START_OBJECT));
        assertThat(jp, hasCurrentName("EmptyObject"));

        assertThat(jp, nextToken(END_OBJECT));
        assertThat(jp, hasCurrentName("EmptyObject"));

        assertThat(jp, nextToken(END_OBJECT));
    }

    @Test
    public void shouldParseObjectWithEmptyChildArray() {
        jp = createParser(object()
                .put("EmptyArray", array()));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        assertThat(jp, nextToken(FIELD_NAME));

        assertThat(jp, nextToken(START_ARRAY));
        assertThat(jp, hasCurrentName("EmptyArray"));

        assertThat(jp, nextToken(END_ARRAY));
        assertThat(jp, hasCurrentName("EmptyArray"));

        assertThat(jp, nextToken(END_OBJECT));
    }

    @Test
    public void shouldParseObjectWithChildObjects() {
        jp = createParser(object()
                .put("Object1", object()
                        .put("Value", 1))
                .put("Object2", object()
                        .put("Value", 2))
                .put("Object3", object()
                        .put("Value", 3)));

        // careful: traversing order can not be predicted

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_OBJECT));
            {
                assertThat(jp, nextToken(FIELD_NAME));
                assertThat(jp, nextToken(VALUE_NUMBER_INT));
            }
            assertThat(jp, nextToken(END_OBJECT));
            
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_OBJECT));
            {
                assertThat(jp, nextToken(FIELD_NAME));
                assertThat(jp, nextToken(VALUE_NUMBER_INT));
            }
            assertThat(jp, nextToken(END_OBJECT));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_OBJECT));
            {
                assertThat(jp, nextToken(FIELD_NAME));
                assertThat(jp, nextToken(VALUE_NUMBER_INT));
            }
            assertThat(jp, nextToken(END_OBJECT));
        }
        assertThat(jp, nextToken(END_OBJECT));
        
        assertThat(jp, nextToken(nullValue()));
    }

    @Test
    public void shouldParseObjectWithChildArrays() {
        jp = createParser(object()
                .put("Array1", array()
                        .add(1))
                .put("Array2", array()
                        .add(2))
                .put("Array3", array()
                        .add(3)));

        // careful: traversing order can not be predicted
        
        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_ARRAY));
            {
                assertThat(jp, nextToken(VALUE_NUMBER_INT));
            }
            assertThat(jp, nextToken(END_ARRAY));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_ARRAY));
            {
                assertThat(jp, nextToken(VALUE_NUMBER_INT));
            }
            assertThat(jp, nextToken(END_ARRAY));

            assertThat(jp, nextToken(FIELD_NAME));
            assertThat(jp, nextToken(START_ARRAY));
            {
                assertThat(jp, nextToken(VALUE_NUMBER_INT));
            }
            assertThat(jp, nextToken(END_ARRAY));
        }
        assertThat(jp, nextToken(END_OBJECT));

        assertThat(jp, nextToken(nullValue()));
    }

//    /**
//     * Verifies that 3 basic keywords (null, true, false) are properly parsed in various contexts.
//     */
//    @Test
//    public void shouldParseKeywordsCorrectly() throws Exception {
//
//        jp = createParser(object()
//                .putNull("key1")
//                .put("key2", true)
//                .put("key3", false)
//                .put("key4", array()
//                        .add(false)
//                        .addNull()
//                        .add(true)));
//
//        JsonStreamContext ctx = jp.getParsingContext();
//
//        assertThat(ctx.inRoot(), is(true));
//        assertThat(ctx.inArray(), is(false));
//        assertThat(ctx.inObject(), is(false));
//        assertThat(ctx.getEntryCount(), is(0));
//        assertThat(ctx.getCurrentIndex(), is(0));
//
//        // Before advancing to content, we should have following default state...
//
//        assertThat(jp.hasCurrentToken(), is(false));
//        assertThat(jp.getText(), is(nullValue()));
//        assertThat(jp.getTextCharacters(), is(nullValue()));
//        assertThat(jp.getTextLength(), is(0));
//        assertThat(jp.getTextOffset(), is(0));
//
//        assertThat(jp, nextToken(START_OBJECT));
//
//        assertThat(jp.hasCurrentToken(), is(true));
//
//        JsonLocation loc = jp.getTokenLocation();
//        assertThat(loc, is(not(nullValue())));
//        assertThat(loc.getLineNr(), is(1));
//        assertThat(loc.getColumnNr(), is(1));
//
//        ctx = jp.getParsingContext();
//
//        assertThat(ctx.inRoot(), is(false));
//        assertThat(ctx.inArray(), is(false));
//        assertThat(ctx.inObject(), is(true));
//        assertThat(ctx.getEntryCount(), is(0));
//        assertThat(ctx.getCurrentIndex(), is(0));
//
//        assertThat(jp, nextToken(FIELD_NAME));
//        assertThat(jp, hasFieldName("key1"));
//        assertThat(jp.getTokenLocation().getLineNr(), is(2));
//        ctx = jp.getParsingContext();
//        assertThat(ctx.inRoot(), is(false));
//        assertThat(ctx.inArray(), is(false));
//        assertThat(ctx.inObject(), is(true));
//        assertThat(ctx.getEntryCount(), is(1));
//        assertThat(ctx.getCurrentIndex(), is(0));
//        assertThat(ctx.getCurrentName(), is("key1"));
//
//        assertThat(jp, nextToken(VALUE_NULL));
//        assertThat(ctx.getCurrentName(), is("key1"));
//        ctx = jp.getParsingContext();
//        assertThat(ctx.getEntryCount(), is(1));
//        assertThat(ctx.getCurrentIndex(), is(0));
//
//        assertThat(jp, nextToken(FIELD_NAME));
//        assertThat(jp, hasFieldName("key2"));
//        ctx = jp.getParsingContext();
//        assertThat(ctx.getEntryCount(), is(2));
//        assertThat(ctx.getCurrentIndex(), is(1));
//        assertThat(ctx.getCurrentName(), is("key2"));
//
//        assertThat(jp, nextToken(VALUE_TRUE));
//        assertThat(ctx.getCurrentName(), is("key2"));
//
//        assertThat(jp, nextToken(FIELD_NAME));
//        assertThat(jp, hasFieldName("key3"));
//
//        assertThat(jp, nextToken(VALUE_FALSE));
//
//        assertThat(jp, nextToken(FIELD_NAME));
//        assertThat(jp, hasFieldName("key4"));
//
//        assertThat(jp, nextToken(START_ARRAY));
//        ctx = jp.getParsingContext();
//        assertThat(ctx.inArray(), is(true));
//        assertThat(ctx.getCurrentName(), is(nullValue()));
//        assertThat(ctx.getParent().getCurrentName(), is("key4"));
//
//        assertThat(jp, nextToken(VALUE_FALSE));
//        assertThat(jp, nextToken(VALUE_NULL));
//        assertThat(jp, nextToken(VALUE_TRUE));
//        assertThat(jp, nextToken(END_ARRAY));
//        ctx = jp.getParsingContext();
//        assertThat(ctx.inObject(), is(true));
//
//        assertThat(jp, nextToken(END_OBJECT));
//        ctx = jp.getParsingContext();
//        assertThat(ctx.inRoot(), is(true));
//        assertThat(ctx.getCurrentName(), is(nullValue()));
//    }

    @Test
    public void shouldSkipChildrenOfWholeObject() throws IOException {
        jp = createParser(object()
                .put("key1", 1)
                .put("key2", 3)
                .put("key3", array()
                        .add(true)
                        .addNull())
                .put("key4", 3)
                .put("key5", object()
                        .put("a", "b"))
                .put("key6", array()
                        .add(array()))
                .put("key7", object()));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));

        jp.skipChildren();

        assertThat(jp, hasCurrentToken(END_OBJECT));

        assertThat(jp, nextToken(nullValue()));
    }

    @Test
    public void shouldSkipChildrenOfChildObject() throws IOException {
        jp = createParser(object()
                .put("ChildObject", object()
                        .put("key1", 1)
                        .putNull("key2")
                        .put("key3", true)
                        .put("key4", array())));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));

            assertThat(jp, nextToken(START_OBJECT));
            {
                jp.skipChildren();
            }
            assertThat(jp, hasCurrentToken(END_OBJECT));
        }
        assertThat(jp, nextToken(END_OBJECT));

        assertThat(jp, nextToken(nullValue()));
    }

    @Test
    public void shouldSkipChildrenOfEmptyChildObject() throws IOException {
        jp = createParser(object()
                .put("EmptyObject", object()));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));

            assertThat(jp, nextToken(START_OBJECT));
            {
                jp.skipChildren();
            }
            assertThat(jp, hasCurrentToken(END_OBJECT));
        }
        assertThat(jp, nextToken(END_OBJECT));

        assertThat(jp, nextToken(nullValue()));
    }

    @Test
    public void shouldNotSkipIntValue() throws IOException {
        jp = createParser(object()
                .put("Integer", 21));

        assertThat(jp, hasCurrentToken(nullValue()));

        assertThat(jp, nextToken(START_OBJECT));
        {
            assertThat(jp, nextToken(FIELD_NAME));

            assertThat(jp, nextToken(VALUE_NUMBER_INT));
            {
                jp.skipChildren();
            }
            assertThat(jp, hasCurrentToken(VALUE_NUMBER_INT));
        }
        assertThat(jp, nextToken(END_OBJECT));

        assertThat(jp, nextToken(nullValue()));
    }


//    private void doTestSpecIndividual(String enc, boolean verify) throws IOException {
//        jp = createParser(object()
//                .put("Image", object()
//                        .put("Width", SAMPLE_SPEC_VALUE_WIDTH)
//                        .put("Height", SAMPLE_SPEC_VALUE_HEIGHT)
//                        .put("Title", SAMPLE_SPEC_VALUE_TITLE)
//                        .put("Thumbnail", object()
//                                .put("Url", SAMPLE_SPEC_VALUE_TN_URL)
//                                .put("Height", SAMPLE_SPEC_VALUE_TN_HEIGHT)
//                                .put("Width", SAMPLE_SPEC_VALUE_TN_WIDTH))
//                        .put("IDs", array()
//                                .add(SAMPLE_SPEC_VALUE_TN_ID1)
//                                .add(SAMPLE_SPEC_VALUE_TN_ID2)
//                                .add(SAMPLE_SPEC_VALUE_TN_ID3)
//                                .add(SAMPLE_SPEC_VALUE_TN_ID4))));
//
//        if (!jp.hasCurrentToken()) {
//            jp.nextToken();
//        }
//
//        assertThat(jp, hasCurrentToken(START_OBJECT)); // main object
//        {
//            assertThat(jp, nextToken(FIELD_NAME)); // 'Image'
//            assertThat(jp, hasFieldName("Image"));
//
//            assertThat(jp, nextToken(START_OBJECT)); // 'image' object
//            {
//                assertThat(jp, nextToken(FIELD_NAME)); // 'Width'
//                assertThat(jp, hasFieldName("Width"));
//
//                assertThat(jp, nextToken(VALUE_NUMBER_INT));
//                assertThat(jp, hasIntValue(SAMPLE_SPEC_VALUE_WIDTH));
//
//                assertThat(jp, nextToken(FIELD_NAME)); // 'Height'
//                assertThat(jp, hasFieldName("Height"));
//
//                assertThat(jp, nextToken(VALUE_NUMBER_INT));
//                assertThat(jp, hasIntValue(SAMPLE_SPEC_VALUE_HEIGHT));
//
//                assertThat(jp, nextToken(FIELD_NAME)); // 'Title'
//                assertThat(jp, hasFieldName("Title"));
//
//                assertThat(jp, nextToken(VALUE_STRING));
//                assertThat(jp, hasTextValue(SAMPLE_SPEC_VALUE_TITLE));
//
//                assertThat(jp, nextToken(FIELD_NAME)); // 'Thumbnail'
//                assertThat(jp, hasFieldName("Thumbnail"));
//
//                assertThat(jp, nextToken(START_OBJECT)); // 'thumbnail' object
//                {
//                    assertThat(jp, nextToken(FIELD_NAME)); // 'Url'
//                    assertThat(jp, hasFieldName("Url"));
//
//                    assertThat(jp, nextToken(VALUE_STRING));
//                    assertThat(jp, hasTextValue(SAMPLE_SPEC_VALUE_TN_URL));
//
//                    assertThat(jp, nextToken(FIELD_NAME)); // 'Height'
//                    assertThat(jp, hasFieldName("Height"));
//
//                    assertThat(jp, nextToken(VALUE_NUMBER_INT));
//                    assertThat(jp, hasIntValue(SAMPLE_SPEC_VALUE_TN_HEIGHT));
//
//                    assertThat(jp, nextToken(FIELD_NAME)); // 'Width'
//                    assertThat(jp, hasFieldName("Width"));
//
//                    assertThat(jp, nextToken(VALUE_STRING));
//                    assertThat(jp, hasTextValue(SAMPLE_SPEC_VALUE_TN_WIDTH));
//                }
//                assertThat(jp, nextToken(END_OBJECT));
//
//                assertThat(jp, nextToken(FIELD_NAME));
//
//                assertThat(jp, nextToken(START_ARRAY));
//                {
//                    assertThat(jp, nextToken(VALUE_NUMBER_INT));
//                    assertThat(jp, hasIntValue(SAMPLE_SPEC_VALUE_TN_ID1));
//
//                    assertThat(jp, nextToken(VALUE_NUMBER_INT));
//                    assertThat(jp, hasIntValue(SAMPLE_SPEC_VALUE_TN_ID2));
//
//                    assertThat(jp, nextToken(VALUE_NUMBER_INT));
//                    assertThat(jp, hasIntValue(SAMPLE_SPEC_VALUE_TN_ID3));
//
//                    assertThat(jp, nextToken(VALUE_NUMBER_INT));
//                    assertThat(jp, hasIntValue(SAMPLE_SPEC_VALUE_TN_ID4));
//                }
//                assertThat(jp, nextToken(END_ARRAY));
//            }
//            assertThat(jp, nextToken(END_OBJECT));
//        }
//        assertThat(jp, nextToken(END_OBJECT));
//    }
}