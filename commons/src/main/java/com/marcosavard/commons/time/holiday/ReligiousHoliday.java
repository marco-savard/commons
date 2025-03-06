package com.marcosavard.commons.time.holiday;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public enum ReligiousHoliday implements Holiday {
    NEW_YEAR(0x1F4C6, Month.JANUARY, 1),
    VALENTINES_DAY(0x2764, Month.FEBRUARY, 14),
    SAINT_PATRICK(0x1F340, Month.MARCH, 17),
    MARDI_GRAS(0x1F389, -40),
    ASH_WEDNESDAY(0x271F, -39),
    PALM_SUNDAY(0x2E19, -7),
    GOOD_FRIDAY(0x271F, -2),
    EASTER(0x1F407, 0),
    ASCENSION(0x271F, 39),
    PENTECOST(0x271F, 49),
    TRINITY_SUNDAY(0x271F, 56),
    FEAST_OF_CORPUS_CHRISTI(0x271F, 60),
    SAINT_JOHN_BAPTIST(0x269C, Month.JUNE, 24),
    ALL_SAINTS_DAY(0x26EA, Month.NOVEMBER, 1),
    CHRISTMAS(0x1F384, Month.DECEMBER, 25);

    private final int codePoint;
    private int daysAfterEaster;
    private Month month;
    private int dayOfMonth;

    ReligiousHoliday(int codePoint, int daysAfterEaster) {
        this.codePoint = codePoint;
        this.daysAfterEaster = daysAfterEaster;
    }

    ReligiousHoliday(int codePoint, Month month, int dayOfMonth) {
        this.codePoint = codePoint;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public LocalDate of(int year) {
        if (month != null) {
            return LocalDate.of(year, month, dayOfMonth);
        } else {
            LocalDate easter = findEasterDate(year);
            return easter.plusDays(daysAfterEaster);
        }
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    @Override
    public int getCodePoint() {
        return codePoint;
    }

    public static Event[] findHolidayEvents(LocalDate from, LocalDate to) {
        List<Event> eventList = new ArrayList<>();

        for (int i=from.getYear(); i<=to.getYear(); i++) {
            eventList.addAll(Arrays.asList(getHolidaysOf(i)));
        }

        eventList = eventList.stream().filter(e -> Holiday.isBetween(e.date, from, to)).toList();

        return eventList.toArray(new Event[0]);
    }

    public static Event[] getHolidaysOf(int year) {
        return Holiday.getHolidaysOf(Arrays.asList(ReligiousHoliday.values()), year);
    }

    public static LocalDate findEasterDate(int year) {
        int golden = (year % 19) + 1; // E1: metonic cycle of 19 years
        int century = (year / 100) + 1; // E2: e.g. 1984 was in 20th C
        int x = (3 * century / 4) - 12; // E3: leap year correction
        int z = ((8 * century + 5) / 25) - 5; // E3: sync with moon's orbit
        int d = (5 * year / 4) - x - 10;
        int epact = (11 * golden + 20 + z - x) % 30; // E5: epact

        if ((epact == 25 && golden > 11) || epact == 24) epact++;

        int n = 44 - epact;
        n += 30 * (n < 21 ? 1 : 0); // E6
        n += 7 - ((d + n) % 7);

        Month month = (n <= 31) ? Month.MARCH : Month.APRIL;
        int dayOfMonth = (n <= 31) ? n : n - 31; // day of month

        LocalDate easter = LocalDate.of(year, month, dayOfMonth);
        return easter;
    }


}
