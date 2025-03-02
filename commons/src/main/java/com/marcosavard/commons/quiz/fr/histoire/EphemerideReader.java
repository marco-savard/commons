package com.marcosavard.commons.quiz.fr.histoire;

import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EphemerideReader {
    private final BufferedReader bufferedReader;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
    private LocalDate currentDayMonth;
    private List<Event> allEvents =  new ArrayList<>();

    public EphemerideReader() {
        ResourceReader resourceReader = new ResourceReader(EphemerideReader.class, "ephemeride.txt", StandardCharsets.UTF_8);
        bufferedReader = new BufferedReader(resourceReader);
    }

    public List<HistoricalEvent> readAll() throws IOException {
        List<HistoricalEvent> events = new ArrayList<>();
        String line;

        do {
            line = bufferedReader.readLine();
            if (line != null) {
                HistoricalEvent event = readLine(line);

                if (event != null) {
                    events.add(event);
                }
            }
            
        } while (line != null);

        bufferedReader.close();
        return events;
    }

    private HistoricalEvent readLine(String line) {
        HistoricalEvent event = null;

        try {
            String date = line.replace("1er", "1").concat(" 2000");
            currentDayMonth = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            //skip line
        }

        try {
          int idx = line.indexOf(':');

          if (idx > 0) {
              String text = line.substring(0, idx).trim();
              int year = toYear(text);
              Month month = currentDayMonth.getMonth();
              int day = currentDayMonth.getDayOfMonth();
              LocalDate date = LocalDate.of(year, month, day);
              String desc = line.substring(idx+1).trim();
              event = new HistoricalEvent(date, desc);
          }
        } catch (NumberFormatException e) {
            //ignore
        }

        return event;
    }

    private int toYear(String text) {
        boolean isBC = false;

        if (text.contains("av. J.-C.")) {
            text = text.replace("av. J.-C.", "").trim();
            isBC = true;
        }

        int year = Integer.parseInt(text) * (isBC ? -1 : 1);
        return year;
    }

    public List<Event> findEvents(LocalDate today) {
        List<Event> events = allEvents.stream()
                .filter(e -> e.date.getMonth().equals(today.getMonth()) &&
                         e.date.getDayOfMonth() == today.getDayOfMonth())
                .toList();
        return events;
    }

    public static class Event {
        private String desc;
        private LocalDate date;

        private Event(String desc, LocalDate date) {
            this.desc = desc;
            this.date = date;
        }

        public static Event of(String desc, LocalDate date) {
            return new Event(desc, date);
        }

        @Override
        public String toString() {
            return date + " : " + desc;
        }
    }

}
