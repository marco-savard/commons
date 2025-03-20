package com.marcosavard.commons.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.Collections;

public class TimeZoneUtil {
    private static final Comparator<? super TimeZone> comparator = new TimeZoneComparator();

    public static String toString(List<TimeZone> timezones) {
        List<String> timeZoneIds = toStringList(timezones);
        return String.join(", ", timeZoneIds);
    }

    public static List<String> toStringList(List<TimeZone> timeZones) {
        return timeZones.stream().map(TimeZone::getID).toList();
    }

    public static Comparator<? super TimeZone> comparator() {
        return comparator;
    }


    public static List<String> extractRegions(List<TimeZone> timezones, Locale display) {
        List<String> regions = new ArrayList<>();

        for (TimeZone timezone : timezones) {
            String[] values =  timezone.getID().split("/");
            String region = toRegion(values[0], display);

            if (! regions.contains(region)) {
                regions.add(region);
            }
        }

        return regions;
    }

    private static String toRegion(String value, Locale display) {
        if (display.getLanguage().equals("fr")) {
            return toFrRegion(value);
        } else {
            return value;
        }
    }

    private static String toFrRegion(String value) {
        return switch(value) {
            case "America" -> "Amérique";
            case "Africa" -> "Afrique";
            case "Asia" -> "Asie";
            case "Antarctica" -> "Antarctique";
            case "Australia" -> "Australie";
            case "Pacific" -> "Pacifique";
            case "Atlantic" -> "Atlantique";
            case "Indian" -> "Océan indien";
            default -> value;
        };
    }

    public static TimeZone getMostEartern(List<TimeZone> timezones) {
        List<TimeZone> copy = new ArrayList<>(timezones);
        Comparator<TimeZone> comparator = new TimeZoneUtil.TimeZoneComparator();
        Collections.sort(copy, comparator.reversed());
        return copy.get(0);
    }


    public static TimeZone getMostWestern(List<TimeZone> timezones) {
        List<TimeZone> copy = new ArrayList<>(timezones);
        Comparator<TimeZone> comparator = new TimeZoneUtil.TimeZoneComparator();
        Collections.sort(copy, comparator);
        return copy.get(0);
    }

    public static String toOffset(TimeZone tz) {
        ZoneOffset offset = tz.toZoneId().getRules().getStandardOffset(Instant.now());
        String text = (offset.getTotalSeconds() == 0) ? "00:00" : offset.getId();
        return text;
    }


    public static class TimeZoneComparator implements Comparator<TimeZone> {
        @Override
        public int compare(TimeZone tz1, TimeZone tz2) {
            int off = tz1.getOffset(0);
            return Integer.compare(tz1.getRawOffset(), tz2.getRawOffset());
        }
    }
}
