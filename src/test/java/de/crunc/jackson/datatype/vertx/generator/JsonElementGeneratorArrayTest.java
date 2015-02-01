package de.crunc.jackson.datatype.vertx.generator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static de.crunc.hamcrest.json.JsonMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link JsonElementGenerator}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementGeneratorArrayTest {

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
    public void shouldWriteBooleanAtArray() throws IOException {
        jgen.writeStartArray();
        {
            jgen.writeBoolean(false);
        }
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item(false));
    }

    @Test
    public void shouldWriteNumberAtArray() throws IOException {
        jgen.writeStartArray();
        {
            jgen.writeNumber(13.37f);
        }
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item(isJsonNumber(13.37f, 0.001f)));
    }

    @Test
    public void shouldWriteStringAtArray() throws IOException {
        jgen.writeStartArray();
        {
            jgen.writeString("Foo bar");
        }
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item("Foo bar"));
    }

    @Test
    public void shouldWriteNullAtArray() throws IOException {
        jgen.writeStartArray();
        {
            jgen.writeNull();
        }
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item(null));
    }

    @Test
    public void shouldWriteBinaryAtArray() throws IOException {
        jgen.writeStartArray();
        {
            jgen.writeBinary(new byte[]{0x01, 0x02, 0x03});
        }
        jgen.writeEndArray();


        assertThat(jgen.get(), isJsonArray()
                .item(BaseEncoding.base64().encode(new byte[]{0x01, 0x02, 0x03})));
    }

    @Test
    public void shouldWriteObjectAtArray() throws IOException {
        jgen.writeStartArray();
        {
            jgen.writeStartObject();
            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item(isJsonObject()));
    }

    @Test
    public void shouldWriteArrayAtArray() throws IOException {
        jgen.writeStartArray();
        {
            jgen.writeStartArray();
            jgen.writeEndArray();
        }
        jgen.writeEndArray();

        assertThat(jgen.get(), isJsonArray()
                .item(isJsonArray()));
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteFieldNameAtArray() throws IOException {
        jgen.writeStartArray();
        jgen.writeFieldName("NotAllowed");
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteEndObjectAtArray() throws IOException {
        jgen.writeStartArray();
        jgen.writeEndObject();
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteBooleanFieldAtObject() throws IOException {
        jgen.writeStartArray();
        jgen.writeBooleanField("BooleanValue", true);
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteNumberFieldAtObject() throws IOException {
        jgen.writeStartArray();
        jgen.writeNumberField("NumberValue", new BigDecimal("0.004"));
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteStringFieldAtObject() throws IOException {
        jgen.writeStartArray();
        jgen.writeStringField("StringValue", "");
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteNullFieldAtObject() throws IOException {
        jgen.writeStartArray();
        jgen.writeNullField("NullValue");
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteBinaryFieldAtObject() throws IOException {
        jgen.writeStartArray();
        jgen.writeBinaryField("BinaryValue", new byte[]{
                0x31, 0x32, 0x33, 0x34,
                0x35, 0x36, 0x37, 0x38
        });
    }
}
