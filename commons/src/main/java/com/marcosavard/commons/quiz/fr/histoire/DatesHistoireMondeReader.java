package com.marcosavard.commons.quiz.fr.histoire;

import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DatesHistoireMondeReader {
    private final BufferedReader bufferedReader;

    public DatesHistoireMondeReader() {
        ResourceReader resourceReader = new ResourceReader(DatesHistoireMondeReader.class, "dates.histoire.monde.txt", StandardCharsets.UTF_8);
        bufferedReader = new BufferedReader(resourceReader);
    }

    public List<HistoricalEvent> readAll() throws IOException {
        List<HistoricalEvent> events = new ArrayList<>();
        String line;

        do {
            line = bufferedReader.readLine();

            if ((line != null) && (! line.isBlank())) {
                HistoricalEvent event = readLine(line);
                events.add(event);
            }
        } while (line != null);

        Collections.sort(events, HistoricalEvent.historicalEventComparator);

        return events;
    }

    private HistoricalEvent readLine(String line) {
        int idx =  line.indexOf(" – ");
        String dateText = line.substring(0, idx);
        String desc = line.substring(idx+3);

        LocalDate date = readDate(dateText);
        HistoricalEvent event = new HistoricalEvent(date, desc);
        return event;
    }

    private LocalDate readDate(String text) {
        int idx = text.indexOf("av. J.-C");
        text = (idx == -1) ? text : text.substring(0, idx-1);
        boolean beforeBC = (idx == -1) ? false : true;

        idx = text.indexOf("apr. J.-C");
        text = (idx == -1) ? text : text.substring(0, idx-1);

        text = text.replace("1er", "1");
        text = normalize(text, beforeBC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM uuuu", Locale.FRENCH);
        LocalDate date = LocalDate.parse(text, formatter);
        return date;
    }

    private String normalize(String text, boolean beforeBC) {
        int idx = text.lastIndexOf(' ');
        String year = text.substring(idx+1);
        year = (year.length() == 1) ? "000" + year : year;
        year = (year.length() == 2) ? "00" + year : year;
        year = (year.length() == 3) ? "0" + year : year;
        year = beforeBC ? "-" + year : year;
        String normalized = text.substring(0, idx+1) + year;
        return normalized;
    }
}
