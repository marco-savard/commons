package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.text.MessageFormat;
import java.util.List;

public class DictionaryDemo {
  private static final String[] WORDS = new String[] {"arbre", "arbol", "gomme"};

  public static void main(String[] args) {
    try {
      // read the dictionary
      DictionaryReader dr = new DictionaryReader("fr.dic", "Cp1250");
      Dictionary dictionary = dr.readAll();

      displaySummary(dictionary);
      findWords(dictionary, WORDS);
      findByPattern(dictionary, "*queux");
      findSuggestions(dictionary, "achetter");
      findAnagrams(dictionary);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static void findAnagrams(Dictionary dictionary) {
    dictionary.length();
  }

  private static void displaySummary(Dictionary dictionary) {
    // how many words in dictionary
    int len = dictionary.length();
    Console.println(MessageFormat.format("Dictionary contains {0} words", len));
  }

  private static void findWords(Dictionary dictionary, String[] words) {
    // is word in dictionary?
    for (String word : words) {
      boolean contained = dictionary.contains(word);
      String msg = MessageFormat.format("  {0} in dictionary : {1}", word, contained);
      Console.println(msg);
    }
  }

  private static void findByPattern(Dictionary dictionary, String pattern) {
    // words by patterns
    List<String> words = dictionary.findWordsByWildcards(pattern);

    for (String word : words) {
      Console.println(word);
    }
    Console.println();
  }

  private static void findSuggestions(Dictionary dictionary, String mispelled) {
    // find suggestions
    List<String> suggestions = dictionary.findSuggestions(mispelled);
    for (String suggestion : suggestions) {
      Console.println(suggestion);
    }
  }
}
