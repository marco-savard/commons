package com.marcosavard.commons.lang;

import com.marcosavard.commons.lang.soundex.Soundex;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * A utility class that provides useful methods that handle strings. It includes methods to
 * predicate whether a string is null or empty, to pad strings with blanks, to remove quotes from
 * strings, to strip accents from a string.
 *
 * @author Marco
 */
public class StringUtil {
  public static final String DIACRITICALS = "áàâäéèêëíìïîóòôöúùûüÿçñ";
  public static final String ASCII = "aaaaeeeeiiiiooooooooycn";

  public static final String ELLIPSIS = "\u2026"; // ...character
  public static final String TWO_DOTS = ".."; // ..characters

  public enum Alignment {
    LEFT,
    RIGHT
  };

  private static final Character[] BOOLEANS = new Character[] {'0', '1', 't', 'f', 'y', 'n'};

  // Utility class is not instantiable
  private StringUtil() {}

  /**
   * Abbreviate source at a given length
   *
   * @param length to be truncate
   * @return a string having at most 'lenght' characters
   */
  public static String abbreviate(CharSequence original, int length) {
    return abbreviate(original, length, TWO_DOTS);
  }

  public static String abbreviate(CharSequence original, int maxLength, String suffix) {
    boolean tooLong = original.length() > maxLength;
    String truncated = truncate(original, maxLength);
    return tooLong ? original.subSequence(0, maxLength - suffix.length()) + suffix : truncated;
  }

