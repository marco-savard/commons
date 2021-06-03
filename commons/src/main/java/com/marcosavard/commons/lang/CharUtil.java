package com.marcosavard.commons.lang;

import java.text.Normalizer;

public class CharUtil {
	public static final String VOYELS = "aeiou"; 
	public static final String FRENCH_DIACRITICS = "ÀÂÄÇÉÈÊËÎÏÔÖÙÛÜŸàâäçéèêëïîôöùûüÿ"; 
	public static final String FRENCH_LIGATURES = "ÆŒæœ"; //AE, OE, ae, oe
	
	public static boolean isAscii(char c) {
		boolean ascii = (c <= 127); 
		return ascii;
	}

	public static boolean isDiacritical(char c) {
		boolean diacritical = Character.isLetter(c) && ! isAscii(c); 
		return diacritical;
	}

	public static boolean isVowel(char c) {
	  c = Character.toLowerCase(stripAccent(c)); 
	  boolean voyel = (VOYELS.indexOf(c) >= 0);   
      return voyel;
	}
	
	public static char stripAccent(char c) {
		String normalized = Normalizer.normalize(Character.toString(c), Normalizer.Form.NFD); 
		String stripped = normalized.replaceAll("[^\\p{ASCII}]", "");
		return stripped.charAt(0); 
	}


	
	

}
