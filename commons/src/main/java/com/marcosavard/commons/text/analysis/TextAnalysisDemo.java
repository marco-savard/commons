package com.marcosavard.commons.text.analysis;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.text.locale.Glossary;

import java.util.List;
import java.util.Locale;

public class TextAnalysisDemo {

    public static void main(String[] args) {
        Locale fr = Locale.FRENCH;
        List<String> words = Glossary.of(fr).getWords(Glossary.Category.NATIONAL_LANGUAGE);
        TextAnalysis analysis = TextAnalysis.of(fr, words);

        Console.println("Text analysis of {0} words", fr.getDisplayLanguage());
        Console.println("  frequentLetters : " + analysis.findFrequentLetters());
        Console.println("  diacriticals : " + analysis.findDiacriticals());
        Console.println("  word length : " + analysis.findWordLength());
        Console.println("  vowel percentage : " + analysis.findVowelPercentage());
    }
}
