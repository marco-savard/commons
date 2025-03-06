package com.marcosavard.commons.time.holiday;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

public enum NationalHoliday implements Holiday {
    CANADA_DAY(0x1F341, Month.JULY, 1),
    US_INDEPENDANCE_DAY(0x1F5FD, Month.JULY, 4);

    private final int codePoint;
    private Month month;
    private int dayOfMonth;

    NationalHoliday(int codePoint, Month month, int dayOfMonth) {
        this.codePoint = codePoint;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public LocalDate of(int year) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    @Override
    public int getCodePoint() {
        return codePoint;
    }

    public static Event[] getHolidaysOf(int year) {
        return Holiday.getHolidaysOf(Arrays.asList(NationalHoliday.values()), year);
    }

}
