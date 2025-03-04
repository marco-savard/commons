package com.marcosavard.commons.time.calendar;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//cf results : https://en.wikipedia.org/wiki/Equinox
//https://github.com/pavolgaj/AstroAlgorithms4Python/blob/master/equinox.py
public class Season {

    public enum Event {
        VERNAL_EQUINOX(0x2740),
        SUMMER_SOLSTICE(0x26F1),
        AUTOMNAL_EQUINOX(0x1F383),
        WINTER_SOLSTICE(0x26C4);
        private int codePoint;

        Event(int codePoint) {
            this.codePoint = codePoint;
        }

        public int getCodePoint() {
            return codePoint;
        }

        public String getDisplayName(Locale display) {
            if (display.getLanguage().equals("fr")) {
                return getDisplayNameFr();
            } else {
                return this.name().toLowerCase().replace('_', ' ');
            }
        }

        private String getDisplayNameFr() {
            return switch (this) {
                case VERNAL_EQUINOX -> "équinoxe du printemps";
                case SUMMER_SOLSTICE -> "solstice d'été";
                case AUTOMNAL_EQUINOX -> "équinoxe d'automne";
                case WINTER_SOLSTICE -> "solstice d'hiver";
            };
        }
    }

    private static final int[] A = new int[] {485,203,199,182,156,136,77,74,70,58,52,50,45,44,29,18,17,16,14,12,12,12,9,8};
    private static final double[] B = new double[] {324.96,337.23,342.08,27.85,73.14,171.52,222.54,296.72,243.58,119.81,297.17,21.02,247.54,325.15,60.93,155.12,288.79,198.04,199.76,95.39,287.11,320.81,227.73,15.45};
    private static final double[] C = new double[] {1934.136,32964.467,20.186,445267.112,45036.886,22518.443,65928.934,3034.906,9037.513,33718.147,150.678,2281.226,29929.562,31555.956,4443.417,67555.328,4562.452,62894.029,31436.921,
            14577.848,31931.756,34777.259,1222.114,16859.074};

    public static List<EventOccurence> findNextEvents(LocalDate date, int count) {
        int year = date.getYear();
        List<EventOccurence> occurences = new ArrayList<>();
        occurences.add(new EventOccurence(Event.VERNAL_EQUINOX, vernalEquinoxOf(year)));
        occurences.add(new EventOccurence(Event.SUMMER_SOLSTICE, summerSolsticeOf(year)));
        occurences.add(new EventOccurence(Event.AUTOMNAL_EQUINOX, automnalEquinoxOf(year)));
        occurences.add(new EventOccurence(Event.WINTER_SOLSTICE, winterSolsticeOf(year)));
        occurences.add(new EventOccurence(Event.VERNAL_EQUINOX, vernalEquinoxOf(year+1)));

        occurences = occurences.stream().filter(o -> o.getDateTime().toLocalDate().isAfter(date)).sorted().toList();
        return (occurences.size() > count) ? occurences.subList(0, count) : occurences;
    }

    public static EventOccurence findNextEvent(LocalDate date) {
        return findNextEvents(date, 1).get(0);
    }

    public static LocalDateTime findNextOccurrence(Event event, int year) {
        return switch (event) {
            case VERNAL_EQUINOX -> vernalEquinoxOf(year);
            case SUMMER_SOLSTICE -> summerSolsticeOf(year);
            case AUTOMNAL_EQUINOX -> automnalEquinoxOf(year);
            case WINTER_SOLSTICE -> winterSolsticeOf(year);
        };
    }

    public static LocalDateTime vernalEquinoxOf(int year) {
        double y = (year-2000) / 1000.0;
        double y2 = y * y;
        double y3 = y2 * y;
        double y4 = y2 * y2;
        double jd = 2451623.80984 + 365242.37404*y + 0.05169*y2 - 0.00411*y3 - 0.00057*y4;
        return toLocalDateTime(correct(jd));
    }

    public static LocalDateTime summerSolsticeOf(int year) {
        double y = (year-2000) / 1000.0;
        double y2 = y * y;
        double y3 = y2 * y;
        double y4 = y2 * y2;
        double jd = 2451716.56767 + 365241.62603*y + 0.00325*y2 + 0.00888*y3 - 0.00030*y4;
        return toLocalDateTime(correct(jd));
    }

    public static LocalDateTime automnalEquinoxOf(int year) {
        double y = (year-2000) / 1000.0;
        double y2 = y * y;
        double y3 = y2 * y;
        double y4 = y2 * y2;
        double jd = 2451810.21715 + 365242.01767*y - 0.11575*y2 + 0.00337*y3 + 0.00078*y4;
        return toLocalDateTime(correct(jd));
    }

    public static LocalDateTime winterSolsticeOf(int year) {
        double y = (year-2000) / 1000.0;
        double y2 = y * y;
        double y3 = y2 * y;
        double y4 = y2 * y2;
        double jd = 2451900.05952 + 365242.74049*y - 0.06223*y2 - 0.00823*y3 + 0.00032*y4;
        return toLocalDateTime(correct(jd));
    }

    private static double correct(double jd) {
        double t = (jd-2451545) / 36525.0;
        double w = Math.toRadians(35999.373 * t - 2.47);
        double dl = 1 + 0.0334 * Math.cos(w) + 0.0007 * Math.cos(2*w);
        double s = 0;

        for (int i=0; i< A.length; i++) {
            s += A[i] * Math.cos(Math.toRadians(B[i] + C[i] * t));
        }

        return jd + (0.00001 * s / dl);
    }

    private static LocalDateTime toLocalDateTime(double jd) {
        long days = (long)(jd + 0.5);
        double fractionOfDay = (jd + 0.5) - days;
        int hour = (int)(fractionOfDay * 24.0);
        int min = (int)((fractionOfDay * 24.0 - hour) * 60.0);
        LocalDate localDate = toLocalDate(days);
        LocalTime localTime = LocalTime.of(hour, min);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return  localDateTime;
    }

    private static LocalDate toLocalDate(long jd) {
        return LocalDate.MIN.with(JulianFields.JULIAN_DAY, jd);
    }

    private static final double DAY_IN_MS = Duration.ofDays(1).toMillis();

    private static double toJulianDay(LocalDateTime date) {
        LocalTime timeOfDay = date.toLocalTime();
        double jd = date.getLong(JulianFields.JULIAN_DAY);
        jd += timeOfDay.get(ChronoField.MILLI_OF_DAY) / DAY_IN_MS - 0.5;
        return jd;
    }

    public static class EventOccurence implements Comparable<EventOccurence> {
        private Event event;
        private LocalDateTime dateTime;

        public EventOccurence(Event event, LocalDateTime dateTime) {
            this.event = event;
            this.dateTime = dateTime;
        }

        public Event getSeasonEvent() {
            return event;
        }

        @Override
        public int compareTo(EventOccurence other) {
            return toJulianDay() - other.toJulianDay();
        }

        public int toJulianDay() {
            return (int)Season.toJulianDay(dateTime);
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}
