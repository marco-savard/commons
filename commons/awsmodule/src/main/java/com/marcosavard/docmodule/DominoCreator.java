package com.marcosavard.docmodule;

import com.marcosavard.common.math.arithmetic.IntegerGrid;
import org.apache.poi.xwpf.usermodel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DominoCreator {
    private static final String BLACK = "000000";
    private static final String GREEN = "00CF00";
    private static final String GRAY = "AAAAAA";
    private static final String WHITE = "FFFFFF";

    private int[][] magicSquare;
    private int[][] solutionDominos, shuffledDominos;
    private List<int[]> pickedDominos;
    private int magicSum;

    public void printTitle(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("Le domino magique");
        run.addCarriageReturn();

        run = paragraph.createRun();
        run.setText("Il y a huit dominos disposés sur quatre rangées. Il faut interchanger deux dominos de façon à former un carré magique. ");
        run.setText("Un carré est magique quand toutes ses rangées, toutes ses colonnes et ses deux diagonales ont la même somme.");
        run.addCarriageReturn();
    }

    public void generateMagicSquare(Random random) {
        int[][] matrix = generateBaseMatrix(random);
        IntegerGrid grid = IntegerGrid.of(matrix);

        grid.rotate(random.nextInt(4));
        grid.mirror(random.nextBoolean(), random.nextBoolean());
        grid.transpose(random.nextBoolean(), random.nextBoolean());
        magicSquare = grid.getValues();
        magicSum = IntegerGrid.of(magicSquare).getMagicSquareSum();

        generateDominos();
        shuffledDominos = cloneGrid(solutionDominos);
        pickedDominos = pickDominos(shuffledDominos, 2, random);
        swapDominos(shuffledDominos, pickedDominos);
    }

    private static void swapDominos(int[][] dominos, List<int[]> pickedDominos) {
        int[] first = pickedDominos.get(0);
        int[] second = pickedDominos.get(1);

        int firstCode = dominos[first[0]][first[1]];
        int secondCode = dominos[second[0]][second[1]];

        dominos[first[0]][first[1]] = secondCode; //swap
        dominos[second[0]][second[1]] = firstCode;
    }

    private List<int[]> pickDominos(int[][] dominos, int nbPicks, Random random) {
        List<Integer> pickedNumber = new ArrayList<>();
        List<int[]> pickedDominos = new ArrayList<>();
        int total = dominos.length * dominos[0].length;

        do {
            int picked = random.nextInt(total);

            if (! pickedNumber.contains(picked)) {
                pickedNumber.add(picked);
            }
        } while (pickedNumber.size() < nbPicks);

        for (int picked : pickedNumber) {
            int row = picked / dominos[0].length;
            int col = picked % dominos[0].length;
            pickedDominos.add(new int[] {row, col});
        }

        return pickedDominos;
    }

    private static int[][] cloneGrid(int[][] grid) {
        return Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
    }

    private void generateDominos() {
        int rows = magicSquare.length;
        int cols = magicSquare[0].length / 2;
        solutionDominos = new int[rows][cols];

        for (int i=0; i<rows; i++) {
            int[] row = magicSquare[i];
            for (int j=0; j<cols; j++) {
                int first = row[j*2];
                int second = row[j*2+1];
                int code = findCode(first, second);
                solutionDominos[i][j] = code;
            }
        }
    }

    private int findCode(int first, int second) {
        int base = 0x1F031;
        int code = base + (first * 7) + second;
        return code;
    }


    //jeux et strategies, n2 1980-04-05
    private static int[][] generateBaseMatrix(Random random) {
        int[][] matrix;
        int number = 6+ random.nextInt(13);

        if (number == 6) {
            matrix = new int[][] {new int[] {2, 2, 0, 2}, new int[] {0, 1, 3, 2}, new int[] {3, 0, 2, 1}, new int[] {1, 3, 1, 1}};
        } else if (number == 7) {
            matrix = new int[][] {new int[] {1, 0, 4, 2}, new int[] {3, 2, 2, 0}, new int[] {2, 2, 1, 2}, new int[] {1, 3, 0, 3}};
        } else if (number == 8) {
            matrix = new int[][] {new int[] {2, 3, 2, 1}, new int[] {3, 1, 4, 0}, new int[] {3, 3, 0, 2}, new int[] {0, 1, 2, 5}};
        } else if (number == 9) {
            matrix = new int[][] {new int[] {2, 2, 1, 4}, new int[] {0, 4, 2, 3}, new int[] {4, 0, 3, 2}, new int[] {3, 3, 3, 0}};
        } else if (number == 10) {
            matrix = new int[][] {new int[] {1, 1, 5, 3}, new int[] {2, 4, 1, 3}, new int[] {4, 3, 2, 1}, new int[] {3, 2, 2, 3}};
        } else if (number == 11) {
            matrix = new int[][] {new int[] {6, 0, 2, 3}, new int[] {2, 1, 3, 5}, new int[] {2, 4, 3, 2}, new int[] {1, 6, 3, 1}};
        } else if (number == 12) {
            matrix = new int[][] {new int[] {5, 4, 0, 3}, new int[] {4, 2, 6, 0}, new int[] {2, 2, 2, 6}, new int[] {1, 4, 4, 3}};
        } else if (number == 13) {
            matrix = new int[][] {new int[] {2, 4, 2, 5}, new int[] {5, 5, 2, 1}, new int[] {1, 1, 5, 6}, new int[] {5, 3, 4, 1}};
        } else if (number == 14) {
            matrix = new int[][] {new int[] {2, 4, 2, 6}, new int[] {6, 4, 3, 1}, new int[] {2, 1, 6, 5}, new int[] {4, 5, 3, 2}};
        } else if (number == 15) {
            matrix = new int[][] {new int[] {1, 3, 6, 5}, new int[] {4, 5, 2, 4}, new int[] {6, 4, 4, 1}, new int[] {4, 3, 3, 5}};
        } else if (number == 16) {
            matrix = new int[][] {new int[] {2, 6, 3, 5}, new int[] {5, 6, 3, 2}, new int[] {4, 3, 4, 5}, new int[] {5, 1, 6, 4}};
        } else if (number == 17) {
            matrix = new int[][] {new int[] {5, 4, 6, 3}, new int[] {2, 4, 6, 6}, new int[] {6, 4, 4, 4}, new int[] {5, 6, 2, 5}};
        } else {
            matrix = new int[][] {new int[] {5, 4, 4, 6}, new int[] {6, 3, 5, 5}, new int[] {6, 6, 5, 2}, new int[] {2, 6, 5, 6}};
        }

        return matrix;
    }

    public void printShuffledDominos(XWPFDocument document) {
        printDominos(document, shuffledDominos, List.of());
    }

    private void printDominos(XWPFDocument document, int[][] dominos, List<int[]> picked) {
        int rows = dominos.length, cols = dominos[0].length;
        int tableWidth = 1200 * cols;

        XWPFTable table = document.createTable(rows, 1);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(tableWidth);

        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, GRAY);
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, GRAY);
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, GRAY);
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, GRAY);
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, WHITE);
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, WHITE);

        for (int i=0; i<rows; i++) {
            XWPFTableRow row = table.getRow(i);
            fillRow(row, i, cols, dominos[i], picked);
        }
    }

    private static void fillRow(XWPFTableRow row, int rowId, int cols, int[] codes, List<int[]> picked) {
        XWPFTableCell cell = row.getCell(0);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        for (int i=0; i<cols; i++) {
            int[] coord = new int[] {rowId, i};
            boolean isPicked = picked.stream().anyMatch(e -> e[0] == coord[0] && e[1] == coord[1]);
            XWPFRun run = paragraph.createRun();
            run.setFontSize(40);
            run.setColor(isPicked ? GREEN : BLACK);
            String text = Character.toString(codes[i]) + " ";
            run.setText(text);
        }
    }


    public void printSolution(XWPFDocument document) {
        int fontSize = 11, dominoSize = 28;
        int domino1 = shuffledDominos[pickedDominos.get(0)[0]][pickedDominos.get(0)[1]];
        int domino2 = shuffledDominos[pickedDominos.get(1)[0]][pickedDominos.get(1)[1]];

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("Solution du domino magique :");
        run.addCarriageReturn();

        run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setText("Il faut interchanger les dominos  ");
        run = paragraph.createRun();
        run.setFontSize(dominoSize);
        run.setText(Character.toString(domino1));
        run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setText("  et  ");

        run = paragraph.createRun();
        run.setFontSize(dominoSize);
        run.setText(Character.toString(domino2));

        run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setText("  pour obtenir un carré magique. ");
        run.setText("Dans ce cas-ci, nous obtenons un carré dont toutes les rangées, ");
        run.setText("toutes les colonnes et des deux grandes diagonales ont une somme égale à " + magicSum + ".");
        run.addCarriageReturn();

        printDominos(document, solutionDominos, pickedDominos);
    }
}
