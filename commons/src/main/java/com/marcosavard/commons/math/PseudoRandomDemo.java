package com.marcosavard.commons.math;

public class NonRandomDemo {

  public static void main(String[] args) {
    printNonRandoms();

  }

  private static void printNonRandoms() {
    NonRandom r1 = new NonRandom(0);
    NonRandom r2 = new NonRandom(1);

    for (int i = 0; i < 1000; i++) {
      System.out.println(r1.nextDouble());
      System.out.println(r2.nextDouble());
      System.out.println();
    }
  }
}
