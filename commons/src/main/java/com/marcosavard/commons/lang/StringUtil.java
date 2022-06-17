package com.marcosavard.commons.lang;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * A utility class that provides useful methods that handle strings. It includes
 * methods to predicate whether a string is null or empty, to pad strings with
 * blanks, to remove quotes from strings, to strip accents from a string.
 *
 * @author Marco
 *
 */
public class StringUtil {
	private static final String ELLIPSIS = "\u2026"; // ...character
	private static final Character[] BOOLEANS = new Character[] {'0', '1', 't', 'f', 'y', 'n'};
	private static final Character[] VOYELS = new Character[] {'a', 'e', 'i', 'o', 'u'};

	// Utility class is not instantiable
	private StringUtil() {
	}

	  /**
	   * Abbreviate source at a given length
	   * 
	   * @param lenght to be truncate
	   * @return a string having at most 'lenght' characters
	   */
	public static String abbreviate(CharSequence original, int length) { 
		String abbreviated = abbreviate(original, length, ELLIPSIS); 
		return abbreviated; 
	}
	
	public static String abbreviate(CharSequence original, int lenght, String suffix) { 
		original = NullSafe.of(original);
		boolean tooLong = original.length() > lenght;
		String abbreviated = tooLong ? original.subSequence(0, lenght-1) + suffix : original.toString(); 
		return abbreviated; 
	}

	/**
	 * Capitalize the original string.
	 *
	 * @param original string
	 * @return capitalized string
	 */
	public static String capitalize(CharSequence original) {
	    return capitalize(original, Locale.getDefault()); 
   }
	
	public static String capitalize(CharSequence original, Locale locale) {
		original = NullSafe.of(original);
	    boolean empty = (original.length() == 0);
	    String firstLetter = toUpperCase(original.subSequence(0, 1), locale);
	    String capitalized = empty ? original.toString() : firstLetter + subSequence(original, 1);
	    return capitalized;
   }

	public static String capitalizeWords(CharSequence original) {
	    return capitalizeWords(original, Locale.getDefault()); 
   }

	/**
	 * Capitalize each words in sentence
	 *
	 * @param sentence
	 * @return
	 */
	public static String capitalizeWords(CharSequence original, Locale locale) {
		original = NullSafe.of(original);
		List<String> words = Arrays.asList(original.toString().split(" "));
		List<String> capitalizedWords = new ArrayList<>();

	    for (String word : words) {
	      capitalizedWords.add(capitalize(word, locale));
	    }

	    String joined = String.join(" ", capitalizedWords);
	    return joined;
    }
	
	public static String center(CharSequence original, int length) {
		original = NullSafe.of(original);
		int margin = length - original.length(); 
		int leftLength = original.length() + (margin / 2); 
		String centered = (margin > 0) ? padRight(padLeft(original, leftLength), length) : original.toString();
		return centered; 
	}
	
	public int compare(CharSequence original, CharSequence other) {
	   	boolean oneNull = (original == null) || (other == null); 
    	boolean bothNull = (original == null) && (other == null); 
		int comparison = oneNull ? (bothNull ? 0 : -1) : original.toString().compareTo(other.toString()); 
		return comparison;
	}
	
	public int compareIgnoreAccent(CharSequence original, CharSequence other) {
		int comparison = stripAccents(original).compareTo(stripAccents(other)); 
		return comparison;
	}
	
	public int compareIgnoreCase(CharSequence original, CharSequence other) {
		return compareIgnoreCase(original, other, Locale.getDefault()); 
	}
	
	public int compareIgnoreCase(CharSequence original, CharSequence other, Locale locale) {
		original = NullSafe.of(original);
		other = NullSafe.of(other);
		int comparison = toLowerCase(original, locale).compareToIgnoreCase(toLowerCase(other, locale)); 
		return comparison;
	}
	
	public int compareIgnoreCaseAndAccent(CharSequence original, CharSequence other) { 
		return compareIgnoreCaseAndAccent(original, other, Locale.getDefault()); 
	}
	
	public int compareIgnoreCaseAndAccent(CharSequence original, CharSequence other, Locale locale) {
		int comparison = stripAccents(toLowerCase(original, locale)).compareToIgnoreCase(stripAccents(toLowerCase(other, locale))); 
		return comparison;
	}

