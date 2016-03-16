package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.crunc.jackson.datatype.vertx.pojo.EmptyPojo;
import de.crunc.jackson.datatype.vertx.pojo.PrimitivePojo;
import org.junit.Before;
import org.junit.Test;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static de.crunc.jackson.datatype.vertx.matcher.MoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration test for {@link JsonElementParser} with {@link ObjectMapper}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class JsonElementParserPojoMappingTest extends JsonElementParserBaseTest {

    private ObjectMapper objectMapper;
    private JsonParser jp;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReadEmptyPojo() throws IOException {
        jp = createParser(object());
        EmptyPojo pojo = objectMapper.readValue(jp, EmptyPojo.class);

        assertThat(pojo, is(not(nullValue())));
    }

    @Test
    public void shouldReadPrimitivePojo() throws IOException {
        JsonObject object = object()
                .put("intValue", 1)
                .put("intValueBoxed", -2)
                .put("longValue", 3)
                .put("longValueBoxed", -4)
                .put("floatValue", 5.23)
                .put("floatValueBoxed", -7.889)
                .put("doubleValue", 3.141)
                .put("doubleValueBoxed", -31498.193)
                .put("bigIntegerValue", 1230L)
                .put("bigDecimalValue", 123098.17)
                .put("stringValue", "HalliGalli")
                .putNull("nullValue")
                .build();

        jp = createParser(object);

        PrimitivePojo pojo = objectMapper.readValue(jp, PrimitivePojo.class);

        assertThat(pojo, equalTo(new PrimitivePojo(
                1,
                -2,
                3L,
                -4L,
                5.23f,
                -7.889f,
                3.141,
                -31498.193,
                new BigInteger("1230"),
                new BigDecimal("123098.17"),
                "HalliGalli",
                null)));
    }
}
