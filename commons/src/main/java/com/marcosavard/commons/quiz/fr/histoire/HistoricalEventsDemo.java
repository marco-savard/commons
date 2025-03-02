package com.marcosavard.commons.quiz.fr.histoire;

import com.marcosavard.commons.debug.Console;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HistoricalEventsDemo {
    public static void main(String[] args) throws IOException {
        Locale display = Locale.FRENCH;
        DatesHistoireFrReader reader = new DatesHistoireFrReader();
        List<HistoricalEvent> events = reader.readAll();

        DatesHistoireMondeReader reader2 = new DatesHistoireMondeReader();
        events.addAll(reader2.readAll());

        EphemerideReader reader3 = new EphemerideReader();
        events.addAll(reader3.readAll());

        LocalDate today = LocalDate.now();
        listEphemerides(events, today);
    }

    private static void listEphemerides(List<HistoricalEvent> events, LocalDate date) throws IOException {
        LocalDate fallOfRome = LocalDate.of(476, Month.SEPTEMBER, 4);
        events = events.stream()
                .filter(e -> e.getDate().isBefore(fallOfRome))
                .toList();

        //obtient les evenements les plus pres du jour
        int doy = date.getDayOfYear();
        List<HistoricalEvent> comingEvents = events.stream()
                .sorted(Comparator.comparing(e -> Math.floorMod(e.getDate().getDayOfYear() - doy, 365)))
                .toList();

        comingEvents = comingEvents.subList(0, Math.min(15, comingEvents.size()));

        for (HistoricalEvent event : comingEvents) {
            Console.println(event);
        }
    }

}
