package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.crunc.jackson.datatype.vertx.matcher.MoreMatchers;
import io.vertx.core.json.JsonArray;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static de.crunc.jackson.datatype.vertx.JsonArrayBuilder.array;
import static de.crunc.jackson.datatype.vertx.matcher.MoreMatchers.closeTo;
import static de.crunc.jackson.datatype.vertx.matcher.MoreMatchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration test for {@link JsonElementParser} with {@link ObjectMapper}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.1
 */
public class JsonElementParserSimpleListMappingTest {

    private ObjectMapper objectMapper;
    private JsonParser jp;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReadIntList() throws IOException {
        JsonArray jsonArray = array()
                .add(-2)
                .add(0)
                .add(1)
                .add(2)
                .add(3)
                .build();

        jp = new JsonElementParser(jsonArray);

        List<Integer> list = objectMapper.readValue(jp, new TypeReference<List<Integer>>() {
        });

        assertThat(list, contains(-2, 0, 1, 2, 3));
    }

    @Test
    public void shouldReadLongList() throws IOException {
        JsonArray jsonArray = array()
                .add(-2)
                .add(0)
                .add(1)
                .add(2)
                .add(3)
                .build();

        jp = new JsonElementParser(jsonArray);

        List<Long> list = objectMapper.readValue(jp, new TypeReference<List<Long>>() {
        });

        assertThat(list, contains(-2L, 0L, 1L, 2L, 3L));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReadFloatList() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        List<Float> list = objectMapper.readValue(jp, new TypeReference<List<Float>>() {
        });

        assertThat(list, contains(
                closeTo(-2.1f, 0.001f),
                closeTo(0.0f, 0.001f),
                closeTo(1.7f, 0.001f),
                closeTo(2.0f, 0.001f),
                closeTo(3.141f, 0.001f)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReadDoubleList() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        List<Double> list = objectMapper.readValue(jp, new TypeReference<List<Double>>() {
        });

        assertThat(list, contains(
                closeTo(-2.1, 0.001),
                closeTo(0.0, 0.001),
                closeTo(1.7, 0.001),
                closeTo(2.0, 0.001),
                closeTo(3.141, 0.001)));
    }

    @Test
    public void shouldReadBigIntegerList() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        List<BigInteger> list = objectMapper.readValue(jp, new TypeReference<List<BigInteger>>() {
        });

        assertThat(list, contains(
                new BigInteger("-2"),
                new BigInteger("0"),
                new BigInteger("1"),
                new BigInteger("2"),
                new BigInteger("3")));
    }

    @Test
    public void shouldReadBigDecimalList() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        List<BigDecimal> list = objectMapper.readValue(jp, new TypeReference<List<BigDecimal>>() {
        });

        assertThat(list, contains(
                new BigDecimal("-2.1"),
                new BigDecimal("0.0"),
                new BigDecimal("1.7"),
                new BigDecimal("2.0"),
                new BigDecimal("3.141")));
    }

    @Test
    public void shouldReadStringList() throws IOException {
        JsonArray jsonArray = array()
                .add("A")
                .add("B")
                .add("C")
                .build();

        jp = new JsonElementParser(jsonArray);

        List<String> list = objectMapper.readValue(jp, new TypeReference<List<String>>() {
        });

        assertThat(list, contains("A", "B", "C"));
    }

    @Test
    public void shouldReadMixedObjectList() throws IOException {
        JsonArray jsonArray = array()
                .add("Foobar")
                .add(42)
                .addNull()
                .add(true)
                .add(false)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        List<Object> list = objectMapper.readValue(jp, new TypeReference<List<Object>>() {
        });

        assertThat(list, MoreMatchers.<Object>contains("Foobar", 42, null, true, false, 3.141));
    }
}
