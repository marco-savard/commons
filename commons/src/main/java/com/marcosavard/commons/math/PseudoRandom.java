package com.marcosavard.commons.math;

public class NonRandom { 
	private double current = Math.PI;

	public NonRandom(int seed) {
		this.current = Math.PI * (1 + seed);
	}

	public double nextDouble() {
		double remainder = Math.ceil(this.current) - this.current; 
		this.current = remainder * 19 * 29 * 31;
		return remainder;
	}
}
