package com.marcosavard.commons.time.holiday;

import java.time.LocalDate;
import java.util.*;

public interface Holiday {

    int getCodePoint();

    LocalDate of(int year);

    String getName();

    static List<Event> getNextEvents(LocalDate startDate, int daysAfter) {
        List<Holiday> holidays = getAllHolidays();
        LocalDate endDate = startDate.plusDays(daysAfter);
        return getHolidays(holidays, startDate, endDate);
    }

    public static Event[] findHolidayEvents(LocalDate from, LocalDate to) {
        List<Event> eventList = new ArrayList<>();

        for (int i=from.getYear(); i<=to.getYear(); i++) {
            eventList.addAll(Arrays.asList(Holiday.getHolidaysOf(Arrays.asList(FloatingHoliday.values()), i)));
            eventList.addAll(Arrays.asList(Holiday.getHolidaysOf(Arrays.asList(NationalHoliday.values()), i)));
            eventList.addAll(Arrays.asList(Holiday.getHolidaysOf(Arrays.asList(ReligiousHoliday.values()), i)));
        }

        eventList = eventList.stream().filter(e -> Holiday.isBetween(e.date, from, to)).toList();

        return eventList.toArray(new Event[0]);
    }

    static List<Event> getHolidays(List<Holiday> holidays, LocalDate startDate, LocalDate endDate) {
        List<Event> eventList = new ArrayList<>();

        for (int i=startDate.getYear(); i<=endDate.getYear(); i++) {
            eventList.addAll(Arrays.asList(Holiday.getHolidaysOf(holidays, i)));
        }

        eventList = eventList.stream()
                .filter(e -> Holiday.isBetween(e.date, startDate, endDate))
                .sorted(Comparator.comparing(e -> e.date))
                .toList();
        return eventList;
    }

    static List<Holiday> getAllHolidays() {
        List<Holiday> holidays = new ArrayList<>();
        holidays.addAll(Arrays.asList(FloatingHoliday.values()));
        holidays.addAll(Arrays.asList(NationalHoliday.values()));
        holidays.addAll(Arrays.asList(ReligiousHoliday.values()));
        return holidays;
    }

    default String getDisplayName(Locale display) {
        String basename = Holiday.class.getName().replace('.', '/');
        ResourceBundle bundle = ResourceBundle.getBundle(basename, display);
        String property = getName();
        String displayName;

        try {
            displayName = bundle.getString(property);
        } catch (MissingResourceException ex) {
            displayName = property;
        }

        return displayName;
    }

    static boolean isBetween(LocalDate givenDate, LocalDate fromDate, LocalDate toDate) {
        return !(givenDate.isBefore(fromDate)) && !(givenDate.isAfter(toDate));
    }

    static Event[] getHolidaysOf(List<Holiday> holidays, int year) {
        List<Event> eventList = new ArrayList<>();

        for (Holiday holiday : holidays) {
            LocalDate date = holiday.of(year);
            Event event = new Event(holiday, date);
            eventList.add(event);
        }

        eventList = eventList.stream().sorted(Comparator.comparing(e -> e.date)).toList();

        return eventList.toArray(new Event[0]);
    }

    static void sort(List<Event> holidays) {
        Collections.sort(holidays, Comparator.comparing(e -> e.date));
    }



    public static class Event {
        Holiday holiday;
        LocalDate date;

        public Event(Holiday holiday, LocalDate date) {
            this.holiday = holiday;
            this.date = date;
        }

        public Holiday getHoliday() {
            return holiday;
        }

        public LocalDate getDate() {
            return date;
        }
    }

}
