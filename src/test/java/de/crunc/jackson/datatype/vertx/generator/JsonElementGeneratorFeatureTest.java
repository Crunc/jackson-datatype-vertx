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
}
