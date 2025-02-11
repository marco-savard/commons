package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;

import static com.marcosavard.commons.math.Maths.range;

import java.text.MessageFormat;

public class MathsDemo {

	public static void main(String[] args) {
		demoRange();
		demoRound(); 
	}
	
	private static void demoRound() {
	   double rounded = Maths.round(Math.PI, 0.0001); 
	   Console.println("PI rounded : " + rounded);
	}

	public static void demoRange() {
		    // degrees in range 0..360
			Console.println("in range 0..360");
		    for (int i = 0; i < 16; i++) {
		      double angle = -60 + i * 30;
		      printRangedValue(angle, 0, 360);
		    }
			Console.println();

		    // degrees in range -180..180
			Console.println("in range -180..180");
		    for (int i = 0; i < 16; i++) {
		      double angle = -60 + i * 30;
		      printRangedValue(angle, -180, 180);
		    }
			Console.println();

		    // degrees in range -pi..pi
		    Console.println("in range -pi..pi");
		    for (int i = 0; i < 16; i++) {
		      double angle = Math.toRadians(-60 + i * 30);
		      printRangedValue(angle, -Math.PI, Math.PI);
		    }
		    Console.println();

		  }

	 
	  private static void printRangedValue(double value, double min, double max) {
		    double ranged = range(value, min, max);
		    String msg = MessageFormat.format("  {0} -> {1}", value, ranged);
		    System.out.println(msg);
		  }

}
