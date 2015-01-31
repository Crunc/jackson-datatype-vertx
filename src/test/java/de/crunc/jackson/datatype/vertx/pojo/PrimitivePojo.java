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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimitivePojo that = (PrimitivePojo) o;

        if (Double.compare(that.doubleValue, doubleValue) != 0) return false;
        if (Float.compare(that.floatValue, floatValue) != 0) return false;
        if (intValue != that.intValue) return false;
        if (longValue != that.longValue) return false;
        if (bigDecimalValue != null ? !bigDecimalValue.equals(that.bigDecimalValue) : that.bigDecimalValue != null)
            return false;
        if (bigIntegerValue != null ? !bigIntegerValue.equals(that.bigIntegerValue) : that.bigIntegerValue != null)
            return false;
        if (doubleValueBoxed != null ? !doubleValueBoxed.equals(that.doubleValueBoxed) : that.doubleValueBoxed != null)
            return false;
        if (floatValueBoxed != null ? !floatValueBoxed.equals(that.floatValueBoxed) : that.floatValueBoxed != null)
            return false;
        if (intValueBoxed != null ? !intValueBoxed.equals(that.intValueBoxed) : that.intValueBoxed != null)
            return false;
        if (longValueBoxed != null ? !longValueBoxed.equals(that.longValueBoxed) : that.longValueBoxed != null)
            return false;
        if (nullValue != null ? !nullValue.equals(that.nullValue) : that.nullValue != null) return false;
        if (stringValue != null ? !stringValue.equals(that.stringValue) : that.stringValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = intValue;
        result = 31 * result + (intValueBoxed != null ? intValueBoxed.hashCode() : 0);
        result = 31 * result + (int) (longValue ^ (longValue >>> 32));
        result = 31 * result + (longValueBoxed != null ? longValueBoxed.hashCode() : 0);
        result = 31 * result + (floatValue != +0.0f ? Float.floatToIntBits(floatValue) : 0);
        result = 31 * result + (floatValueBoxed != null ? floatValueBoxed.hashCode() : 0);
        temp = Double.doubleToLongBits(doubleValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (doubleValueBoxed != null ? doubleValueBoxed.hashCode() : 0);
        result = 31 * result + (bigIntegerValue != null ? bigIntegerValue.hashCode() : 0);
        result = 31 * result + (bigDecimalValue != null ? bigDecimalValue.hashCode() : 0);
        result = 31 * result + (stringValue != null ? stringValue.hashCode() : 0);
        result = 31 * result + (nullValue != null ? nullValue.hashCode() : 0);
        return result;
    }
}
