package com.marcosavard.commons.math.type;

import java.text.MessageFormat;
import org.junit.Assert;

public class BaseDemo {

  public static void main(String[] args) {
    testEncodeLength(75);
    testEncodeHours(3600);
    testEncodeHexa(100);
    testMayanLongCount(400);
  }

  private static void testEncodeLength(int inches) {
    // should give 2 yards 0 foot 3 inches
    Base usLength = Base.of(1760, 3, 12);
    long[] encoded = usLength.encode(inches);
    long result = usLength.decode(encoded);
    Assert.assertEquals(result, inches);

    String msg = MessageFormat.format("{0} inches encoded yard-feet-inch {1}", inches,
        Base.toString(encoded));
    System.out.println(msg);
  }

  private static void testEncodeHours(int seconds) {
    Base timeBase = Base.of(24, 60, 60);
    long[] encoded = timeBase.encode(seconds);
    long result = timeBase.decode(encoded);
    Assert.assertEquals(result, seconds);

    String msg =
        MessageFormat.format("{0} seconds encoded in hms {1}", seconds, Base.toString(encoded));
    System.out.println(msg);
  }

  private static void testEncodeHexa(int decimal) {
    Base hexaBase = Base.of(16, 16, 16);
    long[] encoded = hexaBase.encode(decimal);
    long result = hexaBase.decode(encoded);
    Assert.assertEquals(result, decimal);

    String msg = MessageFormat.format("{0} encoded in hexa {1}", decimal, Base.toString(encoded));
    System.out.println(msg);
  }

  private static void testMayanLongCount(int days) {
    Base longCount = Base.of(20, 20, 18, 20);
    long[] encoded = longCount.encode(days);
    long result = longCount.decode(encoded);
    Assert.assertEquals(result, days);

    String msg = MessageFormat.format("{0} days encoded in {1}", days, Base.toString(encoded));
    System.out.println(msg);
  }

}
