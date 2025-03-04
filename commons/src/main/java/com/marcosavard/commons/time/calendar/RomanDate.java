package com.marcosavard.commons.time.calendar;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

//cf https://www.public.asu.edu/~checkma/today.html
public class RomanDate {
    private static final int[] NONES_DAYS = {5, 5, 7, 5, 7, 5, 7, 5, 5, 7, 5, 5};
    private static final int[] IDES_DAYS = {13, 13, 15, 13, 15, 13, 15, 13, 13, 15, 13, 13};

    private static final String[] DAY_NAMES = {"", "", "tertium", "quartum", "quintum", "sextum", "septimum", "octavum", //
      "nonum", "decimum", "undecimum", "duodecimum" };

    private static final String[] MONTH_NAMES = new String[] { "Ianuarius", "Februarius", "Martius", "Aprilis", "Maius", //
       "Juniius", "Quintilis", "Sextilis", "September", "October", "November", "December"};

    private int year;
    private Month month;
    private Period period;
    private int day;

    public static RomanDate ofGregorian(LocalDate gregorianDate) {
        return ofJulian(JulianCalendar.toJulianDate(gregorianDate));
    }

    public static RomanDate ofJulian(LocalDate localDate) {
        Month month = localDate.getMonth();
        boolean before1stMarch = month.getValue() < 3;
        int year = localDate.getYear() + (before1stMarch ? 753 : 754);

        int day = localDate.getDayOfMonth();
        int nonesDay = NONES_DAYS[month.getValue() - 1];
        int idesDay = IDES_DAYS[month.getValue() - 1];

        if (day == 1) {
            return new RomanDate(year, month, Period.KALENDS, 1);
        } else if (day <= nonesDay) {
            int daysToNones = nonesDay - day + 1;
            return new RomanDate(year, month, Period.NONES, daysToNones);
        } else if (day <= idesDay) {
            int daysToIdes = idesDay - day + 1;
            return new RomanDate(year, month, Period.IDES, daysToIdes);
        } else {
            // calculate days to the Kalends of the next month
            Month nextMonth = month.plus(1);
            int daysInCurrentMonth = month.length(localDate.isLeapYear());
            int daysToKalends = daysInCurrentMonth - day + 2;
            return new RomanDate(year, nextMonth, Period.KALENDS, daysToKalends);
        }
    }

    private RomanDate(int year, Month month, Period period, int day) {
        this.year = year;
        this.month = month;
        this.period = period;
        this.day = day;
    }

    @Override
    public String toString() {
        return getDisplayName(TextStyle.SHORT);
    }

    public String getDisplayName(TextStyle style) {
        if (style == TextStyle.NARROW) {
            return getNarrowName();
        } else if (style == TextStyle.SHORT){
            return getShortName();
        } else {
            return getFullName(style);
        }
    }

    private String getNarrowName() {
        return MessageFormat.format("{0}/{1}/{2}/{3}", day, period, month, Integer.toString(year));
    }

    private String getShortName() {
        String daysStr = period.toString().toLowerCase();
        String monthStr = month.toString().toLowerCase();
        String yearStr = Integer.toString(year);

        if (day == 1) {
            return MessageFormat.format("{0} of {1}, {2} AUC", daysStr, monthStr, yearStr);
        } else if (day == 2) {
            return MessageFormat.format("day before {0} of {1}, {2} AUC", daysStr, monthStr, yearStr);
        } else {
            return MessageFormat.format("{0} days to {1} of {2}, {3} AUC", day, daysStr, monthStr, yearStr);
        }
    }

