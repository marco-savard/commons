package com.marcosavard.commons.util;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class that provides useful methods that handle strings. It includes
 * methods to predicate whether a string is null or empty, to pad strings with
 * blanks, to remove quotes from strings, to strip accents from a string.
 * 
 * @author Marco
 *
 */
public class StringUtil {
	private static final String ELLIPSIS = "\u2026"; // ... character
	private static final Character[] VOYELS = new Character[] { 'a', 'e', 'i', 'o', 'u' };

	// Utility class is not instantiable
	private StringUtil() {
	}

	/**
	 * Capitalize the original string.
	 * 
	 * @param original string
	 * @return capitalized string
	 */
	public static String capitalize(String original) {
		String capitalized = (original == null) ? "" : original;
		capitalized = (capitalized.length() == 0) ? capitalized : capitalized.substring(0, 1).toUpperCase() + capitalized.substring(1);
		return capitalized;
	}

	public static String capitalizeWords(String original) {
		String[] words = original.split(" ");

		for (String word : words) {
			word = capitalize(word);
		}

		String joined = String.join(" ", words);
		return joined;
	}

	//count occurrences of substring in str
	public static int countOccurences(String str, String substring) {
		int count = str.length() - str.replace(substring, "").length();
		return count;
	}
	
	/**
	 * Returns the same string, but empty is null.
	 * 
	 * @param text to check
	 * @return text or "" is text is empty
	 */
	public static String emptyIfNull(String text) {
		String dest = (text == null) ? "" : text;
		return dest;
	}

	/**
	 * Tells if text is null or blank
	 * 
	 * @param text a given string
	 * @return true if null or blank
	 */
	public static boolean isNullOrBlank(String text) {
		boolean nullOrBlank = (text == null) || text.trim().isEmpty();
		return nullOrBlank;
	}

	/**
	 * Tells if text is null or empty
	 * 
	 * @param text a given string
	 * @return true if null or empty
	 */
	public static boolean isNullOrEmpty(String text) {
		boolean nullOrEmpty = (text == null) || text.isEmpty();
		return nullOrEmpty;
	}

	/**
	 * Tells if c is a voyel
	 * 
	 * @param c given character
	 * @return true if voyel
	 */
	public static boolean isVoyel(char c) {
		c = Character.toLowerCase(c);
		List<Character> voyels = Arrays.asList(VOYELS);
		boolean voyel = voyels.contains(c);
		return voyel;
	}

	/**
	 * Pad n blanks at left
	 * 
	 * @param source a given string
	 * @param n of blanks
	 * @return padded string
	 */
	public static String padLeftBlanks(String source, int n) {
		String padded = (n > 0) ? String.format("%1$" + n + "s", source) : source;
		return padded;
	}

	public static String pad(String source, int width) {
		StringBuilder sb = new StringBuilder();
		int n = Math.max(0, width - source.length());

		for (int i = 0; i < n; i++) {
			sb.append(" ");
		}

		sb.append(source);
		return sb.toString();
	}
	
	public static boolean startsWith(String str, String prefix) {
		boolean startsWith = ! isNullOrEmpty(str) && str.startsWith(prefix); 
		return startsWith;
	}

	/**
	 * Strip off accents from characters
	 * 
	 * @param text with accents
	 * @return stripped text
	 */
	public static String stripAccents(String text) {
		String stripped = Normalizer.normalize(text, Normalizer.Form.NFD);
		stripped = stripped.replaceAll("[^\\p{ASCII}]", "");
		return stripped;
	}

	/**
	 * Return a string in which all the blanks (whitespaces and tabs) of the text
	 * parameter are stripped off. For instance, stripBlanks("hello world") returns
	 * "helloworld".
	 * 
	 * @param text the original String, that may contain characters with blanks.
	 * @return the same String, but without blanks
	 */
	public static String stripBlanks(String text) {
		// remove whitespaces and tabs
		text = text.replaceAll("\\s", "");
		return text;
	}

	/**
	 * Strip off non digit characters
	 * 
	 * @param text a given string
	 * @return stripped string
	 */
	public static String stripNonDigit(String text) {
		StringBuilder sb = new StringBuilder();
		int nb = text.length();

		for (int i = 0; i < nb; i++) {
			char ch = text.charAt(i);
			if (Character.isDigit(ch)) {
				sb.append(ch);
			}
		}

		return sb.toString();
	}

	/**
	 * Truncate source at a given length
	 * 
	 * @param source a given string
	 * @param lenght to be truncate
	 * @return a string having at most 'lenght' characters
	 */
	public static String truncate(String source, int lenght) {
		String truncated = (source == null) ? "" : source;
		boolean trunk = truncated.length() > lenght;
		truncated = trunk ? truncated.substring(0, lenght - 1) + ELLIPSIS : truncated;
		return truncated;
	}

	/**
	 * Returns the same String, but without the quotes. For instance,
	 * removeSurroundingQuotes("'text'", '\'') returns "text".
	 * 
	 * @param text whose the first and last characters may be quote characters.
	 * @param quoteCharacter such as ' or "
	 * @return the text without the quotes, if any
	 */
	public static String unquote(String text, char quoteCharacter) {
		String target = "";

		if (!isNullOrBlank(text)) {
			char ch = text.charAt(0);
			target = (ch == quoteCharacter) ? text.substring(1) : text;

			if (!isNullOrBlank(target)) {
				int len = target.length() - 1;
				ch = target.charAt(len);
				target = (ch == quoteCharacter) ? target.substring(0, len) : target;
			}
		}

		return target;
	}



}
