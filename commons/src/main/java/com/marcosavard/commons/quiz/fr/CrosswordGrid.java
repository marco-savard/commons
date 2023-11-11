package com.marcosavard.commons.quiz.fr;

public class CrosswordGrid {
  private static final char EMPTY = '.';
  private static final char BLANK = '#';

  public enum Direction {
    HORIZONTAL,
    VERTICAL
  };

  private int size;
  private char[][] grid;

  public static CrosswordGrid of(int size) {
    CrosswordGrid grid = new CrosswordGrid(size);
    return grid;
  }

  private CrosswordGrid(int size) {
    this.size = size;
    grid = new char[size][size];

    for (int row = 0; row < size; row++) {
      for (int cell = 0; cell < size; cell++) {
        grid[row][cell] = EMPTY;
      }
    }
  }

  public void print() {
    for (int row = 0; row < grid.length; row++) {
      printRow(grid[row]);
    }
  }

  private void printRow(char[] chars) {
    for (int cell = 0; cell < chars.length; cell++) {
      System.out.print(" " + chars[cell] + " ");
    }
    System.out.println();
  }

  public boolean tryWord(String word, Direction direction, int row, int cell) {
    boolean horizontal = (direction == Direction.HORIZONTAL);
    boolean fit = true;

    for (int i = 0; i < word.length(); i++) {
      int r0 = horizontal ? row - 1 : row + i;
      int r1 = horizontal ? row : row + i;
      int r2 = horizontal ? row + 1 : row + i;

      int c0 = horizontal ? cell + i : cell - 1;
      int c1 = horizontal ? cell + i : cell;
      int c2 = horizontal ? cell + i : cell + 1;

      char l0 = ((r0 < 0) || (c0 < 0)) ? EMPTY : grid[r0][c0];
      char l1 = grid[r1][c1];
      char l2 = ((r2 >= size) || (c2 >= size)) ? EMPTY : grid[r2][c2];

      // fit = (l0 == EMPTY);
      fit = fit && ((l1 == EMPTY) || (l1 == word.charAt(i)));
      //   fit = fit && (l2 == EMPTY);

      if (!fit) {
        break;
      }
    }
    return fit;
  }

  public void addWord(String word, Direction direction, int row, int cell) {
    int len = word.length();
    boolean horizontal = (direction == Direction.HORIZONTAL);
    boolean start = horizontal ? (cell == 0) : (row == 0);
    boolean end = horizontal ? ((cell + len) >= size) : ((row + len) >= size);
    word = start ? word : BLANK + word;
    word = end ? word : word + BLANK;

    int c0 = start ? cell : (horizontal ? cell - 1 : cell);
    int r0 = start ? row : (horizontal ? row : row - 1);

    for (int i = 0; i < word.length(); i++) {
      int x = horizontal ? r0 : r0 + i;
      int y = horizontal ? c0 + i : c0;
      grid[x][y] = word.charAt(i);
    }
  }
}
