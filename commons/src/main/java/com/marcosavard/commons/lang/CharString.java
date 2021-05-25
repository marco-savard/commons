package com.marcosavard.commons.lang;

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
	String capitalized = StringUtil.capitalize(this.toString()); 
    return capitalized;
  }

  @Override
  public String capitalizeWords() {
	String capitalized = StringUtil.capitalizeWords(this.toString());
    return capitalized;
  }

  // count occurrences of substring in str
  public int countMatches(String substring) {
	int count = StringUtil.countMatches(this, substring); 
    return count;
  }

  @Override
  public boolean equalsIgnoreAccents(String other) {
    boolean equal = StringUtil.equalsIgnoreAccents(this.toString(), other);  
    return equal;
  }

  @Override
  public String padLeft(int totalLength) {
    String padded = StringUtil.padLeft(this, totalLength); 
    return padded;
  }

  @Override
  public String padRight(int totalLength) {
	String padded = StringUtil.padRight(this, totalLength);  
    return padded;
  }

  @Override
  public String stripAccents() {
    String stripped = StringUtil.stripAccents(this);
    return stripped;
  }

  @Override
  public String stripBlanks() {
	String stripped = StringUtil.stripBlanks(this);
    return stripped;
  }

  @Override
  public String stripNonDigit() {
	String stripped = StringUtil.stripNonDigit(this);
	return stripped;
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
    String trimmed = StringUtil.trimDoubleBlanks(this);
    return trimmed;
  }

  @Override
  public String unquote(char quoteCharacter) {
	String unquoted = StringUtil.unquote(this, quoteCharacter).toString(); 
    return unquoted;
  }

  /**
   * Tells if text is null or blank
   * 
   * @param text a given string
   * @return true if null or blank
   */
  public static boolean isNullOrBlank(String text) {
    boolean nullOrBlank = StringUtil.isNullOrBlank(text);   
    return nullOrBlank;
  }

  /**
   * Tells if text is null or empty
   * 
   * @param text a given string
   * @return true if null or empty
   */
  public static boolean isNullOrEmpty(String text) {
    boolean nullOrEmpty = StringUtil.isNullOrEmpty(text);   
    return nullOrEmpty;
  }


public String wordWrap(int lineLength, String delimitor, String separator) {
	// TODO Auto-generated method stub
	return this.toString();
}

public boolean isBoolean() {
	return StringUtil.isBoolean(this);
}

public boolean isDate() {
	return StringUtil.isDate(this);
}

public boolean isInteger() {
	return StringUtil.isInteger(this); 
}

public boolean isNumber() {
	return StringUtil.isNumber(this);
}






}
