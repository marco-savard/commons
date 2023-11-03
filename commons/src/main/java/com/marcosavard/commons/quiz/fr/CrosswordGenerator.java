package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.debug.Console;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CrosswordGenerator {
  private static final char EMPTY = '.';

  private static final char BLOCK = '#';

  public static void main(String[] args) {
    int rows = 6; // Number of rows for the crossword
    int cols = 6; // Number of columns for the crossword
    List<String> words =
        Arrays.asList("yellow", "orange", "green", "blue", "red", "aga" /*, , "dad" */);

    char[][] crossword = generateCrossword(rows, cols, words);
    printCrossword(crossword);
  }

  public static char[][] generateCrossword(int rows, int cols, List<String> words) {
    char[][] crossword = new char[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        crossword[i][j] = EMPTY; // Initialize all cells with empty spaces
      }
    }

    // Sort the words in descending order of length
    words.sort(Comparator.comparingInt(String::length).reversed());
    boolean horizontal = true;

    // Place the words
    for (String word : words) {
      boolean placed = false;

      if (horizontal) {
        placed = placeHorizontal(crossword, rows, cols, word);
      } else {
        placed = placeVertical(crossword, rows, cols, word);
      }

      if (placed) {
        horizontal = !horizontal;
      }
    }

    return crossword;
  }

  private static boolean placeHorizontal(char[][] crossword, int rows, int cols, String word) {
    boolean placed = false;
    int dx = 0, dy = 1;
    int wordLength = word.length();
    int maxScore = -1;
    int maxI = 0, maxJ = 0;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j <= cols - wordLength; j++) {
        int score = computeScore(crossword, i, j, word, dx, dy);

        if (score > maxScore) {
          maxScore = score;
          maxI = i;
          maxJ = j;
        }
      }
    }

    if (maxScore >= 0) {
      placeWord(crossword, maxI, maxJ, word, dx, dy);
      placed = true;
    }

    return placed;
  }

  private static boolean placeVertical(char[][] crossword, int rows, int cols, String word) {
    boolean placed = false;
    int wordLength = word.length();
    int dx = 1, dy = 0;
    int maxScore = -1;
    int placedI = 0, placedJ = 0;

    for (int i = 0; i <= rows - wordLength; i++) {
      for (int j = 0; j < cols; j++) {
        int score = computeScore(crossword, i, j, word, dx, dy);

        if (score > maxScore) {
          maxScore = score;
          placedI = i;
          placedJ = j;
        }
      }

      if (maxScore >= 0) {
        placeWord(crossword, placedI, placedJ, word, dx, dy);
        placed = true;
      }
    }

    return placed;
  }

  private static int computeScore(
      char[][] crossword, int row, int col, String word, int dx, int dy) {
    int len = word.length();
    boolean tooWide = row + len * dx > crossword.length;
    boolean tooHigh = col + len * dy > crossword[0].length;
    int score = 0;

    if (tooWide || tooHigh) {
      return -1; // Word goes beyond the crossword boundaries
    }

    for (int i = 0; i < len; i++) {
      int r = row + i * dx;
      int c = col + i * dy;
      char cell = crossword[r][c];

      if (cell != EMPTY && cell != word.charAt(i)) {
        return -1; // Word intersects with existing letters
      } else if (cell == EMPTY) {
        int r0 = (dx == 0) ? r - 1 : r;
        int c0 = (dy == 0) ? c - 1 : c;
        int r2 = (dx == 0) ? r + 1 : r;
        int c2 = (dy == 0) ? c + 1 : c;
        char ch0 = ((r0 < 0) || (c0 < 0)) ? EMPTY : crossword[r0][c0];
        char ch2 =
            ((r2 >= crossword.length) || (c2 >= crossword[0].length)) ? EMPTY : crossword[r2][c2];

        if (ch0 != EMPTY) {
          return -1;
        }

        if (ch2 != EMPTY) {
          return -1;
        }
      } else if (cell == word.charAt(i)) {
        score++;
      }
    }

    int x = row + -1 * dx;
    int y = col + -1 * dy;
    boolean outside = (x < 0) || (y < 0);
    char previousCell = outside ? EMPTY : crossword[x][y];

    if (previousCell != EMPTY) {
      return -1;
    }

    x = row + len * dx;
    y = col + len * dy;
    outside = (x >= crossword.length) || (y >= crossword[0].length);
    char nextCell = outside ? EMPTY : crossword[x][y];

    if (nextCell != EMPTY) {
      return -1;
    }

    return score;
  }

  public static boolean canPlaceWord(
      char[][] crossword, int row, int col, String word, int dx, int dy) {
    int len = word.length();
    boolean tooWide = row + len * dx > crossword.length;
    boolean tooHigh = col + len * dy > crossword[0].length;
    int score = 0;

    if (tooWide || tooHigh) {
      return false; // Word goes beyond the crossword boundaries
    }

    for (int i = 0; i < len; i++) {
      char cell = crossword[row + i * dx][col + i * dy];
      if (cell != EMPTY && cell != word.charAt(i)) {
        return false; // Word intersects with existing letters
      } else if (cell == word.charAt(i)) {
        score++;
      }
    }

    int x = row + -1 * dx;
    int y = col + -1 * dy;
    boolean outside = (x < 0) || (y < 0);
    char previousCell = outside ? EMPTY : crossword[x][y];

    if (previousCell != EMPTY) {
      return false;
    }

    return true;
  }

  public static void placeWord(char[][] crossword, int row, int col, String word, int dx, int dy) {
    int rowBefore = row - 1;
    int colBefore = col - 1;
    int rowAfter = (row + word.length() * dx);
    int colAfter = (col + word.length() * dy);
    boolean starting = (rowBefore < 0);
    starting = starting || (colBefore < 0);
    boolean ending = (rowAfter == crossword.length);
    ending = ending || (colAfter == crossword[0].length);

    // word = starting ? word : BLOCK + word;
    // word = ending ? word : word + BLOCK;

    int wordLength = word.length();

    for (int i = -1; i <= wordLength; i++) {
      int r = row + i * dx;
      int c = col + i * dy;

      if ((i == -1) && (r >= 0) && (c >= 0)) {
        crossword[r][c] = BLOCK;
      }

      if ((i >= 0) && (i < wordLength)) {
        crossword[r][c] = word.charAt(i);
      }

      if ((i == wordLength) && (r < crossword.length) && (c < crossword[0].length)) {
        crossword[r][c] = BLOCK;
      }
    }
  }

  private static void printCrossword(char[][] crossword) {
    int rows = crossword.length;
    int cols = crossword[0].length;

    for (int i = 0; i < rows; i++) {
      String row = String.valueOf(crossword[i]);
      Console.println(row);
    }
  }
}