  public static String camelToUnderscore(String camel) {
    String underscore = String.valueOf(Character.toLowerCase(camel.charAt(0)));

    for (int i = 1; i < camel.length(); i++) {
      char ch = camel.charAt(i);
      boolean lowercase = Character.isLowerCase(ch);
      underscore +=
              lowercase ? String.valueOf(ch) : "_" + String.valueOf(Character.toLowerCase(ch));
    }

    return underscore.toUpperCase();
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

  public static String capitalize(CharSequence str, Locale locale) {
    str = NullSafe.of(str);
    int len = str.length();
    String firstLetter = (len == 0) ? "" : toUpperCase(str.subSequence(0, 1), locale);
    String remaining = (len <= 1) ? "" : subSequence(str, 1).toString();
    return firstLetter + remaining;
  }

  public static String capitalizeWords(CharSequence original) {
    return capitalizeWords(original, Locale.getDefault());
  }

  /**
   * Capitalize each words in sentence
   *
   * @param original
   * @return capitalizedWords
   */
  public static String capitalizeWords(CharSequence original, Locale locale) {
    original = NullSafe.of(original);
    List<String> words = Arrays.asList(original.toString().split(" "));
    List<String> capitalizedWords = new ArrayList<>();

    for (String word : words) {
      capitalizedWords.add(capitalize(word, locale));
    }

    return String.join(" ", capitalizedWords);
  }

  public static String center(CharSequence original, int length) {
    original = NullSafe.of(original);
    int margin = length - original.length();
    int leftLength = original.length() + (margin / 2);
    return (margin > 0) ? padRight(padLeft(original, leftLength), length) : original.toString();
  }

  public static int compare(CharSequence original, CharSequence other) {
    boolean oneNull = (original == null) || (other == null);
    boolean bothNull = (original == null) && (other == null);
    return oneNull ? (bothNull ? 0 : -1) : original.toString().compareTo(other.toString());
  }

  public static int compareToIgnoreAccent(CharSequence original, CharSequence other) {
    return stripAccents(original).compareTo(stripAccents(other));
  }

  public static int compareToIgnoreCase(CharSequence original, CharSequence other) {
    return NullSafe.of(original).toString().compareToIgnoreCase(NullSafe.of(original).toString());
  }

  public static int compareToIgnoreCase(CharSequence original, CharSequence other, Locale locale) {
    original = NullSafe.of(original);
    other = NullSafe.of(other);
    return toLowerCase(original, locale).compareToIgnoreCase(toLowerCase(other, locale));
  }

  public static int compareToIgnoreCaseAccent(CharSequence original, CharSequence other) {
    return compareToIgnoreCaseAccent(original, other, Locale.getDefault());
  }

  public static int compareToIgnoreCaseAccent(
      CharSequence original, CharSequence other, Locale locale) {
    return stripAccents(toLowerCase(original, locale))
        .compareToIgnoreCase(stripAccents(toLowerCase(other, locale)));
  }

  public static long countCharacters(String str, char given) {
    long count = str.chars().filter(ch -> ch == given).count();
    return count;
  }

  // count occurrences of substring in str
  public static int countMatches(CharSequence original, CharSequence substring) {
    original = NullSafe.of(original);
    return original.length() - original.toString().replace(substring, "").length();
  }

  // count occurrences of substring in str
  public static int countOccurences(String str, String substring) {
    return str.length() - str.replace(substring, "").length();
  }

  /**
   * Returns the same string, but empty is null.
   *
   * @param text to check
   * @return text or "" is text is empty
   */
  public static String emptyIfNull(String text) {
    return (text == null) ? "" : text;
  }

  public static boolean endsWith(CharSequence original, CharSequence suffix) {
    return original.toString().endsWith(suffix.toString());
  }

  public static boolean endsWithIgnoreAccent(CharSequence original, CharSequence suffix) {
    return stripAccents(original).endsWith(stripAccents(suffix));
  }

  public static boolean endsWithIgnoreCase(CharSequence original, CharSequence suffix) {
    return toLowerCase(original).endsWith(toLowerCase(suffix));
  }

  public static boolean endsWithIgnoreCaseAndAccent(CharSequence original, CharSequence suffix) {
    return stripAccents(original).toLowerCase().endsWith(stripAccents(suffix).toLowerCase());
  }

  public static boolean equalsIgnoreAccents(CharSequence original, CharSequence other) {
    boolean oneNull = (original == null) || (other == null);
    boolean bothNull = (original == null) && (other == null);
    String stripped = stripAccents(original);
    return oneNull ? bothNull : stripped.equals(stripAccents(other));
  }

  public static boolean equalsIgnoreCaseAndAccents(CharSequence original, CharSequence other) {
    return equalsIgnoreCaseAndAccents(original, other, Locale.getDefault());
  }

  public static boolean equalsIgnoreCaseAndAccents(
      CharSequence original, CharSequence other, Locale locale) {
    boolean oneNull = (original == null) || (other == null);
    boolean bothNull = (original == null) && (other == null);
    String stripped = stripAccents(original).toLowerCase(locale);
    return oneNull ? bothNull : stripped.equals(stripAccents(other).toLowerCase(locale));
  }

  public static int indexOf(String original, String... strings) {
    int smallerIdx = Integer.MAX_VALUE;

    for (String string : strings) {
      int idx = original.indexOf(string);
      smallerIdx = (idx == -1) ? smallerIdx : Math.min(smallerIdx, idx);
    }

    smallerIdx = (smallerIdx == Integer.MAX_VALUE) ? -1 : smallerIdx;
    return smallerIdx;
  }

  public static int indexOfAny(CharSequence original, char... chars) {
    int smallestIdx = Integer.MAX_VALUE;

    for (char ch : chars) {
      int idx = NullSafe.of(original).toString().indexOf(ch);
      smallestIdx = (idx >= 0) && (idx < smallestIdx) ? idx : smallestIdx;
    }

    return (smallestIdx == Integer.MAX_VALUE) ? -1 : smallestIdx;
  }

  public static int indexOfIgnoreAccent(CharSequence original, String ch) {
    return stripAccents(original).indexOf(ch);
  }

  public static boolean isBoolean(CharString original) {
    boolean isBoolean = false;

    if ((original != null) && (!isEmpty(original))) {
      char first = Character.toLowerCase(original.charAt(0));
      List<Character> booleans = Arrays.asList(BOOLEANS);
      isBoolean = booleans.contains(first);
    }

    return isBoolean;
  }

  private static boolean isEmpty(CharSequence original) {
    return (original.length() == 0);
  }

  // returns true for "20201231", "2020/12/31", "2020-12-31"
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

  // Same as org.apache.commons.lang3.StringUtils.isNotEmpty()
  public static boolean isNotEmpty(String str) {
    return (str != null);
  }

  /**
   * Tells if text is null or blank
   *
   * @param text a given string
   * @return true if null or blank
   */
  public static boolean isNullOrBlank(String text) {
    return (text == null) || text.trim().isEmpty();
  }

  /**
   * Tells if text is null or empty
   *
   * @param text a given string
   * @return true if null or empty
   */
  public static boolean isNullOrEmpty(String text) {
    return (text == null) || text.isEmpty();
  }
  /**
   * Tells if text is null or blank
   *
   * @param original a given string
   * @return true if null or blank
   */
  public static boolean isNullOrBlank(CharSequence original) {
    return (original == null) || original.toString().trim().isEmpty();
  }

  /**
   * Tells if text is null or empty
   *
   * @param original a given string
   * @return true if null or empty
   */
  public static boolean isNullOrEmpty(CharSequence original) {
    return (original == null) || isEmpty(original);
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
      char last = original.charAt(len - 1);
      quoted = (first == quoteCharacter) && (last == quoteCharacter);
    }

    return quoted;
  }

