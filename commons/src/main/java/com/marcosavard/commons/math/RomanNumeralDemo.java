package com.marcosavard.commons.math;

import java.text.MessageFormat;

public class RomanNumeralDemo {
	
	public static void main(String[] args) {
		for (int i=1; i<=10; i++) {
			String msg = MessageFormat.format("{0} : {1}", i, new RomanNumeral(i)); 
			System.out.println(msg);
		}
	}
}
