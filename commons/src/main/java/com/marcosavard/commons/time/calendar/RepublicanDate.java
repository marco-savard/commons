package com.marcosavard.commons.time.calendar;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class RepublicanDate {
    private static final String[] MONTHS = {
            "Vendémiaire", "Brumaire", "Frimaire", "Nivôse", "Pluviôse",
            "Ventôse", "Germinal", "Floréal", "Prairial", "Messidor",
            "Thermidor", "Fructidor", "Sans-culottides"
    };

    private int republicanYear, month, day;

    public static RepublicanDate of(LocalDate localDate) {
        int year = localDate.getYear();
        LocalDate equinox = Season.automnalEquinoxOf(year).toLocalDate();

        //if the date is before the equinox, use the previous year's equinox
        boolean beforeEquinox = localDate.isBefore(equinox);
        equinox = beforeEquinox ? Season.automnalEquinoxOf(year - 1).toLocalDate() : equinox;
        year = beforeEquinox ? year - 1 : year;

        // calculate the number of days since the start of the Republican year
        long daysSinceEquinox = ChronoUnit.DAYS.between(equinox, localDate);

        // determine the year, month and day
        int republicanYear = year - 1792 + 1;
        int month = (int) (daysSinceEquinox / 30) + 1; // Months are 1-indexed
        int day = (int) (daysSinceEquinox % 30) + 1;

        RepublicanDate republicanDate = new RepublicanDate(republicanYear, month, day);
        return republicanDate;
    }

    public RepublicanDate(int republicanYear, int month, int day) {
        this.republicanYear = republicanYear;
        this.month = month;
        this.day = day;
    }

    public String getDisplayName(Locale display) {
        if (display.getLanguage().equals("fr")) {
            return getDisplayNameFr();
        } else {
            return getDisplayNameEn();
        }
    }

    private String getDisplayNameEn() {
        String month = MONTHS[this.month - 1];
        String year = RomanNumeral.of(republicanYear);
        return MessageFormat.format("{0} of {1}, year {2}", day, month, year);
    }

    private String getDisplayNameFr() {
        String month = MONTHS[this.month - 1];
        String year = RomanNumeral.of(republicanYear);
        return MessageFormat.format("{0} de {1}, an {2}", day, month, year);
    }

    @Override
    public String toString() {
        String month = MONTHS[this.month - 1];
        String year = Integer.toString(republicanYear);
        return MessageFormat.format("{0} of {1}, {2}", day, month, year);
    }



    private static boolean isLeap(int year) {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }


}
