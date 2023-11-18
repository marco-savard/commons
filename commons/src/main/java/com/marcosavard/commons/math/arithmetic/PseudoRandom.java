package com.marcosavard.commons.math.arithmetic;

import java.util.ArrayList;
import java.util.List;

public class PseudoRandom {
  private double current = Math.PI;

  public PseudoRandom(int seed) {
    this.current = Math.PI * (1 + seed);
  }

  public double nextDouble() {
    double remainder = Math.ceil(this.current) - this.current;
    this.current = remainder * 19 * 29 * 31;
    return remainder;
  }

  // return pseudo-random from 0 (inclusive) to upperBound (exclusive)
  public int nextInt(int upperBound) {
    return (int) Math.floor(nextDouble() * upperBound);
  }

  public double nextSquaredDouble() {
    double squared = nextDouble() * nextDouble();
    return squared;
  }

  // based on Fisher–Yates shuffle Algorithm
  public <T> List<T> shuffle(List<T> original) {
    List<T> shuffled = new ArrayList<>();
    shuffled.addAll(original);
    int count = shuffled.size();

    for (int i = count - 1; i > 0; i--) {
      // pick a random index from 0 to i
      int j = this.nextInt(i);

      // swap arr[i] with the element at random index
      T temp = shuffled.get(i);
      shuffled.set(i, shuffled.get(j));
      shuffled.set(j, temp);
    }

    return shuffled;
  }
}
