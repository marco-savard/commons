package com.marcosavard.docmodule;

import com.marcosavard.common.quiz.general.word.SudokuGrid;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

public class SudokuCreator {
    private static final String BLACK = "000000";
    private static final String GRAY = "AAAAAA";
    private static final String LIGHT_GRAY = "F0F0F0";
    private static final String WHITE = "FFFFFF";

    public void printTitle(XWPFDocument document, String title, ParagraphAlignment alignment, int titleFontSize) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(alignment);

        XWPFRun run = paragraph.createRun();
        run.setFontSize(titleFontSize);
        run.setText(title);
        run.addCarriageReturn();
        run.addCarriageReturn();
    }

    public void printGrid(XWPFDocument document, SudokuGrid grid, int cellSize, int cellFontSize) {
        printGrid(document, grid, cellSize, cellFontSize, STTextDirection.LR);
    }

    public void printGrid(XWPFDocument document, SudokuGrid grid, int cellSize, int cellFontSize, STTextDirection.Enum dir) {
        //cells are square
        int cellWidth = cellSize, cellHeight = cellSize;
        int tableWidth = 9 * (cellWidth + 20);

        XWPFTable table = document.createTable(9, 9);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(tableWidth);

        table.setTopBorder(XWPFTable.XWPFBorderType.THICK, 4, 0, BLACK);
        table.setLeftBorder(XWPFTable.XWPFBorderType.THICK, 4, 0, BLACK);
        table.setBottomBorder(XWPFTable.XWPFBorderType.THICK, 4, 0, BLACK);
        table.setRightBorder(XWPFTable.XWPFBorderType.THICK, 4, 0, BLACK);
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 0, 0, GRAY);
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 0, 0, GRAY);

        for (int i = 0; i < 9; i++) {
            XWPFTableRow row = table.getRow(i);
            row.setHeight(cellHeight);
        }

        for (int i = 0; i < 9; i++) {
            XWPFTableRow row = table.getRow(i);

            for (int j = 0; j < 9; j++) {
                createCell(grid, row, i, j, cellFontSize, dir);
            }
        }


    }

    private void createCell(SudokuGrid grid, XWPFTableRow row, int i, int j, int cellFontSize, STTextDirection.Enum dir) {
        XWPFTableCell cell = row.getCell(j);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        tcPr.addNewTextDirection().setVal(dir);
        CTTcBorders borders = tcPr.addNewTcBorders();

        boolean bottomThick = (i % 3) == 2;
        STBorder.Enum style = bottomThick ? STBorder.THICK : STBorder.SINGLE;
        borders.addNewBottom().setVal(style);

        boolean rightThick = (j % 3) == 2;
        style = rightThick ? STBorder.THICK : STBorder.SINGLE;
        borders.addNewRight().setVal(style);

        int value = grid.getRows()[i][j];
        String color = (value == 0) ? WHITE : LIGHT_GRAY;
        CTShd shd = tcPr.addNewShd();
        shd.setFill(color);

        XWPFRun titleRun = paragraph.createRun();
        titleRun.setFontSize(cellFontSize);
        String text = getCellText(grid, value);
        titleRun.setText(text);
    }

    private String getCellText(SudokuGrid grid, int value) {
        String text = (value == 0) ? " " : Integer.toString(value);
        return text;
    }
}