    private String getFullName(TextStyle style) {
        boolean isShort = (style != TextStyle.FULL);
        String monthName = MONTH_NAMES[month.getValue()-1];
        monthName = isShort ? monthName.substring(0, 3).toUpperCase() : monthName;

        String ad = isShort ? "AD" : "ante diem";
        String prid = isShort ? "prid" : "pridie";
        String dayName = isShort ? RomanNumeral.of(day+1) : getDayName(day);
        String periodName = period.getDisplayName(style, day);
        String yearStr = RomanNumeral.of(year);
        String auc = isShort ? "AUC" : "ab urbe condita";

        if (day == 1) {
            return MessageFormat.format("{0} {1}, {2} {3}", periodName, monthName, yearStr, auc);
        } else if (day == 2) {
            return MessageFormat.format("{0} {1} {2}, {3} {4}", prid, periodName, monthName, yearStr, auc);
        } else {
            return MessageFormat.format("{0} {1} {2} {3}, {4} {5}", ad, dayName, periodName, monthName, yearStr, auc);
        }
    }

    public String getDisplayName(TextStyle style, Locale display) {
        if (display.getLanguage().equals("fr")) {
            return getDisplayNameFr(style, display);
        } else {
            return getDisplayNameEn(style, display);
        }
    }

    public String getDisplayNameEn(TextStyle style, Locale display) {
        boolean isShort = (style != TextStyle.FULL);
        String monthName = month.getDisplayName(style, display);
        String dayName = Integer.toString(day);
        String periodName = period.getDisplayName(style, display, day);
        String yearStr = RomanNumeral.of(year);

        if (isShort) {
            return String.join("/", dayName, periodName, monthName, yearStr);
        } else if (day == 1) {
            return MessageFormat.format("{0} of {1}, year {3}", periodName, monthName, yearStr);
        } else if (day == 2) {
            return MessageFormat.format("day before {0} of {1}, year {2}", periodName, monthName, yearStr);
        } else {
            return MessageFormat.format("{0} days before {1} of {2}, year {3}", dayName, periodName, monthName, yearStr);
        }
    }

    public String getDisplayNameFr(TextStyle style, Locale display) {
        boolean isShort = (style != TextStyle.FULL);
        String monthName = month.getDisplayName(style, display);
        String dayName = Integer.toString(day);
        String periodName = period.getDisplayName(style, display, day);
        String yearStr = RomanNumeral.of(year);

        if (isShort) {
            return String.join("/", dayName, periodName, monthName, yearStr);
        } else if (day == 1) {
            return MessageFormat.format("{0} de {1}, an {3}", periodName, monthName, yearStr);
        } else if (day == 2) {
            return MessageFormat.format("veille des {0} de {1}, an {2}", periodName, monthName, yearStr);
        } else {
            return MessageFormat.format("{0} jours avant les {1} de {2}, an {3}", dayName, periodName, monthName, yearStr);
        }
    }

    private String getDayName(int day) {
        return (day <= 12) ? DAY_NAMES[day] : String.join(" ", DAY_NAMES[day % 10], DAY_NAMES[10]);
    }

    //inner enum
    private enum Period {
        KALENDS("kalendis", "kalendas", "kal"),
        NONES("nonis", "nonas", "non"),
        IDES("idibus", "idus", "id");

        private String nominative, genitive, abbreviated;

        Period(String nominative, String genitive, String abbreviated) {
            this.nominative = nominative;
            this.genitive = genitive;
            this.abbreviated = abbreviated;
        }

        public String getDisplayName(TextStyle style, int day) {
            boolean isShort = (style != TextStyle.FULL);
            String displayName = isShort ? abbreviated : (day == 1) ? nominative : genitive;
            return displayName;
        }

        public String getDisplayName(TextStyle style, Locale display, int day) {
            boolean isShort = (style != TextStyle.FULL);

            if (isShort) {
                return abbreviated;
            } else if (display.getLanguage().equals("fr")) {
                return getDisplayNameFr();
            } else {
                return getDisplayNameEn();
            }
        }

        public String getDisplayNameEn() {
            String displayName = this.name().toLowerCase();
            return displayName;
        }

        public String getDisplayNameFr() {
            String displayName = this.equals(Period.KALENDS) ? "calendes" : this.name().toLowerCase();
            return displayName;
        }
    }



}
