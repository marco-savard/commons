package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;

public class FastSquareRootDemo {

  public static void main(String[] args) {

    for (int i = 0; i < 10; i++) {
      double number = i * 5;
      double d1 = Math.sqrt(number);
      double d2 = fastsqrt(number);
      Console.println("sqrt({0}) = {1} vs {2}", number, d1, d2);
    }

    long start = System.currentTimeMillis();
    for (int i = 0; i < 1_000_000; i++) {
      double d1 = fastsqrt(i * 10);
      d1++;
    }
    long duration = System.currentTimeMillis() - start;
    Console.println("duration = {0} ms", duration);
  }

  public static double fastsqrt(double number) {
    double x = number;
    double xhalf = 0.5d * x;
    long i = Double.doubleToLongBits(x);
    i = 0x5fe6ec85e7de30daL - (i >> 1);
    x = Double.longBitsToDouble(i);
    for (int it = 0; it < 4; it++) {
      x = x * (1.5d - xhalf * x * x);
    }
    x *= number;
    return x;
  }
}