  public static int lastIndexOfAny(CharSequence original, char... chars) {
    int largestIdx = 0;

    for (char ch : chars) {
      int idx = NullSafe.of(original).toString().lastIndexOf(ch);
      largestIdx = (idx >= 0) && (idx > largestIdx) ? idx : largestIdx;
    }

    return largestIdx;
  }

  public static String pad(CharSequence original, int totalLength, Alignment alignment) {
    if (alignment == Alignment.RIGHT) {
      return padRight(original, totalLength);
    } else {
      return padLeft(original, totalLength);
    }
  }

  /**
   * Pad n blanks at left
   *
   * @param original a given string
   * @param totalLength of blanks
   * @return padded string
   */
  public static String padLeft(CharSequence original, int totalLength) {
    return String.format("%1$-" + totalLength + "s", original);
  }

  public static String padRight(CharSequence original, int totalLength) {
    return String.format("%1$" + totalLength + "s", original);
  }

  public static String quote(CharSequence original) {
    return "\"" + NullSafe.of(original) + "\"";
  }

  public static String repeat(char ch, int len) {
    char[] chars = new char[len];
    Arrays.fill(chars, ch);
    return String.valueOf(chars);
  }

  public static String soundex(String str) {
    return Soundex.of(str);
  }

  public static String space(int n) {
    return repeat(' ', n);
  }

  public static String[] splitLine(CharSequence original) {
    original = NullSafe.of(original);
    return original.toString().split("\\r?\\n");
  }

  public static boolean startsWith(CharSequence original, CharSequence prefix) {
    original = NullSafe.of(original);
    prefix = NullSafe.of(prefix);
    return original.toString().startsWith(prefix.toString());
  }

  public static boolean startsWithIgnoreAccent(CharSequence original, CharSequence suffix) {
    return stripAccents(original).startsWith(stripAccents(suffix));
  }

  public static boolean startsWithIgnoreCase(CharSequence original, CharSequence prefix) {
    return toLowerCase(original).startsWith(toLowerCase(prefix));
  }

  /**
   * Strip off accents from characters
   *
   * @param original with accents
   * @return stripped text
   */
  public static String stripAccents(CharSequence original) {
    return translate(original, DIACRITICALS, ASCII);
  }

  public static String stripAccentsOld(CharSequence original) {
    String stripped = Normalizer.normalize(NullSafe.of(original), Normalizer.Form.NFD);
    return stripped.replaceAll("[^\\p{ASCII}]", "");
  }

  public static List<String> stripAccents(List<String> original) {
    List<String> list = new ArrayList<>();

    for (String item : original) {
      list.add(stripAccents(item));
    }
    return list;
  }

