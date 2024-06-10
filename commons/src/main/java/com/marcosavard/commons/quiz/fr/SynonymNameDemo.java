package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.List;

public class SynonymNameDemo {

  public static void main(String[] args) {
    // demoAllWords();
    // demoAdjectives();
    demoCountableNouns();
  }

  private static void demoCountableNouns() {
    List<String> singulars = SynonymName.getCountableNouns();
    List<String> plurals = SynonymName.toPlurals(singulars);

    List<String> words = new ArrayList<>();
    words.addAll(singulars);
    words.addAll(plurals);

    print(words);
  }

  private static void demoAdjectives() {
    List<String> masculines = SynonymName.getAdjectives();
    List<String> feminines = SynonymName.toFeminines(masculines);
    List<String> masculinePlurals = SynonymName.toPlurals(masculines);
    List<String> femininePlurals = SynonymName.toPlurals(feminines);

    List<String> words = new ArrayList<>();
    words.addAll(masculines);
    words.addAll(masculinePlurals);
    words.addAll(feminines);
    words.addAll(femininePlurals);

    print(words);
  }

  private static void demoOtherWords() {
    List<String> words = new ArrayList<>();
    words.addAll(SynonymName.getAdverbs());
    words.addAll(SynonymName.getNonCountableNouns());
    print(words);
  }

  private static void print(List<String> words) {
    int count = words.size();

    for (int i = 0; i < count; i++) {
      List<String> synonyms = List.of(words.get(i).split(";"));
      List<String[]> pairs = SynonymName.getPairs(synonyms);

      for (int j = 0; j < pairs.size(); j++) {
        Console.println(pairs.get(j));
      }
    }
  }
}
