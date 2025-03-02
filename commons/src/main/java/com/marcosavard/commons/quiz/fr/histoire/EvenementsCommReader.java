package com.marcosavard.commons.quiz.fr.histoire;

import com.marcosavard.commons.io.reader.CsvReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EvenementsCommReader {
    private List<String[]> headers;
    private CsvReader csvReader;

    public EvenementsCommReader() throws IOException {
        csvReader = new CsvReader(EvenementsCommReader.class, "evenementscomm1515-1763.csv", StandardCharsets.UTF_8);
        csvReader.withHeader(1, ',');
        csvReader.withSeparator(',');
        headers = csvReader.readHeaders();
    }

    public List<HistoricalEvent> readAll() throws IOException {
        List<String[]> rows = csvReader.readAll();
        List<HistoricalEvent> events = toEvents(rows);
        return events;
    }

    private List<HistoricalEvent> toEvents(List<String[]> rows) {
        List<HistoricalEvent> events = new ArrayList<>();

        for (String[] row : rows) {
            HistoricalEvent event = toEvent(row);
            if (event != null) {
                events.add(event);
            }
        }

        return events;
    }

    private static HistoricalEvent toEvent(String[] row) {
        HistoricalEvent event = null;

        try {
            if (! row[1].isEmpty()) {
                int year = Integer.parseInt(row[3]);
                int month = Integer.parseInt(row[2]);
                int day = Integer.parseInt(row[1]);
                LocalDate date = LocalDate.of(year, month, day);
                String desc = row[0];
                event = new HistoricalEvent(date, desc);
            }
        } catch (NumberFormatException e) {
            //ignore
        }

        return event;
    }
}
