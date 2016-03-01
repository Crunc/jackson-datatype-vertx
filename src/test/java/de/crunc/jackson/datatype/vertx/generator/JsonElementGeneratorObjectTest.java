package de.crunc.jackson.datatype.vertx.generator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static de.crunc.hamcrest.json.JsonMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link JsonElementGenerator}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementGeneratorObjectTest {

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
    public void shouldWriteBooleanAfterFieldName() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeFieldName("BooleanValue");
            jgen.writeBoolean(false);
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("BooleanValue", false));
    }

    @Test
    public void shouldWriteNumberAfterFieldName() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeFieldName("NumberValue");
            jgen.writeNumber(13.37f);
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("NumberValue", isJsonNumber(13.37f, 0.001f)));
    }

    @Test
    public void shouldWriteStringAfterFieldName() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeFieldName("StringValue");
            jgen.writeString("Foo bar");
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("StringValue", "Foo bar"));
    }

    @Test
    public void shouldWriteNullAfterFieldName() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeFieldName("NullValue");
            jgen.writeNull();
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("NullValue", null));
    }

    @Test
    public void shouldWriteBinaryAfterFieldName() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeFieldName("BinaryValue");
            jgen.writeBinary(new byte[]{0x01, 0x02, 0x03});
        }
        jgen.writeEndObject();


        assertThat(jgen.get(), isJsonObject()
                .prop("BinaryValue", BaseEncoding.base64().encode(new byte[]{0x01, 0x02, 0x03})));
    }

    @Test
    public void shouldWriteObjectAfterFieldName() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeFieldName("ChildObject");
            jgen.writeStartObject();
            jgen.writeEndObject();
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("ChildObject", isJsonObject()));
    }

    @Test
    public void shouldWriteArrayAfterFieldName() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeFieldName("ChildArray");
            jgen.writeStartArray();
            jgen.writeEndArray();
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("ChildArray", isJsonArray()));
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteBooleanAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeBoolean(true);
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteNumberAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeNumber(-3);
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteStringAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeString("NotAllowed");
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteNullAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeNull();
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteBinaryAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeBinary("Not allowed".getBytes());
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteStartObjectAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeStartObject();
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteStartArrayAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeStartArray();
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteEndArrayAtObject() throws IOException {
        jgen.writeStartObject();
        jgen.writeEndArray();
    }

    @Test
    public void shouldWriteBooleanFieldAtObject() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeBooleanField("BooleanValue", true);
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("BooleanValue", true));
    }

    @Test
    public void shouldWriteNumberFieldAtObject() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeNumberField("NumberValue", new Double(0.004d));
            // ggh - vertx 3 doesn't support BigDecimal
            //jgen.writeNumberField("NumberValue", new BigDecimal("0.004"));
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("NumberValue", isJsonNumber(0.004, 0.0001)));
    }

    @Test
    public void shouldWriteStringFieldAtObject() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeStringField("StringValue", "");
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("StringValue", ""));
    }

    @Test
    public void shouldWriteNullFieldAtObject() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeNullField("NullValue");
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("NullValue", null));
    }

    @Test
    public void shouldWriteBinaryFieldAtObject() throws IOException {
        jgen.writeStartObject();
        {
            jgen.writeBinaryField("BinaryValue", new byte[]{
                    0x31, 0x32, 0x33, 0x34,
                    0x35, 0x36, 0x37, 0x38
            });
        }
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject()
                .prop("BinaryValue", BaseEncoding.base64().encode(new byte[]{
                        0x31, 0x32, 0x33, 0x34,
                        0x35, 0x36, 0x37, 0x38
                })));
    }
}
