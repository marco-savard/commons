package com.marcosavard.commons.lang;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that provides useful methods that handle strings. It includes methods to predicate
 * whether a string is null or empty, to pad strings with blanks, to remove quotes from strings, to
 * strip accents from a string.
 * 
 * @author Marco
 *
 */
public class CharString implements CharSequence, StringOperations, CharStringOperations {
  private static final CharString EMPTY = new CharString("");
  private static final String ELLIPSIS = "\u2026"; // ...character
  private static final Character[] VOYELS = new Character[] {'a', 'e', 'i', 'o', 'u'};

  private String value; // nullSafe value

  public static CharString of(String string) {
    CharString cs;

    if (isNullOrEmpty(string)) {
      cs = EMPTY;
    } else {
      cs = new CharString(string);
    }

    return cs;
  }

  public static CharString of(char ch, int len) {
    char[] chars = new char[len];
    Arrays.fill(chars, ch);
    CharString cs = new CharString(new String(chars));
    return cs;
  }

  private CharString(String value) {
    this.value = (value == null ? "" : value);
  }

  @Override
  public char charAt(int index) {
    return value.charAt(index);
  }

  @Override
  public int length() {
    return value.length();
  }

  @Override
  public CharSequence subSequence(int beginIndex, int endIndex) {
    return value.subSequence(beginIndex, endIndex);
  }

  @Override
  public boolean equals(Object that) {
    boolean equal = false;

    if (that instanceof CharSequence) {
      CharSequence thatSequence = (CharSequence) that;
      equal = thatSequence.equals(this);
    }

    return equal;
  }

  @Override
  public int hashCode() {
    return this.value.hashCode();
  }

  @Override
  public String concat(String suffix) {
    return value.concat(suffix);
  }

  @Override
  public boolean endsWith(String suffix) {
    return value.endsWith(suffix);
  }

  @Override
  public boolean equalsIgnoreCase(String that) {
    return value.equalsIgnoreCase(that);
  }

  @Override
  public int indexOf(String str) {
    return value.indexOf(str);
  }

  @Override
  public int indexOf(String str, int index) {
    return value.indexOf(str, index);
  }

  @Override
  public int lastIndexOf(String str) {
    return value.lastIndexOf(str);
  }

  @Override
  public int lastIndexOf(String str, int fromIndex) {
    return value.lastIndexOf(str, fromIndex);
  }

  @Override
  public String replace(String original, String replacement) {
    return this.replace(original, replacement);
  }

  @Override
  public String replaceAll(String original, String replacement) {
    return value.replace(original, replacement);
  }

  @Override
  public String[] split(String regex) {
    return value.split(regex);
  }

  @Override
  public String[] splitLine() {
    return value.split("\\r?\\n");
  }

  @Override
  public boolean startsWith(String prefix) {
    return value.startsWith(prefix);
  }

  @Override
  public String substring(int beginIndex) {
    return value.substring(beginIndex);
  }

  @Override
  public String substring(int beginIndex, int endIndex) {
    return value.substring(beginIndex, endIndex);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public String toUpperCase() {
    return value.toUpperCase();
  }

  @Override
  public String toLowerCase() {
    return value.toLowerCase();
  }

  @Override
  public String trim() {
    return value.trim();
  }

  @Override
  public String capitalize() {
    boolean empty = (this.length() == 0);
    String firstLetter = this.substring(0, 1).toUpperCase();
    String capitalized = empty ? this.toString() : firstLetter + this.substring(1);
    return capitalized;
  }

  @Override
  public String capitalizeWords() {
    List<String> words = Arrays.asList(this.split(" "));
    List<String> capitalizedWords = new ArrayList<>();

    for (String word : words) {
      CharString cs = CharString.of(word);
      capitalizedWords.add(cs.capitalize());
    }

    String joined = String.join(" ", capitalizedWords);
    return joined;
  }

  // count occurrences of substring in str
  public int countOccurences(String substring) {
    int count = this.length() - this.replace(substring, "").length();
    return count;
  }


  @Override
  public boolean equalsIgnoreAccents(String that) {
    String stripped = CharString.of(that).stripAccents();
    boolean equal = value.equals(stripped);
    return equal;
  }



  @Override
  public String padLeft(int totalLength) {
    String padded = String.format("%1$" + totalLength + "s", this);
    return padded;
  }

  @Override
  public String padRight(int totalLength) {
    String padded = String.format("%1$-" + totalLength + "s", this);
    return padded;
  }

  @Override
  public String pad(int width) {
    StringBuilder sb = new StringBuilder();
    int n = Math.max(0, width - this.length());

    for (int i = 0; i < n; i++) {
      sb.append(" ");
    }

    sb.append(this);
    return sb.toString();
  }


  @Override
  public String stripAccents() {
    String stripped = Normalizer.normalize(this, Normalizer.Form.NFD);
    stripped = stripped.replaceAll("[^\\p{ASCII}]", "");
    return stripped;
  }

  @Override
  public String stripBlanks() {
    // remove whitespaces and tabs
    String stripped = this.replaceAll("\\s", "");
    return stripped;
  }

  @Override
  public String stripNonDigit() {
    StringBuilder sb = new StringBuilder();
    int nb = this.length();

    for (int i = 0; i < nb; i++) {
      char ch = this.charAt(i);
      if (Character.isDigit(ch)) {
        sb.append(ch);
      }
    }

    return sb.toString();
  }

  @Override
  public String toDisplayString() {
    String displayed = this.replaceAll("_", " ");
    displayed = displayed.toLowerCase();
    displayed = CharString.of(displayed).capitalizeWords();
    return displayed;
  }


  @Override
  public String trimDoubleBlanks() {
    String trimmed = this.replace(" +", " ");
    return trimmed;
  }

  @Override
  public String truncate(int lenght) {
    return truncate(lenght, ELLIPSIS);
  }

  @Override
  public String truncate(int lenght, String suffix) {
    boolean trunk = this.length() > lenght;
    String truncated = trunk ? this.substring(0, lenght - 1) + suffix : value;
    return truncated;
  }

  @Override
  public String unquote(char quoteCharacter) {
    String target = "";

    if (!isNullOrBlank(value)) {
      char ch = this.charAt(0);
      target = (ch == quoteCharacter) ? this.substring(1) : value;
      int len = target.length() - 1;
      ch = target.charAt(len);
      target = (ch == quoteCharacter) ? target.substring(0, len) : target;
    }

    return target;
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

  //TODO
public boolean isDate() {
	return false;
}

//TODO
public boolean isNumber() {
	return false;
}

//TODO
public boolean isInteger() {
	return false;
}

//TODO
public boolean isBoolean() {
	return false;
}

public String wordWrap(int lineLength, String delimitor, String separator) {
	// TODO Auto-generated method stub
	return this.toString();
}



}
