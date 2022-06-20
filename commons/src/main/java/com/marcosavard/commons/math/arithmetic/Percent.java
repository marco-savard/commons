package com.marcosavard.commons.math.arithmetic;

import java.text.MessageFormat;

public class Percent {
  private double value;

  public static Percent of(double numerator, double denominator) {
    return of(numerator / denominator);
  }

  public static Percent of(double ratio) {
    return new Percent(ratio);
  }

  private Percent(double value) {
    this.value = value;
  }

  @Override
  public String toString() {
    String text = String.format("%.2f", value * 100);
    String msg = MessageFormat.format("{0}%", text);
    return msg;
  }

}
