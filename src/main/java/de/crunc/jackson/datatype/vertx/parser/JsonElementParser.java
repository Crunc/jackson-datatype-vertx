package de.crunc.jackson.datatype.vertx.parser;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonElement;
import org.vertx.java.core.json.JsonObject;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.fasterxml.jackson.core.JsonToken.*;

/**
 * Parses instances of type {@link JsonElement}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.1
 */
public class JsonElementParser extends ParserMinimalBase {
/*
    /**********************************************************
    /* Configuration
    /**********************************************************
     */

    protected ObjectCodec objectCodec;

    /**
     * Traversal context within tree
     */
    protected AbstractTreeCursor<Object> cursor;

    /*
    /**********************************************************
    /* State
    /**********************************************************
     */

    /**
     * Sometimes parser needs to buffer a single look-ahead token; if so,
     * it'll be stored here. This is currently used for handling
     */
    protected JsonToken nextToken;

    /**
     * Flag needed to handle recursion into contents of child
     * Array/Object nodes.
     */
    protected boolean startContainer;

    /**
     * Flag that indicates whether parser is closed or not. Gets
     * set when parser is either closed by explicit call
     * ({@link #close}) or when end-of-input is reached.
     */
    protected boolean closed;

    public JsonElementParser(JsonElement element) {
        this(element, null);
    }

    public JsonElementParser(JsonElement element, @Nullable ObjectCodec codec) {
        super(0);

        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }

