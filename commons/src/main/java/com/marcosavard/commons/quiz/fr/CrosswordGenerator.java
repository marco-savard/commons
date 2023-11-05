package com.marcosavard.commons.quiz.fr;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CrosswordGenerator {
  private static final char EMPTY = '.';

  private static final char BLOCK = '#';

  public static void main(String[] args) {
    int rows = 8; // Number of rows for the crossword
    int cols = 10; // Number of columns for the crossword
    List<String> words =
        Arrays.asList(
            "espagne", "irlande", "italie", "russie", "canada", "france", "grece", "euro", "irak",
            "usa", "ene", "sos", "ono", "ese", "no", "so", "ag");

    // Sort the words in descending order of length
    words.sort(Comparator.comparingInt(String::length).reversed());

    Crossword crossword = Crossword.of(rows, cols);
    crossword.fill(words);
    crossword.print();
  }
}
