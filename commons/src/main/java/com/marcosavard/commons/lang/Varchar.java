package com.marcosavard.commons.lang;

import com.marcosavard.commons.lang.soundex.Soundex;

import java.util.Arrays;

public class Varchar implements CharSequence {
    private String str;

    public static Varchar of(String str) {
        return new Varchar(str);
    }

    public static Varchar repeat(char ch, int n) {
        return Varchar.of(StringUtil.repeat(ch, n));
    }

    public static Varchar space(int n) {
        return Varchar.of(StringUtil.space(n));
    }

    private Varchar(String str) {
        this.str = str;
    }


    @Override
    public int length() {
        return str.length();
    }

    @Override
    public char charAt(int index) {
        return str.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return str.subSequence(start, end);
    }

    @Override
    public String toString() {
        return str;
    }

    public String left(int len) {
        int endIndex = Math.min(len, str.length());
        return str.substring(0, endIndex);
    }

    public boolean like(String wildcard) {
        return str.matches(toRegex(wildcard));
    }

    public String right(int len) {
        int startIndex = Math.max(0, str.length() - len);
        return str.substring(startIndex);
    }

    public String soundex() {
        return StringUtil.soundex(str);
    }

    public Varchar translate(CharSequence src, CharSequence target) {
        return Varchar.of(StringUtil.translate(str, src, target));
    }

    public String trim() {
        return str.trim();
    }

    public CharSequence trimLeft() {
        return trimLeft(' ');
    }

    public CharSequence trimLeft(char charToTrim) {
        return StringUtil.trimLeft(str, charToTrim);
    }

    public CharSequence trimRight() {
        return trimRight(' ');
    }

    public CharSequence trimRight(char charToTrim) {
        return StringUtil.trimRight(str, charToTrim);
    }

    //
    // private methods
    //
    private static String toRegex(String wildcard) {
        String regex = wildcard.replaceAll("\\?", ".");
        regex = regex.replaceAll("\\*", ".+");
        return regex;
    }



}
