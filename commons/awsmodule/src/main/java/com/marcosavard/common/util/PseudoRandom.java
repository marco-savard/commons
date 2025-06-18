package com.marcosavard.common.util;

import java.util.Random;

public class PseudoRandom extends Random  {
    private double current;

    public PseudoRandom(int seed) {
        this.current = Math.E * (1 + seed);
    }

    @Override
    public double nextDouble() {
        double remainder = Math.ceil(this.current) - this.current;
        this.current = remainder * 19 * 29 * 31;
        return remainder;
    }

    // return pseudo-random from 0 (inclusive) to upperBound (exclusive)
    @Override
    public int nextInt(int upperBound) {
        return (int) Math.floor(nextDouble() * upperBound);
    }

}
