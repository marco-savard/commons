package com.marcosavard.commons.math;

import java.text.MessageFormat;

public class InRangeDemo {

  public static void main(String[] args) {

    // degrees in range 0..360
    System.out.println("in range 0..360");
    for (int i = 0; i < 16; i++) {
      double angle = -60 + i * 30;
      demo(angle, 0, 360);
    }
    System.out.println();

    // degrees in range -180..180
    System.out.println("in range -180..180");
    for (int i = 0; i < 16; i++) {
      double angle = -60 + i * 30;
      demo(angle, -180, 180);
    }
    System.out.println();

    // degrees in range -pi..pi
    System.out.println("in range -pi..pi");
    for (int i = 0; i < 16; i++) {
      double angle = Math.toRadians(-60 + i * 30);
      demo(angle, -Math.PI, Math.PI);
    }
    System.out.println();

  }

  private static void demo(double value, double min, double max) {
    double ranged = InRange.range(value, min, max);
    String msg = MessageFormat.format("  {0} -> {1}", value, ranged);
    System.out.println(msg);
  }

}