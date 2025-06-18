package com.marcosavard.common.quiz.general.word;

import com.marcosavard.common.math.arithmetic.IntegerGrid;

import java.util.Random;

public class SudokuGrid {
    private int[][] grid = new int[9][9];
    private Random random;

    public SudokuGrid(Random random) {
        this.random = random;
    }

    public SudokuGrid(int[][] grid) {
        this.grid = grid;
    }

    @Override
    public Object clone() {
        int [][] copy = new int[grid.length][];
        for(int i = 0; i < grid.length; i++)
            copy[i] = grid[i].clone();

        SudokuGrid cloned = new SudokuGrid(copy);
        cloned.random = this.random;
        return cloned;
    }

    public void fillGrid() {
        fillDiagonal(grid);
        fillRemaining(grid, 0, 3);
    }

    public int[][] getRows() {
        return grid;
    }

    // fill the diagonal 3x3 matrices
    private void fillDiagonal(int[][] grid) {
        for (int i = 0; i < 9; i = i + 3) {
            fillBox(grid, i, i);
        }
    }

    // fill remaining blocks
    private boolean fillRemaining(int[][] grid, int i, int j) {
        if (j >= 9 && i < 8) {
            i = i + 1;
            j = 0;
        }
        if (i >= 9 && j >= 9) {
            return true;
        }
        if (i < 3) {
            if (j < 3) {
                j = 3;
            }
        }
        else if (i < 6) {
            if (j == (i / 3) * 3) {
                j = j + 3;
            }
        }
        else {
            if (j == 6) {
                i = i + 1;
                j = 0;
                if (i >= 9) {
                    return true;
                }
            }
        }

        for (int num = 1; num <= 9; num++) {
            if (checkIfSafe(grid, i, j, num)) {
                grid[i][j] = num;
                if (fillRemaining(grid, i, j + 1)) {
                    return true;
                }
                grid[i][j] = 0;
            }
        }
        return false;
    }

    // check if safe to put in cell
    private boolean checkIfSafe(int[][] grid, int i, int j,
                               int num) {
        return (unusedInRow(grid, i, num)
                && unusedInCol(grid, j, num)
                && unusedInBox(grid, i - i % 3, j - j % 3,
                num));
    }

    // check if it's safe to put num in column j
    private boolean unusedInCol(int[][] grid, int j, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // check if it's safe to put num in row i
    private boolean unusedInRow(int[][] grid, int i, int num) {
        for (int j = 0; j < 9; j++) {
            if (grid[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // fill a 3x3 matrix
    private void fillBox(int[][] grid, int row, int col) {
        int num;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(9) + 1;
                } while (!unusedInBox(grid, row, col, num));
                grid[row + i][col + j] = num;
            }
        }
    }

    // returns false if given 3x3 block contains num
    private boolean unusedInBox(int[][] grid, int rowStart, int colStart, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public void removeCells(int n) {
        removeRandomCells(n);
        makeSureNoFullRows();
        makeSureNoFullColumns();
    }

    private void makeSureNoFullRows() {
        for (int i=0; i<9; i++) {
            if (isFullRow(i)) {
                int j = random.nextInt(9) ;
                grid[i][j] = 0;
            }
        }
    }

    private void makeSureNoFullColumns() {
        for (int j=0; j<9; j++) {
            if (isFullColumn(j)) {
                int i = random.nextInt(9) ;
                grid[i][j] = 0;
            }
        }
    }

    private boolean isFullRow(int idx) {
        int[] row = grid[idx];
        boolean full = true;

        for (int i=0; i<9; i++) {
            if (row[i] == 0) {
                full = false;
                break;
            }
        }

        return full;
    }

    private boolean isFullColumn(int j) {
        boolean full = true;

        for (int i=0; i<9; i++) {
            if (grid[i][j] == 0) {
                full = false;
                break;
            }
        }

        return full;
    }

    private void removeRandomCells(int n) {
        for (int i=0; i<n; i++) {
            int[][] copy = IntegerGrid.cloneGrid(grid);
            int row = random.nextInt(9) ;
            int col = random.nextInt(9) ;
            copy[row][col] = 0;

            if (solveSudoku(copy)) {
                grid[row][col] = 0;
            } else {
                break;
            }

        }
    }

    private boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int number = 1; number <= 9; number++) {
                        if (isValidPlacement(grid, number, row, col)) {
                            grid[row][col] = number;

                            if (solveSudoku(grid)) {
                                return true;
                            }

                            // Backtrack
                            grid[row][col] = 0;
                        }
                    }
                    return false; // No valid number found
                }
            }
        }
        return true; // Solved
    }

    private static boolean isValidPlacement(int[][] board, int number, int row, int col) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == number) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == number) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int subgridRowStart = (row / 3) * 3;
        int subgridColStart = (col / 3) * 3;

        for (int i = subgridRowStart; i < subgridRowStart + 3; i++) {
            for (int j = subgridColStart; j < subgridColStart + 3; j++) {
                if (board[i][j] == number) {
                    return false;
                }
            }
        }

        return true;
    }

    public SudokuGrid rotateClockwise() {
        int[][] copy = new int[grid.length][];
        for (int i=0; i<grid.length; i++) {
            copy[i] = grid[i].clone();
        }

        for (int i = 0; i < copy.length / 2; i++) {
            int top = i;
            int bottom = copy.length - 1 - i;
            for (int j = top; j < bottom; j++) {
                int temp = copy[top][j];
                copy[top][j] = copy[j][bottom];
                copy[j][bottom] = copy[bottom][bottom - (j - top)];
                copy[bottom][bottom - (j - top)] = copy[bottom - (j - top)][top];
                copy[bottom - (j - top)][top] = temp;
            }
        }

        SudokuGrid rotated = new SudokuGrid(copy);
        return rotated;
    }
}
