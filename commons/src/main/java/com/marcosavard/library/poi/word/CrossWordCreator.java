package com.marcosavard.library.poi.word;

import com.marcosavard.commons.quiz.fr.Crossword;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.util.List;

public class CrossWordCreator {
    private static final String GRAY = "707070";
    private static final String WHITE = "FFFFFF";

    public void printTitle(XWPFDocument document, String title, ParagraphAlignment alignment, int fontSize) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(alignment);

        XWPFRun run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setText(title);
        run.addCarriageReturn();
    }

    public XWPFTable createGrid(XWPFDocument document, int rows, int cols, boolean[][] states, int cellSize, int fontSize) {
        //cells are square
        int cellWidth = cellSize, cellHeight = cellSize;
        int tableWidth = (cols + 1) * (cellWidth + 20);

        XWPFTable table = document.createTable(rows+1, cols+1);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(tableWidth);

        table.setTopBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        table.setLeftBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        table.setBottomBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        table.setRightBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        table.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        table.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        XWPFTableRow firstRow = table.getRow(0);

        for (int i=0; i<=rows; i++) {
            XWPFTableRow row = table.getRow(i);
            row.setHeight(cellHeight);
        }

        for (int i=0; i<cols; i++) {
            XWPFTableCell cell = firstRow.getCell(i+1);
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTTOM);
            XWPFParagraph titleParagraph = cell.getParagraphs().get(0);
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText(Integer.toString(i+1));
            titleRun.setFontSize(fontSize);
        }

        for (int i=0; i<rows; i++) {
            XWPFTableRow row = table.getRow(i+1);
            XWPFTableCell cell = row.getCell(0);
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            XWPFParagraph titleParagraph = cell.getParagraphs().get(0);
            titleParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText(Integer.toString(i+1));
            titleRun.setFontSize(fontSize);
        }

        for (int i=0; i<rows; i++) {
            XWPFTableRow row = table.getRow(i+1);
            XWPFTableCell leftCell = row.getCell(0);
            CTTcPr tcPr0 = leftCell.getCTTc().addNewTcPr();
            CTTcBorders borders0 = tcPr0.addNewTcBorders();
            CTBorder right0 = borders0.addNewRight();
            right0.setVal(STBorder.SINGLE);
            right0.setColor(GRAY);

            for (int j=0; j<cols; j++) {
                XWPFTableCell topCell = firstRow.getCell(j+1);
                CTTcPr tcPr1 = topCell.getCTTc().addNewTcPr();
                CTTcBorders borders1 = tcPr1.addNewTcBorders();
                CTBorder bottom1 = borders1.addNewBottom();
                bottom1.setVal(STBorder.SINGLE);
                bottom1.setColor(GRAY);
                
                XWPFTableCell cell = row.getCell(j+1);
                CTTcPr tcPr = cell.getCTTc().addNewTcPr();
                CTTcBorders borders = tcPr.addNewTcBorders();
                CTBorder bottom = borders.addNewBottom();
                CTBorder right = borders.addNewRight();

                bottom.setVal(STBorder.SINGLE);
                bottom.setColor(GRAY);
                right.setVal(STBorder.SINGLE);
                right.setColor(GRAY);

                boolean state = states[i][j];
                String color = state ? GRAY : WHITE;
                CTShd shd = tcPr.addNewShd();
                shd.setFill(color);
            }
        }

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
        return table;
    }

    public void createAllDefinitions(XWPFDocument document, List<String> horizontalDefinitions, List<String> verticalDefinitions) {
        XWPFTable definitionTable = document.createTable(1, 2);
        definitionTable.setTopBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        definitionTable.setBottomBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        definitionTable.setLeftBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        definitionTable.setRightBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        definitionTable.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, GRAY);
        XWPFTableRow row = definitionTable.getRow(0);

        createDefinitions(row.getCell(0), "Horizontalement", horizontalDefinitions);
        createDefinitions(row.getCell(1), "Verticalement", verticalDefinitions);
    }

    private void createDefinitions(XWPFTableCell cell, String title, List<String> definitions) {
        XWPFParagraph titleParagraph = cell.addParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText(title);
        titleRun.setBold(true);
        titleRun.addCarriageReturn();

        XWPFParagraph definitionsParagraph = cell.addParagraph();
        definitionsParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (String definition : definitions) {
            createDefinition(definitionsParagraph, definition);
        }
    }

    private void createDefinition(XWPFParagraph definitionsParagraph, String definition) {
        XWPFRun definitionsRun = definitionsParagraph.createRun();
        definitionsRun.setFontSize(10);
        definitionsRun.setText(definition);
        definitionsRun.addCarriageReturn();
    }

    public void fillGrid(XWPFTable table, Crossword crossword, int fontSize) {
        int rows = table.getNumberOfRows();
        char[][] grid = crossword.getGrid();

        for (int i=1; i<rows; i++) {
            XWPFTableRow tableRow = table.getRow(i);
            int cols = tableRow.getTableCells().size();

            for (int j=1; j<cols; j++) {
                XWPFTableCell cell = tableRow.getTableCells().get(j);
                fillCell(cell, grid, i-1, j-1, fontSize);
            }
        }
    }

    private void fillCell(XWPFTableCell cell, char[][] grid, int row, int col, int fontSize) {
        char ch = Character.toUpperCase(grid[row][col]);

        if (Character.isLetter(ch)) {
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            XWPFParagraph titleParagraph = cell.getParagraphs().get(0);
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText(Character.toString(ch));
            titleRun.setFontSize(fontSize);
        }
    }



}
