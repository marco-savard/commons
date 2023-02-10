package com.marcosavard.commons.math.arithmetic;

import java.text.MessageFormat;

public class BaseDemo {

  public static void main(String[] args) {
    testEncodeLength(75);
    testEncodeHours(3600);
    testEncodeHexa(100);
    testLeapYears();
    testMayanLongCount(400);
  }

  private static void testLeapYears() {
    Base leapBase = Base.of(4, 25, 4);

    for (int year = 1896; year <= 2020; year++) {
      long[] encoded = leapBase.encode(year);

      boolean leap = (encoded[2] == 0) && (encoded[1] != 0 || encoded[0] == 0);

      if (leap) {
        String msg =
            MessageFormat.format(
                "  {0} is leap : {1}, encoded = {2}", year, leap, Base.toString(encoded));

        System.out.println(msg);
      }
    }
  }

  private static void testEncodeLength(int inches) {
    // should give 2 yards 0 foot 3 inches
    Base usLength = Base.of(1760, 3, 12);
    long[] encoded = usLength.encode(inches);

    String msg =
        MessageFormat.format(
            "{0} inches encoded yard-feet-inch {1}", inches, Base.toString(encoded));
    System.out.println(msg);
  }

  private static void testEncodeHours(int seconds) {
    Base timeBase = Base.of(24, 60, 60);
    long[] encoded = timeBase.encode(seconds);

    String msg =
        MessageFormat.format("{0} seconds encoded in hms {1}", seconds, Base.toString(encoded));
    System.out.println(msg);
  }

  private static void testEncodeHexa(int decimal) {
    Base hexaBase = Base.of(16, 16, 16);
    long[] encoded = hexaBase.encode(decimal);

    String msg = MessageFormat.format("{0} encoded in hexa {1}", decimal, Base.toString(encoded));
    System.out.println(msg);
  }

  private static void testMayanLongCount(int days) {
    Base longCount = Base.of(20, 20, 18, 20);
    long[] encoded = longCount.encode(days);
    long result = longCount.decode(encoded);

    String msg = MessageFormat.format("{0} days encoded in {1}", days, Base.toString(encoded));
    System.out.println(msg);
  }
}
