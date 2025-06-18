package com.marcosavard.awsmodule.sendmail;

import com.marcosavard.common.ling.fr.dic.Word;
import com.marcosavard.common.quiz.fr.dic.LongestWordFinder;
import com.marcosavard.common.quiz.general.word.SudokuGrid;
import com.marcosavard.common.quiz.math.ResultFinder;
import com.marcosavard.common.util.PseudoRandom;
import com.marcosavard.docmodule.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTextDirection;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class AlmanachService {
    private static final int ANAGRAM_NB_ROWS = 9;
    private static final int CITATION_NB_COLS = 16;

    public File generateFile(LocalDate date, Locale display) throws IOException {

        //generate game solutions
        Random random = new PseudoRandom(date.hashCode());
        SudokuGrid sudokuGrid = generateSudokuSolutions(random, date, display);
        AnagramCreator anagramCreator = new AnagramCreator(ANAGRAM_NB_ROWS);
        List<String[]> chosenAnagrams = anagramCreator.chooseAnagrams(random);
        CitationCreator citationCreator = new CitationCreator(CITATION_NB_COLS);
        PokerSolitaire pokerSolitaire = new PokerSolitaire(random);
        //LongestWordFinder longestWordFinder = new LongestWordFinder();
        //longestWordFinder.pickLetters(random);
        ResultFinder resultFinder = new ResultFinder(random);
        DominoCreator dominoCreator = new DominoCreator();

        //create document
        XWPFDocument document = new XWPFDocument();

        //generate games
        //pages.add(generateSunEventPage(date, display));
        generateSodukuPage(document, sudokuGrid);
        generateAnagramPage(document, anagramCreator, chosenAnagrams);
        generateCitation(document, citationCreator, CITATION_NB_COLS, random);
        generatePokerPage(document, pokerSolitaire);
      //  generateLongestWord(document, longestWordFinder);
        generateResultFinder(document, resultFinder);
        generateDomino(document, dominoCreator, random);

        //generate game solutions
        generateSodukuSolutionPage(document, sudokuGrid);
        generateAnagramSolutionPage(document, chosenAnagrams);
        generateCitationSolutionPage(document, citationCreator);
        generatePokerSolutionPage(document, pokerSolitaire, display);
     //   generateLongestWordSolution(document, longestWordFinder);
        generateResultFinderSolution(document, resultFinder);
        generateDominoSolution(document, dominoCreator);

        //create file
        File almanachFile = File.createTempFile("almanac-", ".docx");

        try (OutputStream output = new FileOutputStream(almanachFile)) {
            document.write(output);
        }

        return almanachFile;
    }

    private void generateLongestWord(XWPFDocument document, LongestWordFinder longestWordFinder) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("Le mot le plus long");
        run.addCarriageReturn();

        run = paragraph.createRun();
        run.setText("Trouver le mot le plus long en ré-ordonnant les lettres suivantes:");
        run.addCarriageReturn();
        run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("  " + longestWordFinder.toUppercaseLetters());
        run.addCarriageReturn();
        run.addCarriageReturn();
    }

    private void generateLongestWordSolution(XWPFDocument document, LongestWordFinder longestWordFinder) {
        List<String> bestScores = longestWordFinder.findBestScores();
        List<Word> candidateWords = longestWordFinder.getCandidateWords();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("Le mot le plus long, solutions possibles :");
        run.addCarriageReturn();

        run = paragraph.createRun();
        run.setFontSize(11);

        for (String s : bestScores) {
            Word word = candidateWords.stream().filter(w -> w.getText().equals(s)).findFirst().orElse(null);
            String str = MessageFormat.format("..{0} lettres : {1}, {2}", s.length(), s, word.getDefinitions().get(0));
            XWPFRun run1 = paragraph.createRun();
            run1.setText(str);
            run1.addCarriageReturn();
        }
    }

    private void generateResultFinderSolution(XWPFDocument document, ResultFinder resultFinder) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("Solution du jeu le compte est bon");
        run.addCarriageReturn();

        run = paragraph.createRun();
        run.setText("Une solution possible : ");
        run.addCarriageReturn();

        for (ResultFinder.OperationStep step : resultFinder.getSolution()) {
            run.setText(step.toString());
            run.addCarriageReturn();
        }
    }

    private void generateDominoSolution(XWPFDocument document, DominoCreator dominoCreator) {
        dominoCreator.printSolution(document);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }


    private void generateResultFinder(XWPFDocument document, ResultFinder resultFinder) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(16);
        run.setText("Le compte est bon");
        run.addCarriageReturn();

        run = paragraph.createRun();
        run.setText("Utiliser les nombres suivants en les additionnant, soustrayant, multipliant ou divisant ");
        run.setText("pour retrouver le résultat recherché.");

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("  " + resultFinder.getNumberList());

        String text = MessageFormat.format("Résultat demandé : {0}", resultFinder.getResult());
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText(text);
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void generateDomino(XWPFDocument document, DominoCreator dominoCreator, Random random) {
        dominoCreator.printTitle(document);
        dominoCreator.generateMagicSquare(random);
        dominoCreator.printShuffledDominos(document);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private void generatePokerPage(XWPFDocument document, PokerSolitaire pokerSolitaire) {
        pokerSolitaire.printTitle(document);
        pokerSolitaire.printInstructions(document);
        pokerSolitaire.printCards(document);

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private void generatePokerSolutionPage(XWPFDocument document, PokerSolitaire pokerSolitaire, Locale display) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(20);
        run.setText("Solution du carré de poker :");
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        pokerSolitaire.printSolution(paragraph, display);

        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private File generateSunEventPage(LocalDate date, Locale display) throws IOException {
        SunEventCreator sunEventCreator = new SunEventCreator(date, display);

        //create the file
        File file = File.createTempFile("sun-events-", ".docx");
        sunEventCreator.create(file);
        return file;
    }

    private SudokuGrid generateSudokuSolutions(Random random, LocalDate date, Locale display) {
        //create the grid
        SudokuGrid solution = new SudokuGrid(random);
        solution.fillGrid();
        return solution;
    }

    private void generateSodukuPage(XWPFDocument document, SudokuGrid sudokuGrid) throws IOException {
        //create the sudoku game
        SudokuGrid grid = (SudokuGrid)sudokuGrid.clone();
        grid.removeCells(15);

        //create page
        SudokuCreator sudokuCreator = new SudokuCreator();
        sudokuCreator.printTitle(document, "Sudoku", ParagraphAlignment.CENTER, 24);
        sudokuCreator.printGrid(document, grid, 800, 16);

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private void generateSodukuSolutionPage(XWPFDocument document, SudokuGrid sudokuGrid) throws IOException {
        //create page
        SudokuCreator creator = new SudokuCreator();
        creator.printTitle(document, "Solution du sudoku :", ParagraphAlignment.LEFT, 20);
        creator.printGrid(document, sudokuGrid, 400, 8, STTextDirection.BT_LR);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void generateAnagramPage(XWPFDocument document, AnagramCreator anagramCreator, List<String[]> chosenAnagrams) throws IOException {
        //create page
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(24);
        run.setText("Trouver les anagrammes :");
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();

        anagramCreator.printAnagramGrid(document, chosenAnagrams, false);
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void generateAnagramSolutionPage(XWPFDocument document, List<String[]> chosenAnagrams) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(20);
        run.setText("Solution des anagrammes");
        run.addCarriageReturn();

        AnagramCreator anagramCreator = new AnagramCreator(ANAGRAM_NB_ROWS);
        anagramCreator.printAnagramGrid(document, chosenAnagrams, true);

        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private void generateCitation(XWPFDocument document, CitationCreator citationCreator, int nbCols, Random random) {
        String[] citationAndAuthors = citationCreator.selectCitationAndAuthors(random);

        //shuffle letters
        String normalized = citationCreator.normalize(citationAndAuthors[0]);
        int rows = (int)Math.ceil(normalized.length() / (double)nbCols);
        char[][] letters = citationCreator.shuffleLetters(normalized, nbCols, rows, random);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(24);
        run.setText("Deviner la citation :");
        run.addCarriageReturn();
        citationCreator.fillGrid(document, normalized, nbCols, letters, false);

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Une fois la citation devinée, trouver qui en est l'auteur :");
        run.addCarriageReturn();

        citationCreator.fillAuthors(paragraph, citationAndAuthors, random);
        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private void generateCitationSolutionPage(XWPFDocument document, CitationCreator citationCreator) {
        String citation = citationCreator.getCitation();
        String author = citationCreator.getAuthor();
        int cols = citationCreator.getColumns();
        char[][] letters = citationCreator.getLetters();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(20);
        run.setText("Solution de la citation :");
        run.addCarriageReturn();
        String normalized = citationCreator.normalize(citation);
        citationCreator.fillGrid(document, normalized, cols, letters, true);

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Citation : " + citation);
        run.addCarriageReturn();
        run.setText("Auteur : " + author);
        run.addCarriageReturn();
    }
}
