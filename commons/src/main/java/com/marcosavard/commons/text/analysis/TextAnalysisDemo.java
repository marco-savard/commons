package com.marcosavard.commons.text.analysis;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.CharUtil;
import com.marcosavard.commons.text.locale.Glossary;

import java.io.CharConversionException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextAnalysisDemo {

    public static void main(String[] args) {
        Locale fr = Locale.FRENCH;
        Glossary.Category[] categories = new Glossary.Category[] {
                Glossary.Category.EUROPEAN_COUNTRY,
                Glossary.Category.EUROPEAN_LANGUAGE,
                Glossary.Category.MONTH
        };
        List<String> words = Glossary.of(fr).getWords(categories);
        TextAnalysis analysis = TextAnalysis.of(fr, words);

        Console.println("Text analysis of {0} words", fr.getDisplayLanguage());
        Console.println("  frequentLetters : " + analysis.findFrequentLetters());
        Console.println("  diacriticals : " + toString(analysis.findDiacriticals()));
        Console.println("  word length : " + analysis.findWordLength());
        Console.println("  vowel percentage : " + analysis.findVowelPercentage());
    }

    private static String toString(List<Character> chars) {
        List<String> strings = new ArrayList<>();

        for (Character ch : chars) {
            int code = ch;
            char ascii = CharUtil.stripAccent(ch);
            String diacriticalMark = CharUtil.getDiacriticalMark(ch);
            String s = MessageFormat.format("{0} ({1}{2} {3})", ch, ascii, diacriticalMark, code);
            strings.add(s);
        }

        return String.join(", ", strings);
    }
}
