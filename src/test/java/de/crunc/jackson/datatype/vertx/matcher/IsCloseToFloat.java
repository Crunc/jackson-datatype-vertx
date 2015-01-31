package de.crunc.jackson.datatype.vertx.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.number.IsCloseTo;

/**
 * Similar to {@link IsCloseTo} for {@code float} values.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.1
 */
public class IsCloseToFloat extends TypeSafeMatcher<Float> {

    private final float error;
    private final float value;

    public IsCloseToFloat(float value, float error) {
        this.error = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(Float item) {
        return actualDelta(item) <= 0.0;
    }

    @Override
    public void describeMismatchSafely(Float item, Description mismatchDescription) {
        mismatchDescription.appendValue(item)
                .appendText(" differed by ")
                .appendValue(actualDelta(item));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(error)
                .appendText(" of ")
                .appendValue(value);
    }

    private double actualDelta(Float item) {
        return (Math.abs((item - value)) - error);
    }
}
