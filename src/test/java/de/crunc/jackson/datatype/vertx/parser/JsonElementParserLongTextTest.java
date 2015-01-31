package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static com.fasterxml.jackson.core.JsonToken.*;
import static de.crunc.jackson.datatype.vertx.JsonObjectBuilder.object;
import static de.crunc.jackson.datatype.vertx.matcher.JsonParserMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

/**
 * Unit test for {@link JsonElementParser}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class JsonElementParserLongTextTest extends JsonElementParserBaseTest {

    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {7700},
                {49000},
                {96000},
        });
    }

    @Parameterized.Parameter(0)
    public int length;

    @Test
    public void testLongText() {
        final String VALUE = generateLongText(length);

        JsonParser jp = createParser(object()
                .put("LongText", VALUE)
                .build());

        Assert.assertThat(jp, nextToken(START_OBJECT));

        Assert.assertThat(jp, nextToken(FIELD_NAME));
        Assert.assertThat(jp, hasCurrentName("LongText"));

        Assert.assertThat(jp, nextToken(VALUE_STRING));
        Assert.assertThat(jp, hasTextValue(VALUE));

        Assert.assertThat(jp, hasCurrentName("LongText"));

        Assert.assertThat(jp, nextToken(END_OBJECT));
        Assert.assertThat(jp, nextToken(nullValue()));
    }

    private String generateLongText(int length) {
        StringBuilder sb = new StringBuilder(length + 100);
        Random r = new Random(99);
        while (sb.length() < length) {
            sb.append(r.nextInt());
            sb.append(" xyz foo");
            if (r.nextBoolean()) {
                sb.append(" and \"bar\"");
            } else if (r.nextBoolean()) {
                sb.append(" [whatever].... ");
            } else {
                // Let's try some more 'exotic' chars
                sb.append(" UTF-8-fu: try this {\u00E2/\u0BF8/\uA123!} (look funny?)");
            }
            if (r.nextBoolean()) {
                if (r.nextBoolean()) {
                    sb.append('\n');
                } else if (r.nextBoolean()) {
                    sb.append('\r');
                } else {
                    sb.append("\r\n");
                }
            }
        }
        return sb.toString();
    }
}
