package de.crunc.jackson.datatype.vertx.pojo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A pojo which contains a field for every primitive datatype that is relevant when it comes to JSON parsing.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class PrimitivePojo {

    private int intValue;
    private Integer intValueBoxed;
    private long longValue;
    private Long longValueBoxed;
    private float floatValue;
    private Float floatValueBoxed;
    private double doubleValue;
    private Double doubleValueBoxed;
    private BigInteger bigIntegerValue;
    private BigDecimal bigDecimalValue;
    private String stringValue;
    private String nullValue;

    public PrimitivePojo() {

    }
    
    public PrimitivePojo(int intValue, Integer intValueBoxed, long longValue, Long longValueBoxed, float floatValue, Float floatValueBoxed, double doubleValue, Double doubleValueBoxed, BigInteger bigIntegerValue, BigDecimal bigDecimalValue, String stringValue, String nullValue) {
        this.intValue = intValue;
        this.intValueBoxed = intValueBoxed;
        this.longValue = longValue;
        this.longValueBoxed = longValueBoxed;
        this.floatValue = floatValue;
        this.floatValueBoxed = floatValueBoxed;
        this.doubleValue = doubleValue;
        this.doubleValueBoxed = doubleValueBoxed;
        this.bigIntegerValue = bigIntegerValue;
        this.bigDecimalValue = bigDecimalValue;
        this.stringValue = stringValue;
        this.nullValue = nullValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public Integer getIntValueBoxed() {
        return intValueBoxed;
    }

    public void setIntValueBoxed(Integer intValueBoxed) {
        this.intValueBoxed = intValueBoxed;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public Long getLongValueBoxed() {
        return longValueBoxed;
    }

    public void setLongValueBoxed(Long longValueBoxed) {
        this.longValueBoxed = longValueBoxed;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public Float getFloatValueBoxed() {
        return floatValueBoxed;
    }

    public void setFloatValueBoxed(Float floatValueBoxed) {
        this.floatValueBoxed = floatValueBoxed;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Double getDoubleValueBoxed() {
        return doubleValueBoxed;
    }

    public void setDoubleValueBoxed(Double doubleValueBoxed) {
        this.doubleValueBoxed = doubleValueBoxed;
    }

    public BigInteger getBigIntegerValue() {
        return bigIntegerValue;
    }

    public void setBigIntegerValue(BigInteger bigIntegerValue) {
        this.bigIntegerValue = bigIntegerValue;
    }

    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue;
    }

    public void setBigDecimalValue(BigDecimal bigDecimalValue) {
        this.bigDecimalValue = bigDecimalValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    @Override
    public String toString() {
        return "PrimitivePojo{" +
                "intValue=" + intValue +
                ", intValueBoxed=" + intValueBoxed +
                ", longValue=" + longValue +
                ", longValueBoxed=" + longValueBoxed +
                ", floatValue=" + floatValue +
                ", floatValueBoxed=" + floatValueBoxed +
                ", doubleValue=" + doubleValue +
                ", doubleValueBoxed=" + doubleValueBoxed +
                ", bigIntegerValue=" + bigIntegerValue +
                ", bigDecimalValue=" + bigDecimalValue +
                ", stringValue='" + stringValue + '\'' +
                ", nullValue='" + nullValue + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                intValue, 
                intValueBoxed, 
                longValue, 
                longValueBoxed,
                floatValue, 
                floatValueBoxed, 
                doubleValue, 
                doubleValueBoxed, 
                bigIntegerValue, 
                bigDecimalValue, 
                stringValue, 
                nullValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PrimitivePojo other = (PrimitivePojo) obj;
        return Objects.equal(this.intValue, other.intValue) 
                && Objects.equal(this.intValueBoxed, other.intValueBoxed) 
                && Objects.equal(this.longValue, other.longValue) 
                && Objects.equal(this.longValueBoxed, other.longValueBoxed) 
                && Objects.equal(this.floatValue, other.floatValue) 
                && Objects.equal(this.floatValueBoxed, other.floatValueBoxed) 
                && Objects.equal(this.doubleValue, other.doubleValue) 
                && Objects.equal(this.doubleValueBoxed, other.doubleValueBoxed) 
                && Objects.equal(this.bigIntegerValue, other.bigIntegerValue) 
                && Objects.equal(this.bigDecimalValue, other.bigDecimalValue) 
                && Objects.equal(this.stringValue, other.stringValue) 
                && Objects.equal(this.nullValue, other.nullValue);
    }
}
