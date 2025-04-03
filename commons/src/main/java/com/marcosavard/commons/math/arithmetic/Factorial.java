package com.marcosavard.commons.math.arithmetic;

import com.marcosavard.commons.math.Range;

import java.math.BigInteger;

public class Factorial {
  private static final int MAX_VALUE = 20;
  private static long[] cachedResults;
  private int n;

  // gives value!
  public static long toLong(short value) {
    if (cachedResults == null) {
      cacheResults();
    }

    return value > MAX_VALUE ? Long.MAX_VALUE : cachedResults[value];
  }

  private static void cacheResults() {
    cachedResults = new long[MAX_VALUE+1];
    cachedResults[0] = 1;
    long result = 1;

    for (int i=1;i<=MAX_VALUE; i++) {
      result = result * i;
      cachedResults[i] = result;
    }
  }


  public static Factorial of(int n) {
    return new Factorial(n);
  }

  private Factorial(int n) {
    this.n = n;
  }

  @Override
  public String toString() {
    BigInteger factorial = getValue();
    return factorial.toString();
  }

  public BigInteger getValue() {
    BigInteger value = Range.of(n).addTo(1).product();
    return value;
  }

  // avoid cancellation-error
  public BigInteger minus(Factorial that) {
    int delta = this.n - that.n;
    BigInteger factorial = Range.of(delta - 1).addTo(this.n - delta + 1).product();
    return factorial;
  }
}
