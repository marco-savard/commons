package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Crossword {
  private static final char EMPTY = '.';

  private static final char BLOCK = '#';

  private char[][] grid;

  private static final int MINIMAL_COUNT = 5;

  private int wordCount = 0;

  private List<Entry> horizontalEntries = new ArrayList<>();
  private List<Entry> verticalEntries = new ArrayList<>();

  public static Crossword of(int rows, int cols) {
    Crossword crossword = new Crossword(rows, cols);
    return crossword;
  }

  private Crossword(int rows, int cols) {
    grid = new char[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid[i][j] = EMPTY; // Initialize all cells with empty spaces
      }
    }
  }

  public void fill(List<Question> questions) {
    wordCount = 0;
    List<List<Question>> partitions = partitionByLength(questions);
    List<Question> pickedQuestions = new ArrayList<>();

    for (List<Question> partition : partitions) {
      if (partition.size() >= MINIMAL_COUNT) {
        int wordsPlaced = fillPartition(pickedQuestions, partition, 0);

        if (wordsPlaced == 0) {
          // fillPartition(partition, 1);
        }
      }
    }
  }

  private static List<List<Question>> partitionByLength(List<Question> questions) {
    List<List<Question>> partitions = new ArrayList<>();
    int maxLength = questions.get(0).getWord().length();

    for (int i = maxLength; i >= 1; i--) {
      List<Question> partition = new ArrayList<>();
      partitions.add(partition);
    }

    for (Question question : questions) {
      int len = question.getWord().length();
      int idx = maxLength - len;
      partitions.get(idx).add(question);
    }

    return partitions;
  }

  public int fillPartition(
      List<Question> pickedQuestions, List<Question> questions, int orientation) {
    int rows = grid.length;
    int cols = grid[0].length;
    int minScore = 0;
    int wordsPlaced = 0;
    int i = 0;

    // Place the words
    do {
      wordsPlaced = 0;

      for (Question question : questions) {
        String word = question.getWord();

        boolean alreadyPicked =
            pickedQuestions.stream()
                    .filter(q -> isPicked(q, question))
                    .findAny()
                    .orElse(null)
                != null;

        if (!alreadyPicked) {
          boolean horizontal = (wordCount % 2) == 0; // ((wordsPlaced + orientation) % 2) == 0;
          int placed;

          if (horizontal) {
            placed = placeHorizontal(rows, cols, question, word, minScore);
            wordsPlaced += placed;

          } else {
            placed = placeVertical(rows, cols, question, word, minScore);
            wordsPlaced += placed;
          }

          if (placed > 0) {
            wordCount++;
            pickedQuestions.add(question);
            print();
          }

          minScore = (wordsPlaced < 2) ? 0 : 1;
        }
      }
      // orientation++;
    } while (!questions.isEmpty() && (wordsPlaced > 0));

    return wordsPlaced;
  }

  private boolean isPicked(Question pickedQuestion, Question question) {
    boolean picked = pickedQuestion.getWord().equals(question.getWord());
    picked = picked ||  pickedQuestion.getHint().equals(question.getHint());
    return picked;
  }

  private int placeHorizontal(int rows, int cols, Question question, String word, int minScore) {
    int placed = 0;
    int dx = 0, dy = 1;

    int wordLength = word.length();
    int maxScore = -1;
    int maxI = 0, maxJ = 0;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j <= cols - wordLength; j++) {
        int score = computeScore(i, j, word, dx, dy);

        if (score > maxScore) {
          maxScore = score;
          maxI = i;
          maxJ = j;
        }
      }
    }

    if (maxScore >= minScore) {
      placeWord(maxI, maxJ, question, word, dx, dy);
      placed = 1;
    }

    return placed;
  }

  private int placeVertical(int rows, int cols, Question question, String word, int minScore) {
    int placed = 0;
    int wordLength = word.length();
    int dx = 1, dy = 0;
    int maxScore = -1;
    int placedI = 0, placedJ = 0;

    for (int i = 0; i <= rows - wordLength; i++) {
      for (int j = 0; j < cols; j++) {
        int score = computeScore(i, j, word, dx, dy);

        if (score > maxScore) {
          maxScore = score;
          placedI = i;
          placedJ = j;
        }
      }
    }

    if (maxScore >= minScore) {
      placeWord(placedI, placedJ, question, word, dx, dy);
      placed = 1;
    }

    return placed;
  }

  private int computeScore(int row, int col, String word, int dx, int dy) {
    int len = word.length();
    boolean tooWide = row + len * dx > grid.length;
    boolean tooHigh = col + len * dy > grid[0].length;
    int score = 0;

    if (tooWide || tooHigh) {
      return -1; // Word goes beyond the crossword boundaries
    }

    for (int i = 0; i < len; i++) {
      int r = row + i * dx;
      int c = col + i * dy;
      char cell = grid[r][c];

      if (cell != EMPTY && cell != word.charAt(i)) {
        return -1; // Word intersects with existing letters
      } else if (cell == EMPTY) {
        int r0 = (dx == 0) ? r - 1 : r;
        int c0 = (dy == 0) ? c - 1 : c;
        int r2 = (dx == 0) ? r + 1 : r;
        int c2 = (dy == 0) ? c + 1 : c;
        char ch0 = ((r0 < 0) || (c0 < 0)) ? EMPTY : grid[r0][c0];
        char ch2 = ((r2 >= grid.length) || (c2 >= grid[0].length)) ? EMPTY : grid[r2][c2];

        if ((ch0 != EMPTY) && (ch0 != BLOCK)) {
          return -1;
        }

        if ((ch2 != EMPTY) && (ch2 != BLOCK)) {
          return -1;
        }
      } else if (cell == word.charAt(i)) {
        score++;
      }
    }

    int x = row + -1 * dx;
    int y = col + -1 * dy;
    boolean outside = (x < 0) || (y < 0);
    char previousCell = outside ? EMPTY : grid[x][y];

    if ((previousCell != EMPTY) && (previousCell != BLOCK)) {
      return -1;
    }

    x = row + len * dx;
    y = col + len * dy;
    outside = (x >= grid.length) || (y >= grid[0].length);
    char nextCell = outside ? EMPTY : grid[x][y];

    if ((nextCell != EMPTY) && (nextCell != BLOCK)) {
      return -1;
    }

    return score;
  }

  public void placeWord(int row, int col, Question question, String word, int dx, int dy) {
    int rowBefore = row - 1;
    int colBefore = col - 1;
    int rowAfter = (row + word.length() * dx);
    int colAfter = (col + word.length() * dy);
    boolean starting = (rowBefore < 0);
    starting = starting || (colBefore < 0);
    boolean ending = (rowAfter == grid.length);
    ending = ending || (colAfter == grid[0].length);

    // word = starting ? word : BLOCK + word;
    // word = ending ? word : word + BLOCK;

    int wordLength = word.length();

    for (int i = -1; i <= wordLength; i++) {
      int r = row + i * dx;
      int c = col + i * dy;

      if ((i == -1) && (r >= 0) && (c >= 0)) {
        grid[r][c] = BLOCK;
      }

      if ((i >= 0) && (i < wordLength)) {
        grid[r][c] = word.charAt(i);
      }

      if ((i == wordLength) && (r < grid.length) && (c < grid[0].length)) {
        grid[r][c] = BLOCK;
      }
    }

    saveEntry(row, col, question, dx, dy);
  }

  private void saveEntry(int row, int col, Question question, int dx, int dy) {

    if (dy > 0) {
      Entry entry = new Entry(question, row, col);
      horizontalEntries.add(entry);
    } else if (dx > 0) {
      Entry entry = new Entry(question, col, row);
      verticalEntries.add(entry);
    }
  }

  public void print() {
    int rows = grid.length;
    int cols = grid[0].length;

    for (int i = 0; i < rows; i++) {
      String row = String.valueOf(grid[i]);
      Console.println(row);
    }
    Console.println();
    printHorizontalHints();
    printVerticalHints();
  }

  private void printHorizontalHints() {
    Console.println("Horizontal");
    int rows = grid.length;

    for (int i = 0; i < rows; i++) {
      printHintsAtPos(horizontalEntries, i);
    }

    Console.println();
  }

  private void printVerticalHints() {
    Console.println("Vertical");
    int cols = grid[0].length;

    for (int i = 0; i < cols; i++) {
      printHintsAtPos(verticalEntries, i);
    }

    Console.println();
  }

  private void printHintsAtPos(List<Entry> entries, int pos) {
    List<Entry> filtered =
        entries.stream()
            .filter(e -> e.pos == pos)
            .sorted(Comparator.comparing(e -> e.order))
            .toList();

    if (!filtered.isEmpty()) {
      Console.print(Integer.toString(pos + 1) + " ");

      for (int i = 0; i < filtered.size(); i++) {
        Question question = filtered.get(i).question;
        Console.print(question.getHint() + "; ");
      }

      Console.println();
    }
  }

  private static class Entry {
    Question question;
    int pos;
    int order;

    public Entry(Question question, int pos, int order) {
      this.question = question;
      this.pos = pos;
      this.order = order;
    }
  }
}
