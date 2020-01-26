package com.marcosavard.commons.math;

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

  public double nextSquaredDouble() {
    double squared = nextDouble() * nextDouble();
    return squared;
  }
}
