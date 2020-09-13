package com.marcosavard.commons.math;

import java.text.MessageFormat;

public class InRangeDemo {

  public static void main(String[] args) {

    // degrees in range 0..360
    demo(360, 0, 360);
    demo(390, 0, 360);
    System.out.println();


    // degrees in range -180..180
    demo(-180, -180, 180);
    demo(-20, -180, 180);
    demo(180, -180, 180);
    demo(270, -180, 180);
    System.out.println();


    System.out.println();
  }

  private static void demo(double value, double min, double max) {
    double rangedValue = InRange.rangeOld(value, min, max);
    String msg =
        MessageFormat.format("{0} in range [{1}..{2}] -> {3}", value, min, max, rangedValue);
    System.out.println(msg);

    msg = MessageFormat.format("{0} in range [{1}..{2}] -> {3}", value, min, max, rangedValue);
    System.out.println(msg);

    System.out.println();
  }

}
