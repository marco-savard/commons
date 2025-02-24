package com.marcosavard.library.poi.word;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.fr.dic.Word;
import com.marcosavard.commons.quiz.fr.Crossword;
import com.marcosavard.commons.quiz.fr.QuestionList;
import com.marcosavard.commons.quiz.fr.dic.LongestWordFinder;
import com.marcosavard.commons.quiz.general.SudokuGrid;
import com.marcosavard.commons.quiz.math.ResultFinder;
import com.marcosavard.commons.util.PseudoRandom;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTextDirection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GamePagesCreatorDemo {

    public static void main(String[] args) {
        //settings
        LocalDate date = LocalDate.now();
        Locale display = Locale.FRENCH;
        Random random = new PseudoRandom(2);

        //set output file
        String basename = "jeux-du-jour-" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
        String outputFilePath = basename + ".docx";
        File outputFile = new File(outputFilePath);

        //create games
        Crossword crossword = createCrossword(display, random);
        int anagramRows = 9;

        try (OutputStream output = new FileOutputStream(outputFile)) {
            //print games
            XWPFDocument document = new XWPFDocument();
            SudokuGrid sudokuSolution = printSudokuPage(document, random);
            CrossWordCreator crossWordCreator = printCrossword(document, crossword);
            List<String[]> chosenAnagrams = printAnagrams(document, anagramRows, random);
            CitationCreator citationCreator = printCitation(document, random);
            PokerSolitaire pokerSolitaire = printPoker(document, random);
            DominoCreator dominorCreator = printDominos(document, random);
            LongestWordFinder longestWordFinder = printLongestWord(document, random);
            ResultFinder resultFinder = printResultFinder(document, random);

            //print game solutions
            printSudokuSolution(document, sudokuSolution);
            printCrosswordSolution(document, crossword, crossWordCreator);
            printAnagramSolution(document, anagramRows, chosenAnagrams);
            printCitationSolution(document, citationCreator);
            printPokerSolitaireSolution(document, pokerSolitaire, display);
            printDominoSolution(document, dominorCreator);
            printLongestWordSolution(document, longestWordFinder);
            printResultFinderSolution(document, resultFinder);

            //write document
            document.write(output);
            Console.println("Success. Output file: {0}", outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SudokuGrid printSudokuPage(XWPFDocument document, Random random) {
        //create the grid
        SudokuGrid solution = new SudokuGrid(random);
        solution.fillGrid();

        //create the sudoku games
        SudokuGrid grid = (SudokuGrid)solution.clone();
        grid.removeCells(15);

        //create word document
        SudokuCreator creator = new SudokuCreator();
        creator.printTitle(document, "Sudoku", ParagraphAlignment.CENTER, 24);
        creator.printGrid(document, grid, 800, 16);
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);

        return solution;
    }

    private static Crossword createCrossword(Locale display, Random random) {
        //generate crossword
        int rows = 14, cols = 20;
        Crossword crossword = Crossword.of(rows, cols);
        QuestionList questionList = new QuestionList();
        questionList.generateQuestions(display, random);
        questionList.shuffle(random);
        crossword.fill(questionList.getQuestions());
        crossword.print();
        return crossword;
    }

    private static CrossWordCreator printCrossword(XWPFDocument document, Crossword crossword) {
        //print crossword
        CrossWordCreator creator = new CrossWordCreator();
        creator.printTitle(document, "Mot croisé", ParagraphAlignment.CENTER, 24);
        boolean[][] states = crossword.getCellStates();
        int rows = states.length;
        int cols = states[0].length;

        List<String> horizontalDefinitions = crossword.getHorizontalDefinitions();
        List<String> verticalDefinitions = crossword.getVerticalDefinitions();
        creator.createGrid(document, rows, cols, states, 400, 10);
        creator.createAllDefinitions(document, horizontalDefinitions, verticalDefinitions);

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);

        return creator;
    }

    private static List<String[]> printAnagrams(XWPFDocument document, int anagramRows, Random random) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(24);
        run.setText("Trouver les anagrammes :");
        run.addCarriageReturn();

        AnagramCreator anagramCreator = new AnagramCreator(anagramRows);
        List<String[]> chosenAnagrams = anagramCreator.chooseAnagrams(random);
        anagramCreator.printAnagramGrid(document, chosenAnagrams, false);

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();

        return chosenAnagrams;
    }

    private static CitationCreator printCitation(XWPFDocument document, Random random) {
        //choose citations
        int cols = 16;
        CitationCreator citationCreator = new CitationCreator(cols);
        String[] citationAndAuthors = citationCreator.selectCitationAndAuthors(random);

        //shuffle letters
        String normalized = citationCreator.normalize(citationAndAuthors[0]);
        int rows = (int)Math.ceil(normalized.length() / (double)cols);
        char[][] letters = citationCreator.shuffleLetters(normalized, cols, rows, random);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(24);
        run.setText("Deviner la citation :");
        run.addCarriageReturn();
        citationCreator.fillGrid(document, normalized, cols, letters, false);

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Une fois la citation devinée, trouver qui en est l'auteur :");
        run.addCarriageReturn();

        citationCreator.fillAuthors(paragraph, citationAndAuthors, random);

        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);

        return citationCreator;
    }

    private static PokerSolitaire printPoker(XWPFDocument document, Random random) {
        PokerSolitaire pokerSolitaire = new PokerSolitaire(random);
        pokerSolitaire.printTitle(document);
        pokerSolitaire.printInstructions(document);
        pokerSolitaire.printCards(document);

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);

        return pokerSolitaire;
    }

    private static DominoCreator printDominos(XWPFDocument document, Random random) {
        DominoCreator creator = new DominoCreator();
        creator.printTitle(document);
        creator.generateMagicSquare(random);
        creator.printShuffledDominos(document);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
        return creator;
    }

    private static LongestWordFinder printLongestWord(XWPFDocument document, Random random) {
        LongestWordFinder finder = new LongestWordFinder();
        finder.pickLetters(random);

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
        run.setText("  " + finder.toUppercaseLetters());
        run.addCarriageReturn();
        run.addCarriageReturn();

        return finder;
    }

    private static ResultFinder printResultFinder(XWPFDocument document, Random random) {
        ResultFinder finder = new ResultFinder(random);

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
        run.setText("  " + finder.getNumberList());

        String text = MessageFormat.format("Résultat demandé : {0}", finder.getResult());
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText(text);
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);

        return finder;
    }

    private static void printPokerSolitaireSolution(XWPFDocument document, PokerSolitaire pokerSolitaire, Locale display) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(20);
        run.setText("Solution du carré de poker");
        run.addCarriageReturn();

        pokerSolitaire.printSolution(paragraph, display);

        String text = MessageFormat.format("Total : {0} points", Integer.toString(pokerSolitaire.getScore()));
        run = paragraph.createRun();
        run.setText(text);
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private static void printSudokuSolution(XWPFDocument document, SudokuGrid solution) {
        SudokuCreator creator = new SudokuCreator();
        creator.printTitle(document, "Solution du sudoku", ParagraphAlignment.LEFT, 20);
        SudokuGrid rotatedGrid = solution.rotateClockwise();
        creator.printGrid(document, rotatedGrid, 400, 8, STTextDirection.BT_LR);
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private static void printCrosswordSolution(XWPFDocument document, Crossword crossword, CrossWordCreator creator) {
        boolean[][] states = crossword.getCellStates();
        int rows = states.length;
        int cols = states[0].length;

        creator.printTitle(document, "Solution du mot croisé", ParagraphAlignment.LEFT, 20);
        XWPFTable grid = creator.createGrid(document, rows, cols, states, 300, 6);
        creator.fillGrid(grid, crossword, 8);

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private static void printAnagramSolution(XWPFDocument document, int rows, List<String[]> chosenAnagrams) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(20);
        run.setText("Solution des anagrammes");
        run.addCarriageReturn();

        AnagramCreator anagramCreator = new AnagramCreator(rows);
        anagramCreator.printAnagramGrid(document, chosenAnagrams, true);

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private static void printCitationSolution(XWPFDocument document, CitationCreator citationCreator) {
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

        paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    private static void printDominoSolution(XWPFDocument document, DominoCreator dominorCreator) {
        dominorCreator.printSolution(document);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private static void printLongestWordSolution(XWPFDocument document, LongestWordFinder longestWordFinder) {
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

    private static void printResultFinderSolution(XWPFDocument document, ResultFinder resultFinder) {
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
}
