package com.marcosavard.commons.quiz.fr.histoire;

import com.marcosavard.commons.io.reader.CsvReader;
import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatesHistoireFrReader {
    private final CsvReader csvReader;
    private List<String[]> headers;

    public DatesHistoireFrReader() throws IOException {
        csvReader = new CsvReader(EvenementsCommReader.class, "dates.histoire.fr.txt", StandardCharsets.UTF_8);
        csvReader.withHeader(1, ',');
        csvReader.withSeparator(':');
        headers = csvReader.readHeaders();
    }

    public List<HistoricalEvent> readAll() throws IOException {
        List<HistoricalEvent> events = new ArrayList<>();
        List<String[]> lines = csvReader.readAll();

        for (String[] line : lines) {
            HistoricalEvent event = readLine(line);
            events.add(event);
        }

        return events;
    }

    private HistoricalEvent readLine(String[] line) {
        LocalDate date = readDate(line[0]);
        String desc = line[1];
        HistoricalEvent event = new HistoricalEvent(date, desc);
        return event;
    }

    private LocalDate readDate(String text) {
        int idx1 = text.indexOf('(');
        int idx2 = text.indexOf(')');
        text = text.substring(0, idx1) + text.substring(idx1+1, idx2);
        text = text.replace("1er", "1");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy d MMMM", Locale.FRENCH);
        LocalDate date = LocalDate.parse(text, formatter);
        return date;
    }

    public static class Entry {
        private LocalDate date;
        private String desc;

        public static Entry of(String[] line) {
            return new Entry(line);
        }

        private Entry(String[] line) {
            String time = line[0];
            int idx1 = time.indexOf('(');
            int idx2 = time.indexOf(')');
            String text = time.substring(0, idx1) + time.substring(idx1+1, idx2);
            text = text.replace("1er", "1");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy d MMMM", Locale.FRENCH);
            date = LocalDate.parse(text, formatter);
            desc = line[1];
        }

        @Override
        public String toString() {
            return date + " : " + desc;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}
