package com.marcosavard.commons.math.arithmetic;

import com.marcosavard.commons.math.Range;

import java.math.BigInteger;
import java.text.MessageFormat;

public class Factorial {
  private int n;

  public static Factorial of(int n) {
    return new Factorial(n);
  }

  private Factorial(int n) {
    this.n = n;
  }

  @Override
  public String toString() {
    BigInteger factorial = getValue();
    String msg = MessageFormat.format("{0}", factorial);
    return msg;
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