        objectCodec = codec;
        cursor = new JsonElementRootCursor(element);
    }

    @Override
    public void setCodec(ObjectCodec c) {
        objectCodec = c;
    }

    @Override
    public ObjectCodec getCodec() {
        return objectCodec;
    }

    @Override
    public Version version() {
        return com.fasterxml.jackson.databind.cfg.PackageVersion.VERSION;
    }

    @Override
    public void close() throws IOException {
        if (!closed) {
            closed = true;
            cursor = null;
            _currToken = null;
        }
    }

    @Override
    public JsonToken nextToken() throws IOException {
//        if (nextToken != null) {
//            _currToken = nextToken;
//            nextToken = null;
//            return _currToken;
//        }
        
        // are we to descend to a container child?
        if (startContainer) {
            startContainer = false;
            
            // minor optimization: empty containers can be skipped
            if (!cursor.currentHasChildren()) {
                _currToken = (_currToken == START_OBJECT) ? END_OBJECT : END_ARRAY;
                return _currToken;
            }
            
            cursor = cursor.iterateChildren();
            _currToken = cursor.nextToken();
            if ((_currToken == START_OBJECT) || (_currToken == START_ARRAY)) {
                startContainer = true;
            }
            return _currToken;
        }
        
        // No more content?
        if (cursor == null) {
            closed = true; // if not already set
            return null;
        }
        
        // Otherwise, next entry from current cursor
        _currToken = cursor.nextToken();
        
        if (_currToken != null) {
            if (_currToken == START_OBJECT || _currToken == START_ARRAY) {
                startContainer = true;
            }
            return _currToken;
        }
        
        // null means no more children; need to return end marker
        _currToken = cursor.endToken();
        cursor = cursor.getParent();
        return _currToken;
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        if (_currToken == START_OBJECT) {
            startContainer = false;
            _currToken = END_OBJECT;
        } else if (_currToken == START_ARRAY) {
            startContainer = false;
            _currToken = END_ARRAY;
        }
        return this;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public String getCurrentName() {
        return (cursor == null) ? null : cursor.getCurrentName();
    }

    @Override
    public void overrideCurrentName(String name) {
        if (cursor != null) {
            cursor.overrideCurrentName(name);
        }
    }

    @Override
    public JsonStreamContext getParsingContext() {
        return cursor;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return JsonLocation.NA;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        return JsonLocation.NA;
    }

    @Override
    public String getText() {
        if (closed) {
            return null;
        }
        
        if (_currToken == null) {
            return null;
        }
        
        switch (_currToken) {
            case FIELD_NAME:
                return cursor.getCurrentName();
            case VALUE_STRING:
                return (String) currentNode();
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                return String.valueOf(currentNode());
            case VALUE_TRUE:
                return "true";
            case VALUE_FALSE:
                return "false";
            case VALUE_EMBEDDED_OBJECT:
                return String.valueOf(currentNode());
            default:
                return _currToken.asString();
        }
    }

    @Override
    public char[] getTextCharacters() throws IOException {
        return getText().toCharArray();
    }

    @Override
    public int getTextLength() throws IOException {
        return getText().length();
    }

    @Override
    public int getTextOffset() throws IOException {
        return 0;
    }

    @Override
    public boolean hasTextCharacters() {
        // generally we do not have efficient access as char[], hence:
        return false;
    }

    @Override
    public NumberType getNumberType() throws IOException {
        Number n = currentNumber();

        if (n instanceof Integer) {
            return NumberType.INT;
        } else if (n instanceof Long) {
            return NumberType.LONG;
        } else if (n instanceof Float) {
            return NumberType.FLOAT;
        } else if (n instanceof Double) {
            return NumberType.DOUBLE;
        } else if (n instanceof BigDecimal) {
            return NumberType.BIG_DECIMAL;
        } else if (n instanceof BigInteger) {
            return NumberType.BIG_INTEGER;
        }

        return null;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        Number n = currentNumber();

        if (n instanceof BigInteger) {
            return (BigInteger) n;
        } else {
            return BigInteger.valueOf(n.longValue());
        }
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException {
        Number n = currentNumber();

        if (n instanceof BigDecimal) {
            return (BigDecimal) n;
        } else {
            return new BigDecimal(n.toString());
        }
    }

    @Override
    public double getDoubleValue() throws IOException {
        return currentNumber().doubleValue();
    }

    @Override
    public float getFloatValue() throws IOException {
        return (float) currentNumber().doubleValue();
    }

    @Override
    public long getLongValue() throws IOException {
        return currentNumber().longValue();
    }

    @Override
    public int getIntValue() throws IOException {
        return currentNumber().intValue();
    }

    @Override
    public Number getNumberValue() throws IOException {
        return currentNumber();
    }

    @Override
    public Object getEmbeddedObject() {
        return currentNode();
//        if (!closed) {
//            JsonNode n = currentNode();
//            if (n != null) {
//                if (n.isPojo()) {
//                    return ((POJONode) n).getPojo();
//                }
//                if (n.isBinary()) {
//                    return ((BinaryNode) n).binaryValue();
//                }
//            }
//        }
//        return null;
    }

    /*
    /**********************************************************
    /* Public API, typed binary (base64) access
    /**********************************************************
     */

    @Override
    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
        throw new UnsupportedOperationException();
        // Multiple possibilities...
//        JsonNode n = currentNode();
//        if (n != null) { // binary node?
//            byte[] data = n.binaryValue();
//            // (or TextNode, which can also convert automatically!)
//            if (data != null) {
//                return data;
//            }
//            // Or maybe byte[] as POJO?
//            if (n.isPojo()) {
//                Object ob = ((POJONode) n).getPojo();
//                if (ob instanceof byte[]) {
//                    return (byte[]) ob;
//                }
//            }
//        }
//        // otherwise return null to mark we have no binary content
//        return null;
    }


    @Override
    public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
        byte[] data = getBinaryValue(b64variant);
        if (data != null) {
            out.write(data, 0, data.length);
            return data.length;
        }
        return 0;
    }

    /*
    /**********************************************************
    /* Internal methods
    /**********************************************************
     */

    protected Object currentNode() {
        if (closed || cursor == null) {
            return null;
        }
        return cursor.currentElement();
    }

    protected Number currentNumber() throws JsonParseException {
        Object n = currentNode();

        if (n instanceof Number) {
            return (Number) n;
        }
        else if(n instanceof String) {
            try {
                return new BigDecimal((String) n);
            } catch (NumberFormatException e) {
                throw _constructError("String value <" + n + "> is not numeric", e);
            }
        }

        throw _constructError("Current token (" + _currToken + ") not numeric, can not access numeric value");
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        _throwInternal(); // should never get called
    }
}
