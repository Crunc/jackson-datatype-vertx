package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import de.crunc.jackson.datatype.vertx.matcher.MoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.json.JsonArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static de.crunc.jackson.datatype.vertx.JsonArrayBuilder.array;
import static de.crunc.jackson.datatype.vertx.matcher.MoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration test for {@link JsonElementParser} with {@link ObjectMapper}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.1
 */
public class JsonElementParserBasicArrayMappingTest {

    private ObjectMapper objectMapper;
    private JsonParser jp;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReadIntArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2)
                .add(0)
                .add(1)
                .add(2)
                .add(3)
                .build();

        jp = new JsonElementParser(jsonArray);

        int[] array = objectMapper.readValue(jp, int[].class);

        assertThat(array, equalTo(new int[]{-2, 0, 1, 2, 3}));
    }

    @Test
    public void shouldReadBoxedIntArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2)
                .add(0)
                .add(1)
                .add(2)
                .add(3)
                .build();

        jp = new JsonElementParser(jsonArray);

        Integer[] array = objectMapper.readValue(jp, Integer[].class);

        assertThat(array, equalTo(new Integer[]{-2, 0, 1, 2, 3}));
    }

    @Test
    public void shouldReadLongArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2)
                .add(0)
                .add(1)
                .add(2)
                .add(3)
                .build();

        jp = new JsonElementParser(jsonArray);

        long[] array = objectMapper.readValue(jp, long[].class);

        assertThat(array, equalTo(new long[]{-2L, 0L, 1L, 2L, 3L}));
    }

    @Test
    public void shouldReadBoxedLongArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2)
                .add(0)
                .add(1)
                .add(2)
                .add(3)
                .build();

        jp = new JsonElementParser(jsonArray);

        Long[] array = objectMapper.readValue(jp, Long[].class);

        assertThat(array, equalTo(new Long[]{-2L, 0L, 1L, 2L, 3L}));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReadFloatArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        float[] array = objectMapper.readValue(jp, float[].class);

        assertThat(Floats.asList(array), contains(
                closeTo(-2.1f, 0.001f),
                closeTo(0.0f, 0.001f),
                closeTo(1.7f, 0.001f),
                closeTo(2.0f, 0.001f),
                closeTo(3.141f, 0.001f)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReadBoxedFloatArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        Float[] array = objectMapper.readValue(jp, Float[].class);

        assertThat(array, arrayContaining(
                closeTo(-2.1f, 0.001f),
                closeTo(0.0f, 0.001f),
                closeTo(1.7f, 0.001f),
                closeTo(2.0f, 0.001f),
                closeTo(3.141f, 0.001f)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReadDoubleArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        double[] array = objectMapper.readValue(jp, double[].class);

        assertThat(Doubles.asList(array), contains(
                closeTo(-2.1, 0.001),
                closeTo(0.0, 0.001),
                closeTo(1.7, 0.001),
                closeTo(2.0, 0.001),
                closeTo(3.141, 0.001)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReadBoxedDoubleArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        Double[] array = objectMapper.readValue(jp, Double[].class);

        assertThat(array, arrayContaining(
                closeTo(-2.1, 0.001),
                closeTo(0.0, 0.001),
                closeTo(1.7, 0.001),
                closeTo(2.0, 0.001),
                closeTo(3.141, 0.001)));
    }

    @Test
    public void shouldReadBigIntegerArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        BigInteger[] array = objectMapper.readValue(jp, BigInteger[].class);

        assertThat(array, equalTo(new BigInteger[]{
                new BigInteger("-2"),
                new BigInteger("0"),
                new BigInteger("1"),
                new BigInteger("2"),
                new BigInteger("3"),
        }));
    }

    @Test
    public void shouldReadBigDecimalArray() throws IOException {
        JsonArray jsonArray = array()
                .add(-2.1)
                .add(0.0)
                .add(1.7)
                .add(2.0)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        BigDecimal[] array = objectMapper.readValue(jp, BigDecimal[].class);

        assertThat(array, equalTo(new BigDecimal[]{
                new BigDecimal("-2.1"),
                new BigDecimal("0.0"),
                new BigDecimal("1.7"),
                new BigDecimal("2.0"),
                new BigDecimal("3.141"),
        }));
    }

    @Test
    public void shouldReadStringArray() throws IOException {
        JsonArray jsonArray = array()
                .add("A")
                .add("B")
                .add("C")
                .build();

        jp = new JsonElementParser(jsonArray);

        String[] array = objectMapper.readValue(jp, String[].class);

        assertThat(array, arrayContaining("A", "B", "C"));
    }

    @Test
    public void shouldReadMixedObjectArray() throws IOException {
        JsonArray jsonArray = array()
                .add("Foobar")
                .add(42)
                .addNull()
                .add(true)
                .add(false)
                .add(3.141)
                .build();

        jp = new JsonElementParser(jsonArray);

        Object[] array = objectMapper.readValue(jp, Object[].class);

        assertThat(array, MoreMatchers.<Object>arrayContaining(
                "Foobar", 42, null, true, false, 3.141));
    }
}
