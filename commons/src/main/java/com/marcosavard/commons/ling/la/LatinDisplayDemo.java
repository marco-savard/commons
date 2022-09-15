package com.marcosavard.commons.ling.la;

public class LatinDisplayDemo {

	public static void main(String[] args) {
		displayLatin("Audentes fortuna juvat"); 
		displayLatin("Jēsus Nazarēnus, Rēx Jūdaeōrum"); 
		displayLatin("Une aventure d'Astérix le Gaulois"); 
	}

	private static void displayLatin(String givenString) {
		String displayString = LatinDisplay.toDisplayString(givenString);
		System.out.println(displayString);
	}



}
