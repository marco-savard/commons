package com.marcosavard.commons.math;

import java.util.Arrays;

// eg convert 0.5 days in hh:mm:ss
// mayan calendar 20-20-18-20

public class Base {
  private long[] units;

  public static Base of(long... unit) {
    return new Base(unit);
  }

  public Base(long[] units) {
    this.units = units;
  }

  public long[] encode(long src) {
    long[] encoded = new long[units.length];
    long quotient = 1;

    for (int i = units.length - 1; i >= 0; i--) {
      encoded[i] = (src / quotient) % units[i];
      quotient *= units[i];
    }

    return encoded;
  }

  public long decode(long[] encoded) {
    long decoded = 0;
    long product = 1;

    for (int i = units.length - 1; i >= 0; i--) {
      decoded += (encoded[i] * product);
      product *= units[i];
    }

    return decoded;
  }

  public static String toString(long[] result) {
    String joined = String.join(", ", Arrays.toString(result));
    return joined;
  }


}
