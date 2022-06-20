package com.marcosavard.commons.math.arithmetic;

import org.junit.Assert;
import org.junit.Test;

public class BaseTest {

    @Test
    public void testEncodeLength() {
        // should give 2 yards 0 foot 3 inches
        int inches = 75;
        Base usLength = Base.of(1760, 3, 12);
        long[] encoded = usLength.encode(inches);
        long result = usLength.decode(encoded);
        Assert.assertEquals(result, inches);
    }

    @Test
    public void testEncodeHours() {
        int seconds = 3600;
        Base timeBase = Base.of(24, 60, 60);
        long[] encoded = timeBase.encode(seconds);
        long result = timeBase.decode(encoded);
        Assert.assertEquals(result, seconds);
    }

    @Test
    public void testEncodeHexa() {
        int decimal = 100;
        Base hexaBase = Base.of(16, 16, 16);
        long[] encoded = hexaBase.encode(decimal);
        long result = hexaBase.decode(encoded);
        Assert.assertEquals(result, decimal);
    }

    @Test
    public void testMayanLongCount() {
        int days = 400;
        Base longCount = Base.of(20, 20, 18, 20);
        long[] encoded = longCount.encode(days);
        long result = longCount.decode(encoded);
        Assert.assertEquals(result, days);
    }
}
