package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.JsonParser;
import de.crunc.jackson.datatype.vertx.JsonArrayBuilder;
import de.crunc.jackson.datatype.vertx.JsonObjectBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

abstract class JsonElementParserBaseTest {
    
    private Set<JsonParser> parsers;
    
    @Before
    public void prepareParsers() {
        closeParsers();
        parsers = new HashSet<JsonParser>();
    }

    @After
    public void tearDown() throws IOException {
        closeParsers();
        parsers = null;
    }
    
    private void closeParsers() {
        if (parsers != null) {
            for (JsonParser parser : parsers) {
                try {
                    parser.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    protected JsonParser createParser(Object element) {
        JsonParser jp = new JsonElementParser(element);
        parsers.add(jp);
        return jp;
    }

    protected JsonParser createParser(JsonObjectBuilder builder) {
        return createParser(builder.build());
    }

    protected JsonParser createParser(JsonArrayBuilder builder) {
        return createParser(builder.build());
    }
}
