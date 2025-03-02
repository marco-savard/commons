package com.marcosavard.commons.quiz.fr.histoire;

import java.time.LocalDate;
import java.util.Comparator;

public class HistoricalEvent {
    public static Comparator<HistoricalEvent> historicalEventComparator = new HistoricalEventComparator();
    private LocalDate date;
    private String desc;

    public HistoricalEvent(LocalDate date, String desc) {
        this.date = date;
        this.desc = desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return desc;
    }

    private static class HistoricalEventComparator implements Comparator<HistoricalEvent> {

        @Override
        public int compare(HistoricalEvent o1, HistoricalEvent o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }
}
