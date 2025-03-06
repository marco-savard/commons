package com.marcosavard.library.poi.word;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.quiz.fr.citations.CitationReader;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CitationCreator {
    private static final String GRAY = "707070";
    private static final String WHITE = "FFFFFF";
    private String citation, author;
    private int cols;
    private char[][] letters;

    public CitationCreator(int cols) {
        this.cols = cols;
    }

    public String[] selectCitationAndAuthors(Random random) {
        CitationReader reader = new CitationReader();
        Map<String, CitationReader.Author> citationsByAuthor = reader.readAll();
        List<String> allAuthors = new ArrayList<>(citationsByAuthor.keySet());
        List<String> authors = new ArrayList<>();
        boolean correct;

        do {
            int authorIdx = random.nextInt(citationsByAuthor.size());
            author = allAuthors.get(authorIdx);
            List<String> citations = citationsByAuthor.get(author).getCitations();
            int citationIdx = random.nextInt(citations.size());
            citation = citations.get(citationIdx);
            int len = citation.length();
            correct = (len >= cols * 2) && (len <= cols * 4);
        } while (! correct);

        authors.add(author);
        String[] text = new String[4];
        text[0] = citation;
        text[1] = author;

        do {
            int authorIdx = random.nextInt(citationsByAuthor.size());
            String otherAuthor = allAuthors.get(authorIdx);

            if (! authors.contains(otherAuthor)) {
                authors.add(otherAuthor);
            }
        } while (authors.size() < 3);

        text[2] = authors.get(1);
        text[3] = authors.get(2);
        return text;
    }

    public String normalize(String original) {
        return StringUtil.stripAccents(original).replace('.', ' ').trim();
    }

    public char[][] shuffleLetters(String citation, int cols, int rows, Random random) {
        letters = new char[cols][rows];

        //init
        for (int i=0; i<cols; i++) {
            for (int j=0; j<rows; j++) {
                letters[i][j] = ' ';
            }
        }

        //fill array
        for (int i=0; i<citation.length(); i++) {
            int col = i % cols;
            int row = i / cols;
            char ch = citation.charAt(i);
            letters[col][row] = ch;
        }

        for (int i=0; i<cols; i++) {
            shuffle(letters[i], random);
        }

        return letters;
    }

    // Fisher–Yates shuffle
    private static void shuffle(char[] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // swap
            char ch = array[index];
            array[index] = array[i];
            array[i] = ch;
        }
    }

    public void fillGrid(XWPFDocument document, String citation, int cols, char[][] letters, boolean printSolution) {
        int rows = (int)Math.ceil(citation.length() / (double)cols);

        //cells are square
        int cellSize = 500;
        int cellWidth = cellSize, cellHeight = cellSize;
        int tableWidth = cols * (cellWidth + 20);

        XWPFTable table = document.createTable(rows + 1, cols);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(tableWidth);

        //add header
        XWPFTableRow header = table.getRow(0);
        fillHeader(header, rows, cols, letters);

        for (int i=0; i<rows; i++) {
            XWPFTableRow row = table.getRow(i+1);
            row.setHeight(cellHeight);
            int end = i*cols + cols;
            end = (end > citation.length()) ? citation.length() : end;
            String line = citation.substring(i*cols, end);
            fillRow(row, i, cols, line, printSolution);
        }

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void fillHeader(XWPFTableRow header, int rows, int cols, char[][] letters) {
        //fill cell
        for (int i=0; i<cols; i++) {
            XWPFTableCell cell = header.getCell(i);
            XWPFParagraph paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();

            for (int j=0; j<letters[i].length; j++) {
                char ch = Character.toUpperCase(letters[i][j]);
                if (ch != ' ') {
                    run.setText(Character.toString(ch));
                    run.addCarriageReturn();
                }
            }
        }
    }

    private void fillRow(XWPFTableRow row, int rowId, int cols, String line, boolean printSolution) {
        for (int i=0; i<cols; i++) {
            char ch = (i >= line.length()) ? ' ' : line.charAt(i);
            ch = Character.toUpperCase(ch);
            fillCell(row, rowId, i, ch, printSolution);
        }
    }

    private void fillCell(XWPFTableRow row, int i, int j, char ch, boolean printSolution) {
        XWPFTableCell cell = row.getCell(j);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        String text = printSolution ? Character.toString(ch) : " ";
        cell.setText(text);

        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        String color = (ch == ' ') ? GRAY : WHITE;
        CTShd shd = tcPr.addNewShd();
        shd.setFill(color);
    }

    public String getCitation() {
        return citation;
    }


    public int getColumns() {
        return cols;
    }

    public char[][] getLetters() {
        return letters;
    }

    public void fillAuthors(XWPFParagraph paragraph, String[] citationAndAuthors, Random random) {
        XWPFRun run = paragraph.createRun();
        List<String> authors = new ArrayList<>();

        for (int i=1; i<citationAndAuthors.length; i++) {
            authors.add(citationAndAuthors[i]);
        }

        Collections.shuffle(authors, random);

        for (String author : authors) {
            run.setText(" - " + author);
            run.addCarriageReturn();
        }

        run.addCarriageReturn();
    }

    public String getAuthor() {
        return author;
    }
}