  /**
   * Return a string in which all the blanks (whitespaces and tabs) of the text parameter are
   * stripped off. For instance, stripBlanks("hello world") returns "helloworld".
   *
   * @param original the original String, that may contain characters with blanks.
   * @return the same String, but without blanks
   */
  public static String stripBlanks(CharSequence original) {
    // remove whitespaces and tabs
    original = NullSafe.of(original);
    return original.toString().replaceAll("\\s", "");
  }

  /**
   * Strip off non digit characters
   *
   * @param original a given string
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
    return original.subSequence(beginIdx, original.length());
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
    return capitalizeWords(displayed);
  }

  public static String toLowerCase(CharSequence original) {
    return toLowerCase(original, Locale.getDefault());
  }

  public static String toLowerCase(CharSequence original, Locale locale) {
    original = NullSafe.of(original);
    return original.toString().toLowerCase(locale);
  }

  public static String toUpperCase(CharSequence original) {
    return toUpperCase(original, Locale.getDefault());
  }

  public static String toUpperCase(CharSequence original, Locale locale) {
    original = NullSafe.of(original);
    return original.toString().toUpperCase(locale);
  }

  public static String translate(CharSequence str, CharSequence old, CharSequence target) {
    int n = Math.min(old.length(), target.length());
    char[] array = str.toString().toCharArray();

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < array.length; j++) {
        if (array[j] == old.charAt(i)) {
          array[j] = target.charAt(i);
        }
      }
    }

    return String.valueOf(array);
  }


  public static String trimDoubleBlanks(CharSequence original) {
    original = NullSafe.of(original);
    return original.toString().replace(" +", " ");
  }

  public static CharSequence trimLeft(CharSequence original) {
     return trimLeft(original, ' ');
  }

  public static CharSequence trimLeft(CharSequence original, char charToTrim) {
    int i = 0;
    while (i < original.length() && (original.charAt(i)) == charToTrim) {
      i++;
    }
    return original.subSequence(i, original.length());
  }

  public static CharSequence trimRight(CharSequence original) {
    return trimRight(original, ' ');
  }

  public static CharSequence trimRight(CharSequence original, char charToTrim) {
    int i = original.length()-1;
    while (i >= 0 && (original.charAt(i) == charToTrim)) {
      i--;
    }
    return original.subSequence(0, i+1);
  }


  /**
   * Truncate source at a given length
   *
   * @param original a given string
   * @param maxLenght to be truncate
   * @return a string having at most 'lenght' characters
   */
  public static String truncate(CharSequence original, int maxLenght) {
    original = NullSafe.of(original);
    boolean tooLong = original.length() > maxLenght;
    return tooLong ? original.subSequence(0, maxLenght).toString() : original.toString();
  }

  public static String uncapitalize(CharSequence str) {
    str = NullSafe.of(str);
    int len = str.length();
    String firstLetter = (len == 0) ? "" : Character.toString(Character.toLowerCase(str.charAt(0)));
    String remaining = (len <= 1) ? "" : str.subSequence(1, str.length()).toString();
    return firstLetter + remaining;
  }

  public static String underscoreToCamel(String underscore) {
    String camel = underscore.toLowerCase();
    camel = camel.replace('_', ' ');
    camel = capitalizeWords(camel);
    camel = camel.replace(" ", "");
    return uncapitalize(camel);
  }


  public static CharSequence unquote(CharSequence original) {
    return unquote(original, '\"');
  }

  /**
   * Returns the same String, but without the quotes. For instance,
   * removeSurroundingQuotes("'text'", '\'') returns "text".
   *
   * @param original whose the first and last characters may be quote characters.
   * @param quoteCharacter such as ' or "
   * @return the text without the quotes, if any
   */
  public static CharSequence unquote(CharSequence original, char quoteCharacter) {
    CharSequence target = "";

    if (isQuoted(original, quoteCharacter)) {
      int start = 1;
      int end = original.length() - 1;
      target = original.subSequence(start, end);
    }

    return target;
  }

  public static String wordWrap(
      CharSequence original, int lineLength, String delimitor, String separator) {
    return original.toString();
  }
}
