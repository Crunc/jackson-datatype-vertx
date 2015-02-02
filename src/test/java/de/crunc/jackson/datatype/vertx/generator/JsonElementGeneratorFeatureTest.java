package de.crunc.jackson.datatype.vertx.generator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link JsonElementGenerator}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementGeneratorFeatureTest {

    private JsonElementGenerator jgen;

    @Before
    public void setUp() {
        jgen = new JsonElementGenerator(0, new ObjectMapper());
    }

    @After
    public void tearDown() throws IOException {
        if (jgen != null) {
            jgen.close();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // WRITE_NUMBERS_AS_STRINGS
    // -----------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldWriteIntAsStringIf_WRITE_NUMBERS_AS_STRINGS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        jgen.writeStartArray();
        jgen.writeNumber(42);
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("42"));
    }

    @Test
    public void shouldWriteLongAsStringIf_WRITE_NUMBERS_AS_STRINGS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        jgen.writeStartArray();
        jgen.writeNumber(42L);
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("42"));
    }

    @Test
    public void shouldWriteFloatAsStringIf_WRITE_NUMBERS_AS_STRINGS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        jgen.writeStartArray();
        jgen.writeNumber(3.141f);
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("3.141"));
    }

    @Test
    public void shouldWriteDoubleAsStringIf_WRITE_NUMBERS_AS_STRINGS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        jgen.writeStartArray();
        jgen.writeNumber(3.141);
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("3.141"));
    }

    @Test
    public void shouldWriteBigIntegerAsStringIf_WRITE_NUMBERS_AS_STRINGS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        jgen.writeStartArray();
        jgen.writeNumber(new BigInteger("42"));
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("42"));
    }

    @Test
    public void shouldWriteBigDecimalAsStringIf_WRITE_NUMBERS_AS_STRINGS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        jgen.writeStartArray();
        jgen.writeNumber(new BigDecimal("3.141"));
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("3.141"));
    }

    @Test
    public void shouldWriteNumericStringAsStringIf_WRITE_NUMBERS_AS_STRINGS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        jgen.writeStartArray();
        jgen.writeNumber("3.141");
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("3.141"));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // AUTO_CLOSE_JSON_CONTENT
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    public void shouldFinishGenerationWhenWritingFieldIf_AUTO_CLOSE_JSON_CONTENT_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);

        jgen.writeStartObject();
        {
            jgen.writeFieldName("ChildArray");
            jgen.writeStartArray();
            {
                jgen.writeStartObject();
                {
                    jgen.writeNumberField("NumberValue", 42);
                    jgen.writeFieldName("OmittedDueToClose");

                    jgen.close();
                } // not closed
            } // not closed
        } // not closed

        assertThat(jgen.get(), isJsonObject()
                .prop("ChildArray", isJsonArray()
                        .item(isJsonObject()
                                .prop("NumberValue", 42))));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotFinishGenerationWhenWritingFieldIf_AUTO_CLOSE_JSON_CONTENT_Disabled() throws IOException {
        jgen.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);

        jgen.writeStartObject();
        {
            jgen.writeFieldName("ChildArray");
            jgen.writeStartArray();
            {
                jgen.writeStartObject();
                {
                    jgen.writeNumberField("NumberValue", 42);
                    jgen.writeFieldName("OmittedDueToClose");
                } // not closed
            } // not closed
        } // not closed

        jgen.get(); // should throw IllegalStateException
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ESCAPE_NON_ASCII
    // -----------------------------------------------------------------------------------------------------------------
    
    @Test
    public void shouldWriteStringAsEscapedStringIf_ESCAPE_NON_ASCII_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        jgen.writeStartObject();
        jgen.writeStringField("StringValue", "我能吞下玻璃而不伤身体。");
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("StringValue", "\\u6211\\u80FD\\u541E\\u4E0B\\u73BB\\u7483\\u800C\\u4E0D\\u4F24\\u8EAB\\u4F53\\u3002"));
    }

    @Test
    public void shouldNotWriteStringAsEscapedStringIf_ESCAPE_NON_ASCII_Disabled() throws IOException {
        jgen.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        jgen.writeStartObject();
        jgen.writeStringField("StringValue", "我能吞下玻璃而不伤身体。");
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("StringValue", "我能吞下玻璃而不伤身体。"));
    }

    @Test
    public void shouldWriteFieldNameAsEscapedStringIf_ESCAPE_NON_ASCII_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        jgen.writeStartObject();
        jgen.writeStringField("私はガラスを食べられます。それは私を傷つけません", "watashihagarasuotaberaremasu.sorehawatashiokizutsukemasen");
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("\\u79C1\\u306F\\u30AC\\u30E9\\u30B9\\u3092\\u98DF\\u3079\\u3089\\u308C\\u307E\\u3059\\u3002\\u305D\\u308C\\u306F\\u79C1\\u3092\\u50B7\\u3064\\u3051\\u307E\\u305B\\u3093", 
                        "watashihagarasuotaberaremasu.sorehawatashiokizutsukemasen"));
    }

    @Test
    public void shouldNotWriteFieldNameAsEscapedStringIf_ESCAPE_NON_ASCII_Disabled() throws IOException {
        jgen.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        jgen.writeStartObject();
        jgen.writeStringField("私はガラスを食べられます。それは私を傷つけません", "watashihagarasuotaberaremasu.sorehawatashiokizutsukemasen");
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("私はガラスを食べられます。それは私を傷つけません", "watashihagarasuotaberaremasu.sorehawatashiokizutsukemasen"));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // QUOTE_NON_NUMERIC_NUMBERS
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    public void shouldWriteFloatNaNAsStringIf_QUOTE_NON_NUMERIC_NUMBERS_Enabled() throws IOException {
        jgen.enable(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
        jgen.writeStartObject();
        jgen.writeNumberField("FloatValue", Float.NaN);
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("FloatValue", "NaN"));
    }
    
    @Test
    public void shouldNotWriteFloatNaNAsStringIf_QUOTE_NON_NUMERIC_NUMBERS_Disabled() throws IOException {
        jgen.disable(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
        jgen.writeStartObject();
        jgen.writeNumberField("FloatValue", Float.NaN);
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("FloatValue", Float.NaN));
    }
}
