package com.marcosavard.commons.lang;

import java.util.Arrays;
import java.util.List;

/**
 * A class that provides useful methods that handle strings. It includes methods to predicate
 * whether a string is null or empty, to pad strings with blanks, to remove quotes from strings, to
 * strip accents from a string.
 *
 * @author Marco
 */
public class CharString implements CharSequence, StringInterface, CharStringInterface {
    private static final CharString EMPTY = new CharString("");

    private final String value; // nullSafe value

    private CharString(String value) {
        this.value = (value == null ? "" : value);
    }

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
        String s = new String(chars);
        return new CharString(s);
    }

    /**
     * Tells if text is null or blank
     *
     * @param text a given string
     * @return true if null or blank
     */
    public static boolean isNullOrBlank(String text) {
        return StringUtil.isNullOrBlank(text);
    }

    /**
     * Tells if text is null or empty
     *
     * @param text a given string
     * @return true if null or empty
     */
    public static boolean isNullOrEmpty(String text) {
        return StringUtil.isNullOrEmpty(text);
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
            CharSequence thatSequence = (CharSequence)this;
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

    public boolean in(String... strings) {
        List<String> stringList = Arrays.asList(strings);
        boolean contained = stringList.contains(value);
        return contained;
    }

    public static String join(String prefix, List<String> list, String delimiter, String suffix) {
        String delimited = String.join(delimiter, list);
        String joined = list.isEmpty() ? delimited : prefix + delimited + suffix;
        return joined;
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

    public boolean like(String wildcard) {
        String regex = toRegex(wildcard);
        boolean matched = value.matches(regex);
        return matched;
    }

    @Override
    public String replace(CharSequence original, CharSequence replacement) {
        return value.replace(original, replacement);
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

    private static String toRegex(String wildcard) {
        String regex = wildcard.replaceAll("\\*", "(.*)");
        regex = regex.replaceAll("\\?", "(.)");
        return regex;
    }

    @Override
    public String trim() {
        return value.trim();
    }

    @Override
    public String capitalize() {
        return StringUtil.capitalize(this.toString());
    }

    @Override
    public String capitalizeWords() {
        return StringUtil.capitalizeWords(this.toString());
    }

    // count occurrences of substring in str
    public int countMatches(String substring) {
        return StringUtil.countMatches(this, substring);
    }

    @Override
    public boolean equalsIgnoreAccents(String other) {
        return StringUtil.equalsIgnoreAccents(this.toString(), other);
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


    @Override
    public String padLeft(int totalLength) {
        return StringUtil.padLeft(this, totalLength);
    }

    @Override
    public String padRight(int totalLength) {
        return StringUtil.padRight(this, totalLength);
    }

    @Override
    public String stripAccents() {
        return StringUtil.stripAccents(this);
    }

    @Override
    public String stripBlanks() {
        return StringUtil.stripBlanks(this);
    }

    @Override
    public String stripNonDigit() {
        return StringUtil.stripNonDigit(this);
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
        return StringUtil.trimDoubleBlanks(this);
    }

    @Override
    public String unquote(char quoteCharacter) {
        return StringUtil.unquote(this, quoteCharacter).toString();
    }

    public String wordWrap(int lineLength, String delimitor, String separator) {
        return StringUtil.wordWrap(this, lineLength, delimitor, separator);
    }

}
