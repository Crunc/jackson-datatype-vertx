package de.crunc.jackson.datatype.vertx.matcher;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsArrayContaining;
import org.hamcrest.collection.IsArrayContainingInOrder;

import java.util.Arrays;

/**
 * Provides some additional matcher factory methods like {@link Matchers#equalTo(Object)}.
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 * @since 2.1
 */
public class MoreMatchers extends Matchers {

    public static Matcher<Float> closeTo(float expected, float error) {
        return new IsCloseToFloat(expected, error);
    }

    private MoreMatchers() {
        throw new UnsupportedOperationException(MoreMatchers.class.getName() + " may not be instantiated");
    }
}
