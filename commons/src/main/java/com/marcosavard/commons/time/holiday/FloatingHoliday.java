package com.marcosavard.commons.time.holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum FloatingHoliday implements Holiday {
    FAMILY_DAY(0x1F46A, Month.FEBRUARY, DayOfWeek.MONDAY, 3),
    DAYLIGHT_SAVING_ON(0x1F4A1, Month.MARCH, DayOfWeek.SUNDAY, 2),
    MOTHERS_DAY(0x1F469, Month.MAY, DayOfWeek.SUNDAY, 2),
    VICTORIA_DAY(0x2655, Month.MAY, DayOfWeek.MONDAY, 3),
    FATHERS_DAY(0x1F468, Month.JUNE, DayOfWeek.SUNDAY, 3),
    CIVIC_DAY(0x1F389, Month.AUGUST, DayOfWeek.MONDAY, 1),
    LABOR_DAY(0x2692, Month.SEPTEMBER, DayOfWeek.MONDAY, 1),
    CANADIAN_THANKSGIVING(0x1F341, Month.OCTOBER, DayOfWeek.MONDAY, 2),
    DAYLIGHT_SAVING_OFF(0x1F4A1, Month.NOVEMBER, DayOfWeek.SUNDAY, 1),
    AMERICAN_THANKSGIVING(0x1F5FD, Month.NOVEMBER, DayOfWeek.THURSDAY, 4);

    private final int codePoint;
    private Month month;
    private DayOfWeek dayOfWeek;
    private int weekOfMonth;

    FloatingHoliday(int codePoint, Month month, DayOfWeek dayOfWeek, int weekOfMonth) {
        this.codePoint = codePoint;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.weekOfMonth = weekOfMonth;
    }

    @Override
    public int getCodePoint() {
        return codePoint;
    }

    public LocalDate of(int year) {
        int dayOfMonth = 1 + (weekOfMonth - 1) * 7;
        LocalDate date =
                LocalDate.of(year, month, dayOfMonth) //
                        .with(TemporalAdjusters.nextOrSame(dayOfWeek));
        return date;
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public String getDisplayName(Locale display) {
        String basename = Holiday.class.getName().replace('.', '/');
        ResourceBundle bundle = ResourceBundle.getBundle(basename, display);
        String property = this.name().toLowerCase();
        String displayName;

        try {
            displayName = bundle.getString(this.name().toLowerCase());
        } catch (MissingResourceException ex) {
            displayName = property;
        }

        return displayName;
    }

    public static Event[] getHolidaysOf(int year) {
        return Holiday.getHolidaysOf(Arrays.asList(FloatingHoliday.values()), year);
    }

}