	// count occurrences of substring in str
	public static int countMatches(CharSequence original, CharSequence substring) {
		original = NullSafe.of(original);
		int count = original.length() - original.toString().replace(substring, "").length();
		return count;
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
	
	public boolean endsWith(CharSequence original, CharSequence suffix) {
		boolean endsWith = original.toString().endsWith(suffix.toString()); 
		return endsWith;
	}
	
	public boolean endsWithIgnoreAccent(CharSequence original, CharSequence suffix) {
		boolean endsWith = stripAccents(original).endsWith(stripAccents(suffix)); 
		return endsWith;
	}
	
	public boolean endsWithIgnoreCase(CharSequence original, CharSequence suffix) {
		boolean endsWith = toLowerCase(original).endsWith(toLowerCase(suffix)); 
		return endsWith;
	}
	
	public boolean endsWithIgnoreCaseAndAccent(CharSequence original, CharSequence suffix) {
		boolean endsWith = stripAccents(original).toLowerCase().endsWith(stripAccents(suffix).toLowerCase()); 
		return endsWith;
	}

    public static boolean equalsIgnoreAccents(CharSequence original, CharSequence other) {
    	boolean oneNull = (original == null) || (other == null); 
    	boolean bothNull = (original == null) && (other == null); 
	    String stripped = stripAccents(original);
	    boolean equal = oneNull ? bothNull : stripped.equals(stripAccents(other));
	    return equal;
	}
    
    public static boolean equalsIgnoreCaseAndAccents(CharSequence original, CharSequence other) {
    	return equalsIgnoreCaseAndAccents(original, other, Locale.getDefault());
    }
    
    public static boolean equalsIgnoreCaseAndAccents(CharSequence original, CharSequence other, Locale locale) {
    	boolean oneNull = (original == null) || (other == null); 
    	boolean bothNull = (original == null) && (other == null); 
	    String stripped = stripAccents(original).toLowerCase(locale);
	    boolean equal = oneNull ? bothNull : stripped.equals(stripAccents(other).toLowerCase(locale));
	    return equal;
	}
    
	public static int indexOfAny(CharSequence original, char... chars) {
		int smallestIdx = Integer.MAX_VALUE; 
		
		for (char ch : chars) { 
			int idx = NullSafe.of(original).toString().indexOf(ch); 
			smallestIdx = (idx >= 0) && (idx < smallestIdx) ? idx : smallestIdx; 
		}
		
		smallestIdx = (smallestIdx == Integer.MAX_VALUE) ? -1 : smallestIdx; 
		return smallestIdx;
	}
		
	public static boolean isBoolean(CharString original) {
		boolean isBoolean = false; 
		
		if ((original != null) && (! original.isEmpty())) { 
			char first = Character.toLowerCase(original.charAt(0)); 
		    List<Character> booleans = Arrays.asList(BOOLEANS);
		    isBoolean = booleans.contains(first);
		}
		
		return isBoolean;
	}
	  

	//returns true for "20201231", "2020/12/31", "2020-12-31" 
	public static boolean isDate(CharSequence original) {
		boolean date; 
		String stripped = stripNonDigit(original); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		try {
		  LocalDate.parse(stripped, formatter); 
		  date = true; 
		} catch (DateTimeParseException ex) { 
		  date = false;
		}

		return date;
	}
	
	public static boolean isInteger(CharSequence cs) {
		boolean integer; 
		
		try {
			Integer.parseInt(NullSafe.of(cs).toString());
			integer = true;
		} catch (NumberFormatException ex) {  
			integer = false;
		}
		
		return integer;
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

	public static boolean isNumber(CharSequence cs) {
		boolean number; 
		
		try {
			Double.parseDouble(NullSafe.of(cs).toString());
			number = true;
		} catch (NumberFormatException ex) {  
			number = false;
		}
		
		return number;
	}
	
	public static boolean isQuoted(CharSequence original) { 
		return isQuoted(original, '\"'); 
	} 
	
	public static boolean isQuoted(CharSequence original, char quoteCharacter) { 
		boolean quoted = false; 
		
		if (!isNullOrBlank(original)) {
		  int len = original.length();
		  char first = original.charAt(0); 
		  char last = original.charAt(len-1); 
		  quoted = (first == quoteCharacter) && (last == quoteCharacter); 
	    }
		
		return quoted; 
	}
	
	  /**
	   * Tells if text is null or blank
	   * 
	   * @param text a given string
	   * @return true if null or blank
	   */
    public static boolean isNullOrBlank(CharSequence original) {
	    boolean nullOrBlank = (original == null) || original.toString().trim().isEmpty();
	    return nullOrBlank;
	  }
 
    /**
     * Tells if text is null or empty
     * 
     * @param text a given string
     * @return true if null or empty
     */
    public static boolean isNullOrEmpty(CharSequence original) {
        boolean nullOrEmpty = (original == null) || original.isEmpty();
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
    
	public static int lastIndexOfAny(CharSequence original, char... chars) {
		int largestIdx = 0; 
		
		for (char ch : chars) { 
			int idx = NullSafe.of(original).toString().lastIndexOf(ch); 
			largestIdx = (idx >= 0) && (idx > largestIdx) ? idx : largestIdx; 
		}

		return largestIdx;
	}

	/**
	 * Pad n blanks at left
	 *
	 * @param source a given string
	 * @param n of blanks
	 * @return padded string
	 */
    public static String padLeft(CharSequence original, int totalLength) {
      String padded = String.format("%1$" + totalLength + "s", original);
      return padded;
    }

    public static String padRight(CharSequence original, int totalLength) {
      String padded = String.format("%1$-" + totalLength + "s", original);
      return padded;
    }

	public static String quote(CharSequence original) {
		String quoted = "\"" + NullSafe.of(original) + "\""; 
		return quoted;
	}
	
	public static String repeat(char ch, int len) {
		char[] chars = new char[len];
	    Arrays.fill(chars, ch);
	    String repeated = new String(chars); 
	    return repeated; 
	}
	  
	public static String[] splitLine(CharSequence original) {
		original = NullSafe.of(original);
		String[] splitted = original.toString().split("\\r?\\n");
	    return splitted; 
	}
	
	public boolean startsWith(CharSequence original, CharSequence prefix) {
		original = NullSafe.of(original);
		prefix = NullSafe.of(prefix);
		boolean startsWith = original.toString().startsWith(prefix.toString()); 
		return startsWith;
	}
	
	public boolean startsWithIgnoreAccent(CharSequence original, CharSequence suffix) {
		boolean startsWith = stripAccents(original).startsWith(stripAccents(suffix)); 
		return startsWith;
	}
	
	public boolean startsWithIgnoreCase(CharSequence original, CharSequence prefix) {
		boolean startsWith = toLowerCase(original).startsWith(toLowerCase(prefix)); 
		return startsWith;
	}

	/**
	 * Strip off accents from characters
	 *
	 * @param text with accents
	 * @return stripped text
	 */
	public static String stripAccents(CharSequence original) {
	    String stripped = Normalizer.normalize(NullSafe.of(original), Normalizer.Form.NFD);
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
	public static String stripBlanks(CharSequence original) {
	    // remove whitespaces and tabs
		original = NullSafe.of(original);
	    String stripped = original.toString().replaceAll("\\s", "");
	    return stripped;
	}

	/**
	 * Strip off non digit characters
	 *
	 * @param text a given string
	 * @return stripped string
	 */
	public static String stripNonDigit(CharSequence original) {
		original = NullSafe.of(original);
	    StringBuilder sb = new StringBuilder();
	    int nb = original.length();

	    for (int i = 0; i < nb; i++) {
	      char ch = original.charAt(i);
	      if (Character.isDigit(ch)) {
	        sb.append(ch);
	      }
	    }

	    return sb.toString();
	}
	
	public static CharSequence subSequence(CharSequence original, int beginIdx) {
		original = NullSafe.of(original);
		CharSequence substr = original.subSequence(beginIdx, original.length()); 
		return substr;
	}

	/**
	 * Convert "HELLO_WORLD" to "Hello World"
	 *
	 * @param original
	 * @return
	 */
	public static String toDisplayString(String original) {
		String displayed = original.replaceAll("_", " ");
		displayed = displayed.toLowerCase();
		displayed = capitalizeWords(displayed);
		return displayed;
	}
	
	public static String toLowerCase(CharSequence original) {
		return toLowerCase(original, Locale.getDefault()); 
	}
	
	public static String toLowerCase(CharSequence original, Locale locale) {
		original = NullSafe.of(original);
		String lowerCase = original.toString().toLowerCase(locale);
		return lowerCase;
	}
	
	public static String toUpperCase(CharSequence original) {
		return toUpperCase(original, Locale.getDefault()); 
	}
	
	public static String toUpperCase(CharSequence original, Locale locale) {
		original = NullSafe.of(original);
		String upperCase = original.toString().toUpperCase(locale);
		return upperCase;
	}
	
	public static String trimDoubleBlanks(CharSequence original) {
		original = NullSafe.of(original);
	    String trimmed = original.toString().replace(" +", " ");
	    return trimmed;
	}

	/**
	 * Truncate source at a given length
	 *
	 * @param source a given string
	 * @param lenght to be truncate
	 * @return a string having at most 'lenght' characters
	 */
	public static String truncate(CharSequence original, int lenght) { 
		original = NullSafe.of(original);
		boolean tooLong = original.length() > lenght;
		String truncated = tooLong ? original.subSequence(0, lenght).toString() : original.toString(); 
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
	public static CharSequence unquote(CharSequence original) {
		CharSequence unquoted = unquote(original, '\"');
		return unquoted;
	}
	
	public static CharSequence unquote(CharSequence original, char quoteCharacter) {
		CharSequence target = "";
		
		if (isQuoted(original, quoteCharacter)) { 
			int start = 1;
			int end = original.length() - 1; 
			target = original.subSequence(start, end);
		}

	    return target;
	  }


	

}
