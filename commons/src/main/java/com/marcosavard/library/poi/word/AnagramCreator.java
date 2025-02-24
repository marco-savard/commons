package com.marcosavard.library.poi.word;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.ling.fr.dic.AnagramReader;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnagramCreator {
    private static final String BLACK = "000000";
    private static final String GRAY = "F0F0F0";
    private static final String WHITE = "FFFFFF";
    private int rows;

    public AnagramCreator(int rows) {
       this.rows = rows;
    }

    public List<String[]> chooseAnagrams(Random random) {
        //read anagram
        AnagramReader reader = new AnagramReader();
        List<String> allAnagrams = reader.readAll();
        List<String[]> chosenAnagrams = new ArrayList<>();
        int rows = 9;

        //choose anagrams
        for (int i=0; i<rows; i++) {
            chosenAnagrams.add(reader.chooseAnagram(allAnagrams, random, i));
        }

        return chosenAnagrams;
    }

    public void printAnagramGrid(XWPFDocument document, List<String[]> chosenAnagrams, boolean printSolution) {
        int cols = (rows + 1) * 2;

        //cells are square
        int cellSize = 400;
        int cellWidth = cellSize, cellHeight = cellSize;
        int tableWidth = cols * (cellWidth + 20);

        XWPFTable table = document.createTable(rows, cols);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(tableWidth);

        for (int i=0; i<rows; i++) {
            XWPFTableRow row = table.getRow(i);
            fillRow(row, rows, cols, i, chosenAnagrams.get(i), printSolution);
        }
    }

    private static void fillRow(XWPFTableRow row, int rows, int cols, int rowIdx, String[] anagrams, boolean printSolution) {
        for (int c=0; c<cols; c++) {
            XWPFTableCell cell = row.getCell(c);
            fillCell(cell, rows, cols, rowIdx, c, anagrams, printSolution);
        }
    }

    private static void fillCell(XWPFTableCell cell, int rows, int cols, int row, int col, String[] anagrams, boolean printSolution) {
        int dist = (int)Math.abs(cols / 2 - (col + 0.5));
        boolean isInside = (dist <= row + 1);
        boolean isLeft = (col < (cols / 2));
        boolean isRight = (col > (cols / 2));
        String anagram = isLeft ? anagrams[0].trim() : anagrams[1].trim();
        anagram = StringUtil.stripAccents(anagram);

        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        CTShd shd = tcPr.addNewShd();
        shd.setFill(isInside && isLeft ? GRAY : WHITE);

        CTTcBorders borders = tcPr.addNewTcBorders();
        CTBorder top = borders.addNewTop();
        CTBorder bottom = borders.addNewBottom();
        CTBorder left = borders.addNewLeft();
        CTBorder right = borders.addNewRight();

        top.setVal(STBorder.SINGLE);
        bottom.setVal(STBorder.SINGLE);
        left.setVal(STBorder.SINGLE);
        right.setVal(STBorder.SINGLE);

        top.setColor(isInside ? BLACK : WHITE);
        bottom.setColor(isInside ? BLACK : WHITE);

        int d0 = (int)Math.floor(Math.abs(col - (cols / 2.0) + 0.5));
        d0 = Math.max(0, d0 - row - 1);

        if (isLeft) {
            left.setColor(d0 == 0 ? BLACK : WHITE);
        } else if (isRight) {
            left.setColor(d0 <= 1 ? BLACK : WHITE);
            right.setColor(d0 == 0 ? BLACK : WHITE);
        } else {
            left.setColor(BLACK);
        }

        int idx = isLeft ? (row + 1 - dist) : dist;
        boolean printable = isLeft ? isInside : isInside && printSolution;
        char ch = printable ? anagram.charAt(idx) : ' ';
        String text = Character.toString(Character.toUpperCase(ch));

        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setText(text);
    }


}
