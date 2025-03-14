package com.marcosavard.library.poi.word;

import com.marcosavard.commons.astro.planet.AstroObject;
import com.marcosavard.commons.astro.zodiac.ZodiacSign;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.fr.dic.Glossary;
import com.marcosavard.commons.ling.fr.dic.Word;
import com.marcosavard.commons.quiz.fr.citations.CitationReader;
import com.marcosavard.commons.quiz.fr.histoire.DatesHistoireFrReader;
import com.marcosavard.commons.quiz.fr.histoire.DatesHistoireMondeReader;
import com.marcosavard.commons.quiz.fr.histoire.EphemerideReader;
import com.marcosavard.commons.quiz.fr.histoire.HistoricalEvent;
import com.marcosavard.commons.time.DayOfWeekUtil;
import com.marcosavard.commons.time.calendar.AthenianDate;
import com.marcosavard.commons.time.calendar.RomanDate;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class AntiquitePageCreator {
    private LocalDate date;
    private Random random;
    private Locale display;

    public AntiquitePageCreator(LocalDate date,  Random random, Locale display) {
        this.date = date;
        this.random = random;
        this.display = display;
    }

    public void create(File outputFile) {
        try (OutputStream output = new FileOutputStream(outputFile)) {
            XWPFDocument document = new XWPFDocument();
            printTitle(document);
            printChart(document);
            printRomanDate(document);
            printAtticDate(document);
            printEphemerides(document);
            printAntiqueGlossary(document);
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
        run.setText("Athènes et Rome");
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void printChart(XWPFDocument document) {
        XWPFTable table = document.createTable(1, 2);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(6000);

        XWPFTableCell cell0 = table.getRow(0).getCell(0);
        XWPFTableCell cell1 = table.getRow(0).getCell(1);

        printDivinityOfTheDay(cell0, date, display);
        printZodiacSign(cell1, date, display);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void printDivinityOfTheDay(XWPFTableCell cell, LocalDate date, Locale display) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        AstroObject astroObject = AstroObject.ofDayOfWeek(dayOfWeek);
        String symbol = astroObject.getSymbol();

        String latin = DayOfWeekUtil.getDisplayName(dayOfWeek, TextStyle.FULL_STANDALONE, Locale.forLanguageTag("la"));
        String astroName = "de " + astroObject.getDisplayName(display);
        astroName = astroName.replace("de le", "du");
        String text = MessageFormat.format("Le jour {0}", astroName);

        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Divinité du jour :");
        run.addCarriageReturn();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setFontSize(40);
        run.setText(symbol);
        run.addCarriageReturn();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText(latin);
        run.addCarriageReturn();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText(text);
    }

    private void printZodiacSign(XWPFTableCell cell, LocalDate date, Locale display) {
        ZodiacSign sign = ZodiacSign.of(date.getMonth(), date.getDayOfMonth());
        ZodiacSign nextSign = sign.getNext();
        String name = sign.getDisplayName(display);
        String symbol = Character.toString(sign.getSymbol());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", display);
        LocalDate start = LocalDate.of(date.getYear(), sign.getMonth(), sign.getDayOfMonth());
        LocalDate end = LocalDate.of(date.getYear(), nextSign.getMonth(), nextSign.getDayOfMonth());
        end = end.minusDays(1);
        String s1 = start.format(formatter);
        String s2 = end.format(formatter);
        String text = MessageFormat.format("du {0} au {1}", s1, s2);

        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Signe du zodiaque :");
        run.addCarriageReturn();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setFontSize(40);
        run.setText(symbol);
        run.addCarriageReturn();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText(name);
        run.addCarriageReturn();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText(text);
    }

    private void printRomanDate(XWPFDocument document) {
        RomanDate romanDate = RomanDate.ofJulian(date);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Aujourd'hui selon le calendrier romain : ");
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("  " + romanDate.getDisplayName(TextStyle.FULL));
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("  " + romanDate.getDisplayName(TextStyle.FULL, display));
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private void printAtticDate(XWPFDocument document) {
        AthenianDate atticDate = AthenianDate.ofJulian(date);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Aujourd'hui selon le calendrier athénien : ");
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("  " + atticDate.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("he")));
        run.addCarriageReturn();

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("  " + atticDate.getDisplayName(TextStyle.FULL, display));
        run.addCarriageReturn();

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

            LocalDate fallOfRome = LocalDate.of(476, Month.SEPTEMBER, 4);
            events = events.stream()
                    .filter(e -> e.getDate().isBefore(fallOfRome))
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

    private void printAntiqueGlossary(XWPFDocument document) throws IOException {
        Glossary glossary = new Glossary();
        glossary.addCategory(Glossary.Category.GREECE);
        glossary.addCategory(Glossary.Category.ROME);
        glossary.addCategory(Glossary.Category.GAUL);
        List<Word> wordList = glossary.getWordList();
        List<Word> shuffled = new ArrayList<>();
        shuffled.addAll(wordList);
        Collections.shuffle(shuffled, random);
        shuffled = shuffled.subList(0, 5);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Glossaire antique: ");

        for (Word word : shuffled) {
            String definition = findDefinition(word.getDefinitions(), "antiqu");

            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setBold(true);
            run.setText("  " + word.getText() + " : ");

            run = paragraph.createRun();
            run.setText(" " + definition);
        }

        paragraph = document.createParagraph();
        run = paragraph.createRun();
    }

    private String findDefinition(List<String> definitions, String keyword) {
        String definition = definitions.stream().filter(d -> d.toLowerCase().contains(keyword)).findFirst().orElse(null);
        definition = (definition == null) ? definitions.get(0) : definition;
        return definition;
    }

    private void printCitations(XWPFDocument document) {
        CitationReader reader = new CitationReader();
        Map<String, CitationReader.Author> citationsByAuthor = reader.readAll();
        List<String> allAuthors = new ArrayList<>(citationsByAuthor.keySet());

        LocalDate fallOfRome = LocalDate.of(476, Month.SEPTEMBER, 4);
        allAuthors = allAuthors.stream().filter(a -> citationsByAuthor.get(a).getYears()[0].getValue() < 476).toList();

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
