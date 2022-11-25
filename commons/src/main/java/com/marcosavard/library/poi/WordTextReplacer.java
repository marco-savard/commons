package com.marcosavard.library.poi;

import org.apache.poi.xwpf.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WordTextReplacer {
    private InputStream in;
    private OutputStream out;

    private Map<String, String> replacements = new HashMap<>();

    public WordTextReplacer(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void addReplacement(String original, String replacement) {
        Properties properties = System.getProperties();
        properties.put(original, replacement);

        //replacements.put(original, replacement);
    }

    public void replace() {
        try {
            XWPFDocument document = new XWPFDocument(in);
            List<XWPFTable> tables = document.getTables();
            for (XWPFTable table : tables) {
                replace(table);
            }

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                replace(paragraph);
            }

            document.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void replace(XWPFTable table) {
        List<XWPFTableRow> rows = table.getRows();
        for (XWPFTableRow row : rows) {
            replace(row);
        }
    }

    public void replace(XWPFTableRow row) {
        List<XWPFTableCell> cells = row.getTableCells();
        for (XWPFTableCell cell : cells) {
            replace(cell);
        }
    }

    public void replace(XWPFTableCell cell) {
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            replace(paragraph);
        }
    }

    public void replace(XWPFParagraph paragraph) {
        /* TODO
        List<XWPFRun> runs =  paragraph.getRuns();
        for (XWPFRun run : runs) {
            replace(run);
        }*/
    }

    public void replace(XWPFRun run) {
        String text = ""; //TODO run.getText(0);
        text = (text == null) ? "" : text;
        boolean changed = false;

        Properties properties = System.getProperties();
        for (Object entry : properties.entrySet()) {
            String key = "{" + ((Map.Entry<String, String>)entry).getKey() + "}";
            String value = ((Map.Entry<String, String>)entry).getValue();

            while (text.contains(key)) {
                text = text.replace(key, value);
                System.out.println("Replaced : " + text);
                changed = true;
            }
        }

        Map.Entry<String, String> e;

        for (String original : replacements.keySet()) {
            String replacement = replacements.get(original);
            while (text.contains(original)) {
                text = text.replace(original, replacement);
                System.out.println("Replaced : " + text);
                changed = true;
            }
        }

        if (changed) {
            //TODO run.setText(text, 0);
        }
    }
}
