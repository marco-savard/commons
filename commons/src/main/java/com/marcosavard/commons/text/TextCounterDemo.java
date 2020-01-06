package com.marcosavard.commons.text;

import java.util.Map;
import com.marcosavard.commons.io.ContentReader;

public class TextCounterDemo {

  public static void main(String[] args) {
    // reading input
    String filename = "christmasCarol.txt";
    System.out.println("reading " + filename + "..");
    ContentReader reader = new ContentReader(TextCounterDemo.class, filename);
    String content = reader.readContent().toLowerCase();

    // print 10 most common words
    System.out.println("displaying stats..");
    System.out.println();
    TextCounter counter = new TextCounter();
    MapPrinter<String> stringMapPrinter = new MapPrinter<>();
    TextCounter.TextCounterResult<String> result = counter.countWords(content, 10);
    Map<String, Integer> wordsByFrequency = result.getValuesByOccurrences();
    stringMapPrinter.printOccurrences(wordsByFrequency, "Rank", "Word", "Frequency");

    // print 15 most common letters
    MapPrinter<Character> charMapPrinter = new MapPrinter<>();
    TextCounter.Characters characters = TextCounter.Characters.ONLY_LETTERS;
    TextCounter.TextCounterResult<Character> result2 =
        counter.countCharacters(content, 15, characters);
    charMapPrinter.printPercents(result2.getValuesByPercent(), "Rank", "Letter", "Frequency");
  }

}
