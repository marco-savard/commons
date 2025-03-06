package com.marcosavard.library.poi.word;

import org.apache.poi.xwpf.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class WordWalker {
    private final XWPFDocument document;
    private List<PictureListener> pictureListeners = new ArrayList<>();
    private List<TableCellListener> tableCellListeners = new ArrayList<>();

    public WordWalker(XWPFDocument document) {
        this.document = document;
    }

    public void addPictureListener(PictureListener listener) {
        pictureListeners.add(listener);
    }

    public void addTableCellListener(TableCellListener listener) {
        tableCellListeners.add(listener);
    }

    public void walk() {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        List<XWPFTable> tables = document.getTables();

        for (XWPFParagraph paragraph : paragraphs) {
            walkParagraph(paragraph);
        }

        for (XWPFTable table : tables) {
            walkTable(table);
        }
    }

    private void walkParagraph(XWPFParagraph paragraph) {
        List<XWPFRun> runs = paragraph.getRuns();

        for (XWPFRun run : runs) {
            walkRun(run);
        }
    }

    private void walkTable(XWPFTable table) {
        List<XWPFTableRow> rows = table.getRows();

        for (XWPFTableRow row : rows) {
            walkRow(row);
        }

    }

    private void walkRow(XWPFTableRow row) {
        List<XWPFTableCell> cells = row.getTableCells();

        for (XWPFTableCell cell : cells) {
            walkCell(cell);
        }
    }

    private void walkCell(XWPFTableCell cell) {
        listenTableCellListeners(cell);
        List<XWPFParagraph> paragraphs = cell.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            walkParagraph(paragraph);
        }
    }

    private void walkRun(XWPFRun run) {
        List<XWPFPicture> pictures = run.getEmbeddedPictures();

        for (XWPFPicture picture : pictures) {
            walkPicture(run, picture);
        }
    }

    private void walkPicture(XWPFRun run, XWPFPicture picture) {
        listenPictureListeners(run, picture);
    }

    private void listenPictureListeners(XWPFRun run, XWPFPicture picture) {
        for (PictureListener listener : pictureListeners) {
            listener.onPicture(run, picture);
        }
    }

    private void listenTableCellListeners(XWPFTableCell cell) {
        for (TableCellListener listener : tableCellListeners) {
            listener.onTableCell(cell);
        }
    }

    public static abstract class PictureListener {
        public abstract void onPicture(XWPFRun run, XWPFPicture picture);
    }

    public static abstract class TableCellListener {
        public abstract void onTableCell(XWPFTableCell cell);
    }
}
