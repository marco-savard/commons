package com.marcosavard.library.poi.word;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.util.PseudoRandom;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class AlmanacCreatorDemo {

    public static void main(String[] args) {
        //setting
        Locale display = Locale.FRENCH;
        LocalDate date = LocalDate.now().plusDays(0);
        Random random = new PseudoRandom(date.hashCode());

        //set output file
        String basename = "almanac-" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
        String outputFilePath = basename + ".docx";
        File outputFile = new File(outputFilePath);
        List<File> pages = new ArrayList<>();

        //generate individual files
        pages.add(generateSunEventPage(date, display));
        pages.add(generateCalendarPage(date, display));
        pages.add(generateMoonEventPage(date, display));
        pages.add(generateClimatePage(date, display));
       // pages.add(generateAntiquePage(date, random, display));
       // pages.add(generateMedievalPage(date, random, display));

        try (OutputStream output = new FileOutputStream(outputFile)) {
            //merge together
            WordMerger merger = new WordMerger();

            for (File page : pages) {
                merger.add( new FileInputStream(page));
            }

            merger.merge(output);

            //clean up
            for (File page : pages) {
                page.delete();
            }

            Console.println("Success. Output file: {0}", outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File generateCalendarPage(LocalDate date, Locale display) {
        String outputFilePath = "calendar.docx";
        File outputFile = new File(outputFilePath);

        try (OutputStream output = new FileOutputStream(outputFile)) {
            //create document
            XWPFDocument document = new XWPFDocument();
            CalendarCreator calendarCreator = new CalendarCreator();
            List<CalendarCreator.CalendarEvent> events = calendarCreator.findCalendarEvents(date, display);

            calendarCreator.printTitle(document, date, display);
            Map<Integer, Point> gridPositionByDay = calendarCreator.findGridPositions(date);
            XWPFTable table = calendarCreator.printCalendar(document, date, gridPositionByDay, display);
            calendarCreator.addCalendarEvents(table, date, gridPositionByDay, events);
            calendarCreator.addUpcomingEvents(document, date, events, display);

            //write document
            document.write(output);
            Console.println("Success. Output file: {0}", outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputFile;
    }

    private static File generateSunEventPage(LocalDate date, Locale display) {
        SunEventCreator sunEventCreator = new SunEventCreator(date, display);
        String outputFilePath = "sunEvents.docx";
        File outputFile = new File(outputFilePath);
        sunEventCreator.create(outputFile);
        return outputFile;
    }

    private static File generateMoonEventPage(LocalDate date, Locale display) {
        MoonEventCreator moonEventCreator = new MoonEventCreator(date, display);
        String outputFilePath = "moonEvents.docx";
        File outputFile = new File(outputFilePath);
        moonEventCreator.create(outputFile);
        return outputFile;
    }

    private static File generateClimatePage(LocalDate date, Locale display) {
        ClimateCreator climateCreator = new ClimateCreator(date, display);
        String outputFilePath = "climate.docx";
        File outputFile = new File(outputFilePath);
        climateCreator.create(outputFile);
        return outputFile;
    }

    private static File generateAntiquePage(LocalDate date, Random random, Locale display) {
        AntiquitePageCreator creator = new AntiquitePageCreator(date, random, display);
        String outputFilePath = "antiquite.docx";
        File outputFile = new File(outputFilePath);
        creator.create(outputFile);
        return outputFile;
    }

    private static File generateMedievalPage(LocalDate date, Random random, Locale display) {
        MedievalPageCreator creator = new MedievalPageCreator(date, random, display);
        String outputFilePath = "medieval.docx";
        File outputFile = new File(outputFilePath);
        creator.create(outputFile);
        return outputFile;
    }



}
