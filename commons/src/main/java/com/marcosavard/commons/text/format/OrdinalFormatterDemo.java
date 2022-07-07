package com.marcosavard.commons.text.format;

import java.util.Locale;

public class OrdinalFormatterDemo {

	public static void main(String[] args) {
		OrdinalFormatter formatter = new OrdinalFormatter(Locale.FRENCH); 
		for (int i=1; i<=25; i++) {
			String text = formatter.format(i); 
			System.out.println("  " + text);
		}

	}

}
