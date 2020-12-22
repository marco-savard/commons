package com.marcosavard.commons.ling;

import java.util.Map;
import com.marcosavard.commons.io.ContentReader;
import com.marcosavard.commons.text.TextCounter;
import com.marcosavard.commons.text.TextCounterDemo;
import com.marcosavard.commons.text.TextCounter.Characters;
import com.marcosavard.commons.text.TextCounter.TextCounterResult;

public class LanguageGuesserDemo {

  public static void main(String[] args) {
    // read sample text
    String filename = "christmasCarol.txt";
    System.out.println("reading " + filename + "..");
    ContentReader reader = new ContentReader(TextCounterDemo.class, filename);
    String content = reader.readContent().toLowerCase();

    // count characters
    TextCounter counter = new TextCounter();
    TextCounter.Characters characters = TextCounter.Characters.ONLY_LETTERS;
    TextCounter.TextCounterResult<Character> result =
        counter.countCharacters(content, 26, characters);

    // guess languages
    LanguageGuesser languageGuesser = new LanguageGuesser();
    Map<String, Double> candidateLanguages =
        languageGuesser.getCandidateLanguages(result.getValuesByPercent());

    for (String language : candidateLanguages.keySet()) {
      double value = candidateLanguages.get(language);
      System.out.println("Language " + language + " has divergence : " + value);
    }


  }

}
