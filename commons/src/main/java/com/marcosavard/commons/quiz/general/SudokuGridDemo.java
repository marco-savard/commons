package com.marcosavard.commons.quiz.general;

import com.marcosavard.commons.util.PseudoRandom;

import java.util.Random;

public class SudokuGridDemo {

    public static void main(String[] args) {
        int seed = 3;
        Random random = new PseudoRandom(seed);
        SudokuGrid grid = new SudokuGrid(random);
        grid.fillGrid();;
        printGrid(grid); 
    }

    private static void printGrid(SudokuGrid grid) {

        for (int[] row : grid.getRows()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }

            System.out.println();
        }
        System.out.println();

    }
}
