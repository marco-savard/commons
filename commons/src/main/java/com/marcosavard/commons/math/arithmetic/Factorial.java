package com.marcosavard.commons.math.arithmetic;

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
    BigInteger value = IntegerList.of(n).multiply();
    return value;
  }

  // avoid cancellation-error
  public BigInteger minus(Factorial that) {
    int delta = this.n - that.n;
    BigInteger factorial = IntegerList.of(delta - 1).addTo(this.n - delta).multiply();
    return factorial;
  }


}
