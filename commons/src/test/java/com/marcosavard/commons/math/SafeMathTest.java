package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class SafeMathTest {

    @Test
    void test() {
        double x = 3, y = 4, z = 5;
        double[] spherical = SafeMath.toSpherical(x, y, z);
        Console.println("{0}", spherical);

        double[] rectangular = SafeMath.toRectangular(spherical);
        Console.println("{0}", rectangular);

        Assert.assertEquals(x, rectangular[0]);
        Assert.assertEquals(y, rectangular[1]);
        Assert.assertEquals(z, rectangular[2]);
    }
}
