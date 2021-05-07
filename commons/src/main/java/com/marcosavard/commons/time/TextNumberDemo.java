package com.marcosavard.commons.time;

import java.util.Locale;

public class TextNumberDemo {

	public static void main(String[] args) {
		Locale locale = Locale.FRENCH; 
		printFibbonacci(locale); 
		//printUpTo110(locale); 
	}

	private static void printUpTo110(Locale locale) {
		for (int i=0; i<=110; i++) { 
			System.out.println("  " + i + " : " + TextNumber.of(i, locale)); 
		}
	}

	private static void printFibbonacci(Locale locale) {
		int a=0, b = 1; 
		for (int i=0; i<46; i++) { 
			int c = a + b;
			a = b;
			b = c;
			System.out.println("  " + c + " : " + TextNumber.of(c, locale)); 
		}
	}
	
	

}
