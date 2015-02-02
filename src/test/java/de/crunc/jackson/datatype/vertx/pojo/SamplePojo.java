package de.crunc.jackson.datatype.vertx.pojo;

/**
 * Plain old java object :)
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
public class SamplePojo {
    
    private String message;

    public SamplePojo() {

    }
    
    public SamplePojo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SamplePojo that = (SamplePojo) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }
}
