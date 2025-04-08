package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;

public class FastSquareRootDemo {
  private static final long NB_ITERATIONS = 10_000_000;

  public static void main(String[] args) {
    demoSpeed();
    demoAccuracy();

  }

  public static void demoSpeed() {
    long start, duration;
    double number = 0;

    start = System.currentTimeMillis();
    for (int i = 0; i < NB_ITERATIONS; i++) {
      number = 10 + Math.sqrt(number);
    }

    duration = System.currentTimeMillis() - start;
    Console.println("duration Math.sqrt : {0} ms", duration);
    number = 0;

    start = System.currentTimeMillis();
    for (int i = 0; i < NB_ITERATIONS; i++) {
      number = 10 + FastMath.sqrt(i * 10);
    }
    duration = System.currentTimeMillis() - start;
    Console.println("duration FastMath.sqrt : {0} ms", duration);
  }

  private static void demoAccuracy() {
    for (int i = 0; i < 16; i++) {
      Console.println("sqrt({0})) = {1} (classical) vs {2} (fast)", i, Math.sqrt(i), FastMath.sqrt(i));
    }
  }


}
