package de.crunc.jackson.datatype.vertx.generator;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link JsonElementGenerator}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementGeneratorRootTest {

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
    public void shouldWriteObjectAtRoot() throws IOException {
        jgen.writeStartObject();
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject());
    }

    @Test
    public void shouldWriteArrayAtRoot() throws IOException {
        jgen.writeStartObject();
        jgen.writeEndObject();

        assertThat(jgen.get(), isJsonObject());
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteFieldNameAtRoot() throws IOException {
        jgen.writeFieldName("NotAllowed");
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteBooleanAtRoot() throws IOException {
        jgen.writeBoolean(true);
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteNumberAtRoot() throws IOException {
        jgen.writeNumber(-3);
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteStringAtRoot() throws IOException {
        jgen.writeString("Not allowed");
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteNullAtRoot() throws IOException {
        jgen.writeNull();
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteEndObjectAtRoot() throws IOException {
        jgen.writeEndObject();
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteEndArrayAtRoot() throws IOException {
        jgen.writeEndArray();
    }

    @Test(expected = JsonGenerationException.class)
    public void shouldNotWriteBinaryAtRoot() throws IOException {
        jgen.writeBinary("Not allowed".getBytes());
    }
}
