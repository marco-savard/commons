package com.marcosavard.commons.math.arithmetic;

import com.marcosavard.commons.debug.Console;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.IntStream;

public class PseudoRandomDemo {

  public static void main(String[] args) {
    printPseudoRandomShuffle();
    // printPseudoRandomDistribution();
    // printSquaredPseudoRandomDistribution();
  }

  private static void printPseudoRandomShuffle() {
    List<Character> letters = IntStream.rangeClosed('A', 'Z').mapToObj(var -> (char) var).toList();
    Console.println(letters);

    for (int i=0; i<5; i++) {
      PseudoRandom pr = new PseudoRandom(i);
      List<Character> shuffled = pr.shuffle(letters);
      Console.println(shuffled);
    }
  }

  private static void printPseudoRandomDistribution() {
    Quadrant quadrant = new Quadrant(4);
    PseudoRandom r1 = new PseudoRandom(0);
    PseudoRandom r2 = new PseudoRandom(1);

    for (int i = 0; i < 1000; i++) {
      double d1 = r1.nextDouble();
      double d2 = r2.nextDouble();
      quadrant.add(d1, d2);
    }

    System.out.println(quadrant);
    System.out.println();
  }

  private static void printSquaredPseudoRandomDistribution() {
    Quadrant quadrant = new Quadrant(4);
    PseudoRandom r1 = new PseudoRandom(0);
    PseudoRandom r2 = new PseudoRandom(1);

    for (int i = 0; i < 1000; i++) {
      double squared = r1.nextDouble() * r2.nextDouble();
      quadrant.add(squared);
    }

    System.out.println(quadrant);
    System.out.println();
  }

  private static class Quadrant {
    private int size;
    private int[] quadrants;
    private int total;

    public Quadrant(int size) {
      this.size = size;
      quadrants = new int[size];
    }

    public void add(double... values) {
      for (double value : values) {
        int idx = (int) Math.floor(value * size);
        quadrants[idx]++;
        total++;
      }
    }

    @Override
    public String toString() {
      String msg =
          MessageFormat.format(
              "[q1: {0}, q2:{1}, q3:{2}, q4:{3}]", //
              Percent.of(quadrants[0], total), //
              Percent.of(quadrants[1], total), //
              Percent.of(quadrants[2], total), //
              Percent.of(quadrants[3], total));
      return msg;
    }
  }
}
