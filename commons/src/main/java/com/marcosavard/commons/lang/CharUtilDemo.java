package com.marcosavard.commons.lang;

public class CharUtilDemo {

    public static void main(String[] args) {
        char c = 'à';

        System.out.println("'à' is ascii : " + CharUtil.isAscii(c));
        System.out.println("'à' is diacritical : " + CharUtil.isDiacritical(c));
        System.out.println("'à' is voyel : " + CharUtil.isVowel(c));
    }
}
