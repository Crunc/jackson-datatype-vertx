package de.crunc.jackson.datatype.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.crunc.jackson.datatype.vertx.parser.JsonElementParser;
import de.crunc.jackson.datatype.vertx.pojo.SamplePojo;
import org.junit.Test;
import io.vertx.core.json.JsonObject;

import java.io.IOException;

import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static de.crunc.jackson.datatype.vertx.matcher.MoreMatchers.*;

/**
 * Sample unit test for {@link JsonElementParser}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class ObjectMarshallerSampleTest {

    @Test
    public void shouldMarshallSamplePojo() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectMarshaller marshaller = new ObjectMarshaller(mapper);

        SamplePojo pojo = new SamplePojo("Foobar");

        JsonObject json = marshaller.marshall(pojo);

        assertThat(json, isJsonObject().prop("message", "Foobar"));
    }

    @Test
    public void shouldUnmarshallSamplePojo() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectMarshaller marshaller = new ObjectMarshaller(mapper);

        JsonObject json = new JsonObject();
        json.put("message", "Hello pojo");

        SamplePojo pojo = marshaller.unmarshall(json, SamplePojo.class);

        assertThat(pojo, equalTo(new SamplePojo("Hello pojo")));
    }
}
