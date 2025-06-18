package com.marcosavard.common.math.arithmetic;

import java.util.Arrays;

public class IntegerGrid {
    int[][] grid;

    public static IntegerGrid of(int[][] grid) {
        return new IntegerGrid(grid);
    }

    private IntegerGrid(int[][] grid) {
        this.grid = cloneGrid(grid);
    }

    public static int[][] cloneGrid(int[][] grid) {
        return Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
    }

    public int[][] getValues() {
        return grid;
    }

    //rotations = 0, 1 (90°), 2 (180°) or 3 (270°)
    public void rotate(int rotations) {
        rotations = (rotations % 4);

        for (int i = 0; i < rotations; i++) {
            rotate90();
        }
    }

    private int[][] rotate90() {
        int n = grid.length;
        int m = grid[0].length;
        int[][] rotated = new int[m][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated[j][n - 1 - i] = grid[i][j];
            }
        }
        return rotated;
    }

    public boolean isSquare() {
        int n = grid.length;

        for (int[] row : grid) {
            if (row.length != n) {
                return false;
            }
        }

        return true;
    }


    public int getMagicSquareSum() {
        int n = grid.length;

        if (! isSquare()) {
            return -1;
        }

        // Calculer la somme de la première ligne comme référence
        int magicSum = 0;
        for (int num : grid[0]) {
            magicSum += num;
        }

        // Vérifier les sommes des autres lignes
        for (int i = 1; i < n; i++) {
            int rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += grid[i][j];
            }
            if (rowSum != magicSum) {
                return -1;
            }
        }

        // Vérifier les sommes des colonnes
        for (int j = 0; j < n; j++) {
            int colSum = 0;
            for (int i = 0; i < n; i++) {
                colSum += grid[i][j];
            }
            if (colSum != magicSum) {
                return -1;
            }
        }

        // Vérifier la somme de la première diagonale (\)
        int diag1Sum = 0;
        for (int i = 0; i < n; i++) {
            diag1Sum += grid[i][i];
        }
        if (diag1Sum != magicSum) {
            return -1;
        }

        // Vérifier la somme de la seconde diagonale (/)
        int diag2Sum = 0;
        for (int i = 0; i < n; i++) {
            diag2Sum += grid[i][n - 1 - i];
        }
        if (diag2Sum != magicSum) {
            return -1;
        }

        // Si toutes les conditions sont remplies, c'est un carré magique
        return magicSum;
    }

    public void mirror(boolean horizontal, boolean vertical) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] mirrored = new int[rows][cols];

        if (horizontal) {
            // Inverser les lignes (effet miroir horizontal)
            for (int i = 0; i < rows; i++) {
                mirrored[i] = grid[rows - 1 - i].clone();
            }
        }

        if (vertical) { //vertical
            // Inverser les colonnes (effet miroir vertical)
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    mirrored[i][j] = grid[i][cols - 1 - j];
                }
            }
        }
    }

    public int[][] transpose(boolean primary, boolean secondary) {
        int n = grid.length;

        // Vérifier que la matrice est carrée
        for (int[] row : grid) {
            if (row.length != n) {
                throw new IllegalArgumentException("La matrice doit être carrée.");
            }
        }

        int[][] transposed = new int[n][n];

        if (primary) {
            // Transposition selon la diagonale principale (\)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    transposed[j][i] = grid[i][j];
                }
            }
        }

        if (secondary) {
            // Transposition selon la diagonale secondaire (/)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    transposed[n - 1 - j][n - 1 - i] = grid[i][j];
                }
            }
        }

        return transposed;
    }

}
