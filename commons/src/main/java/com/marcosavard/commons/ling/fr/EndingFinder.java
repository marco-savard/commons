package com.marcosavard.commons.ling.fr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.marcosavard.commons.ling.processing.Noun;

public class EndingFinder {

  public Map<String, List<String>> findEndings(Map<String, Noun> nouns) {
    Map<String, List<String>> endings = new TreeMap<>();

    for (String word : nouns.keySet()) {
      Noun noun = nouns.get(word);

      if (noun.isSingular()) {
        int idx = word.length() - 1;
        String ending = word.substring(idx);
        List<String> words = endings.get(ending);

        if (words == null) {
          words = new ArrayList<>();
          endings.put(ending, words);
        }

        words.add(word);
      }
    }

    return endings;
  }

  public List<String> getWordsEndingIn(Map<String, Noun> nouns, String suffix) {
    List<String> words = new ArrayList<>();

    for (String word : nouns.keySet()) {
      if (word.endsWith(suffix)) {
        words.add(word);
      }
    }

    return words;
  }

  public List<String> getWordsWithPrefix(Map<String, Noun> nouns, String prefix) {
    List<String> words = new ArrayList<>();

    for (String word : nouns.keySet()) {
      if (word.startsWith(prefix)) {
        words.add(word);
      }
    }

    return words;
  }

}
