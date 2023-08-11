package com.marcosavard.commons.math.arithmetic;

import java.text.MessageFormat;

public class Percent {
  private double value;
  private int precision = 2; // 2 digits after point, by default

  public static Percent of(double numerator, double denominator) {
    return of(numerator / denominator);
  }

  public static Percent of(double ratio) {
    return new Percent(ratio);
  }

  private Percent(double value) {
    this.value = value;
  }

  public Percent withPrecision(int precision) {
    this.precision = precision;
    return this;
  }

  @Override
  public String toString() {
    String pattern = "%." + precision + "f";
    String text = String.format(pattern, value * 100);
    String msg = MessageFormat.format("{0}%", text);
    return msg;
  }
}
