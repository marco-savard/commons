package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.util.PseudoRandom;

import java.util.Locale;
import java.util.Random;

public class CrosswordGenerator {
  private static final char EMPTY = '.';

  private static final char BLOCK = '#';

  public static void main(String[] args) {
    int rows = 12; // Number of rows for the crossword
    int cols = 24; // Number of columns for the crossword
    Random random = new PseudoRandom(1);

    QuestionList questionList = new QuestionList();
    Locale display = Locale.FRENCH;
    int level = 1, seed = 2; // facile
    questionList.generateQuestions(display, random);
    questionList.shuffle(random);
    Crossword crossword = Crossword.of(rows, cols);
    crossword.fill(questionList.getQuestions());
    crossword.print();
  }
}
