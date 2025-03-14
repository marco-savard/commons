package com.marcosavard.library.poi.word;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.fr.dic.Glossary;
import com.marcosavard.commons.ling.fr.dic.Word;
import com.marcosavard.commons.quiz.fr.citations.CitationReader;
import com.marcosavard.commons.quiz.fr.histoire.DatesHistoireFrReader;
import com.marcosavard.commons.quiz.fr.histoire.DatesHistoireMondeReader;
import com.marcosavard.commons.quiz.fr.histoire.EphemerideReader;
import com.marcosavard.commons.quiz.fr.histoire.HistoricalEvent;
import com.marcosavard.commons.time.calendar.VikingDate;
import com.marcosavard.commons.util.PseudoRandom;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class MedievalPageCreator {
    private static final LocalDate FALL_OF_ROME = LocalDate.of(476, Month.SEPTEMBER, 4);
    private static final LocalDate FALL_OF_CONSTANTINOPLE = LocalDate.of(1453, Month.MAY, 29);

    private LocalDate date;
    private Random random;
    private Locale display;
    
    public MedievalPageCreator(LocalDate date, Random random, Locale display) {
        this.date = date;
        this.random = random;
        this.display = display;
    }

    public void create(File outputFile) {
        try (OutputStream output = new FileOutputStream(outputFile)) {
            XWPFDocument document = new XWPFDocument();
            printTitle(document);
            printVikingDate(document);
            printEphemerides(document);
            printMedievalGlossary(document);
            printCitations(document);

            //write document
            document.write(output);
            Console.println("Success. Output file: {0}", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printTitle(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
        run.setFontSize(24);
        run.setText("Moyen-Age");
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void printVikingDate(XWPFDocument document) {
        VikingDate vikingDate = VikingDate.ofLocalDate(date);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Aujourd'hui selon le calendrier viking : ");
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("  " + vikingDate.getDisplayName(TextStyle.FULL));

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void printEphemerides(XWPFDocument document) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", display);
        String title = MessageFormat.format("Évènements s`étant produit un {0} ou peu après", date.format(formatter));
        List<HistoricalEvent> events = findComingAntiqueEvents(date);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText(title);
        run.addCarriageReturn();
        run = paragraph.createRun();

        for (HistoricalEvent event : events) {
            printEphemeride(document, event, display);
        }

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private List<HistoricalEvent> findComingAntiqueEvents(LocalDate date) {
        List<HistoricalEvent> comingEvents;

        try {
            DatesHistoireFrReader reader = new DatesHistoireFrReader();
            List<HistoricalEvent> events = reader.readAll();

            DatesHistoireMondeReader reader2 = new DatesHistoireMondeReader();
            events.addAll(reader2.readAll());

            EphemerideReader reader3 = new EphemerideReader();
            events.addAll(reader3.readAll());

            events = events.stream()
                    .filter(e -> e.getDate().isAfter(FALL_OF_ROME) && e.getDate().isBefore(FALL_OF_CONSTANTINOPLE))
                    .toList();

            //obtient les evenements les plus pres du jour
            int doy = date.getDayOfYear();
            comingEvents = events.stream()
                    .sorted(Comparator.comparing(e -> Math.floorMod(e.getDate().getDayOfYear() - doy, 365)))
                    .toList();

            comingEvents = comingEvents.subList(0, Math.min(5, comingEvents.size()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        return comingEvents;
    }

    private void printEphemeride(XWPFDocument document, HistoricalEvent event, Locale display) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM uuu", display);
        LocalDate date = event.getDate();
        String formatted = date.format(formatter);
        String desc = event.getDescription();
        String text = MessageFormat.format("  {0} : {1}", formatted, desc);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.addCarriageReturn();
    }

    private void printMedievalGlossary(XWPFDocument document) throws IOException {
        Glossary glossary = new Glossary();
        glossary.addCategory(Glossary.Category.VIKING);
        glossary.addCategory(Glossary.Category.MIDDLE_AGE);
        List<Word> wordList = glossary.getWordList();
        List<Word> shuffled = new ArrayList<>();
        shuffled.addAll(wordList);
        Collections.shuffle(shuffled, random);
        shuffled = shuffled.subList(0, 5);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Glossaire médiéval : ");

        for (Word word : shuffled) {
            word.getText();
            String definitions = word.getDefinitions().get(0);

            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setBold(true);
            run.setText("  " + word.getText() + " : ");

            run = paragraph.createRun();
            run.setText(" " + definitions);
        }

        paragraph = document.createParagraph();
        run = paragraph.createRun();
    }

    private void printCitations(XWPFDocument document) {
        CitationReader reader = new CitationReader();
        Map<String, CitationReader.Author> citationsByAuthor = reader.readAll();
        List<String> allAuthors = new ArrayList<>(citationsByAuthor.keySet());

        allAuthors = allAuthors.stream().filter(a ->
                citationsByAuthor.get(a).getYears()[0].getValue() > FALL_OF_ROME.getYear() &&
                        citationsByAuthor.get(a).getYears()[0].getValue() < FALL_OF_CONSTANTINOPLE.getYear())
                .toList();

        List<String> authors = new ArrayList<>();
        authors.addAll(allAuthors);
        Collections.shuffle(authors, random);
        authors = authors.subList(0, 3);
        authors = authors.stream().sorted(Comparator.comparing(a -> citationsByAuthor.get(a).getYears()[0].getValue())).toList();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Citations :");
        run.addCarriageReturn();
        run = paragraph.createRun();

        for (String name : authors) {
            CitationReader.Author author = citationsByAuthor.get(name);
            List<String> citations = author.getCitations();
            int idx = random.nextInt(citations.size());
            String citation = citations.get(idx);
            printCitation(document, author, citation);
        }

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void printCitation(XWPFDocument document, CitationReader.Author author, String citation) {
        String y1 = Integer.toString(author.getYears()[0].getValue());
        String y2 = Integer.toString(author.getYears()[1].getValue());
        String authorLine = MessageFormat.format("{0} ({1}-{2})", author.getName(), y1, y2);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("  " + citation);
        run.addCarriageReturn();
        run.setText("    " + authorLine);
        run.addCarriageReturn();
        run.addCarriageReturn();
    }
}
