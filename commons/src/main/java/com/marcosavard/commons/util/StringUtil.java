package com.marcosavard.commons.util;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class that provides useful methods that handle strings. 
 * 
 * @author Marco
 *
 */
public class StringUtil {
	private static final Character[] VOYELS = new Character[] {'a', 'e', 'i', 'o', 'u'};
	
	/**
	 * Capitalize the original string.  
	 * 
	 * @param original
	 * @return
	 */
	public static String capitalize(String original) {
		String s = (original == null) ? "" : original; 
		s = (s.length() == 0) ? s : s.substring(0, 1).toUpperCase() + s.substring(1); 
		return s; 
	}
	
	/**
	 * Tells if text is null or empty 
	 * 
	 * @param text
	 * @return true if null or empty
	 */
	public static boolean isNullOrEmpty(String text) {
		boolean nullOrEmpty = (text == null) || text.isEmpty();
		return nullOrEmpty;
	}
	
	/**
	 * Tells if text is null or blank 
	 * 
	 * @param text
	 * @return true if null or blank
	 */
	public static boolean isNullOrBlank(String text) {
		boolean nullOrBlank = (text == null) || text.trim().isEmpty();
		return nullOrBlank;
	}

	/**
	 * Tells if c is a voyel
	 * @param c
	 * @return true if voyel
	 */
	public static boolean isVoyel(char c) {
		c = Character.toLowerCase(c); 
		List<Character> voyels = Arrays.asList(VOYELS); 
		boolean voyel = voyels.contains(c);
		return voyel;
	}
	
	/**
	 * Strip off accents from characters
	 * @param text with accents
	 * @return stripped text
	 */
	public static String stripAccents(String text) {
		String stripped = Normalizer.normalize(text, Normalizer.Form.NFD);
		stripped = stripped.replaceAll("[^\\p{ASCII}]", "");
		return stripped; 
	}

	/**
	 * Strip off non digit characters
	 * @param text
	 * @return
	 */
	public static String stripNonDigit(String text) {
		StringBuilder sb = new StringBuilder(); 
		int nb = text.length();
		
		for (int i=0; i<nb; i++) {
			char ch = text.charAt(i); 
			if (Character.isDigit(ch)) {
				sb.append(ch); 
			}
		}
		
		return sb.toString();
	}

}
