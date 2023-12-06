package com.marcosavard.commons.quiz.fr;

import java.util.Locale;

public class CrosswordGenerator {
  private static final char EMPTY = '.';

  private static final char BLOCK = '#';

  public static void main(String[] args) {
    int rows = 8; // Number of rows for the crossword
    int cols = 32; // Number of columns for the crossword

    QuestionList questionList = new QuestionList();
    Locale display = Locale.FRENCH;
    int level = 1; // facile
    questionList.generateQuestions(display, 4);

    Crossword crossword = Crossword.of(rows, cols);
    crossword.fill(questionList.getQuestions());
    crossword.print();
  }
}
