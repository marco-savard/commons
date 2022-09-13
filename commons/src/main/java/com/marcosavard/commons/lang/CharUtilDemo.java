package com.marcosavard.commons.lang;

import com.marcosavard.commons.debug.Console;

public class CharUtilDemo {

    public static void main(String[] args) {
        char c = 'à';

        Console.println("{0} is ascii : {1}", c, CharUtil.isAscii(c));
        Console.println("{0} is diacritical : {1}", c, CharUtil.isDiacritical(c));
        Console.println("{0} is vowel : {1}", c, CharUtil.isVowel(c));
    }
}
