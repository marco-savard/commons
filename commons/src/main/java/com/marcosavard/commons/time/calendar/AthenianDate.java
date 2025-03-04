package com.marcosavard.commons.time.calendar;
import com.marcosavard.commons.debug.Console;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.Month;
import java.util.Locale;

public class AthenianDate {
    private static final int OLYMPIC_ERA = 776;
    private int year;
    private AthenianMonth month;
    private Decade decade;
    private int dateOfMonth;

    public enum AthenianMonth {
        Hekatombaion(30),
        Metageitnion(29),
        Boedromion(30),
        Pyanepsion(30),
        Maimakterion(29),
        Poseideon(29),
        Hadrianion(30), //intercalar
        Gamelion(30),
        Anthesterion(29),
        Elaphebolion(30),
        Mounichion(29),
        Thargelion(30),
        Skirophorion(29);

        private int days;

        AthenianMonth(int days) {
            this.days = days;
        }
    }

    public enum Decade {
        START("mênòs istamenou"),
        MIDDLE("mênòs mesoũntos"),
        END("mênòs phthínontos");
        String displayName;

        Decade(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Day {
        FIRST("noumênía"),
        SECOND("deutéra"),
        THIRD("trítê"),
        FOURTH("tetártê"),
        FIFTH("pémptê"),
        SIXTH("héktê"),
        SEVENTH("hebdómê"),
        EIGHTH("ogdóê"),
        NINTH("ennátê"),
        TENTH("dekátê"),
        ELEVENTH("prốtê"),
        TWELFTH("deutéra"),
        THIRTEENTH("trítê"),
        FOURTHEENTH("tetártê"),
        FIFTEENTH("pémptê"),
        SIXTEENTH("héktê"),
        SEVENTEENTH("hebdómê"),
        EIGHTEENTH("ogdóê"),
        NINTEENTH("ennátê"),
        TWENTITH("dekátê"),
        TWENTY_FIRST("dekátê"),
        TWENTY_SECOND("ennátê"),
        TWENTY_THIRD("ogdóê"),
        TWENTY_FOURTH("hebdómê"),
        TWENTY_FIFTH("héktê"),
        TWENTY_SIXTH("pémptê"),
        TWENTY_SEVENTH("tetártê"),
        TWENTY_EIGHTH("trítê"),
        TWENTY_NINTH("deutéra"),
        THIRTITH("énê kaì néa");

        private String greekName;

        Day(String greekName) {
            this.greekName = greekName;
        }
    }

    public static AthenianDate ofGregorian(LocalDate gregorianDate) {
        return ofJulian(JulianCalendar.toJulianDate(gregorianDate));
    }

    public static AthenianDate ofJulian(LocalDate date) {
        int year = date.getYear();
        LocalDate athYearStart = findNewMoonAfterSolstice(year);
        AthenianMonth month;

        // Si la date est avant le début de l'année athénienne, prendre l'année précédente
        if (date.isBefore(athYearStart)) {
            year -= 1;
            athYearStart = findNewMoonAfterSolstice(year);
        }
        // Déterminer si l'année a un mois intercalaire
        boolean hasExtraMonth = (year % 3 == 0);
        int totalMonths = hasExtraMonth ? 13 : 12;

        // Trouver le mois athénien
        int monthIndex = 0;
        month = AthenianMonth.values()[monthIndex];
        LocalDate monthStart = athYearStart;

        for (int i = 0; i < totalMonths; i++) {
            LocalDate nextMonthStart = monthStart.plusDays(month.days);

            if (date.isBefore(nextMonthStart)) {
                break;
            }

            // if month is Poseideon and no extra month, skip Hadrianion
            monthStart = nextMonthStart;
            monthIndex = (!hasExtraMonth && monthIndex == AthenianMonth.Poseideon.ordinal()) ? monthIndex + 2 : monthIndex + 1;
            month = AthenianMonth.values()[monthIndex];
        }

        // Calcul du jour du mois
        int dateOfMonth = (int) ChronoUnit.DAYS.between(monthStart, date) + 1;

        AthenianDate athenianDate = new AthenianDate(year+OLYMPIC_ERA, month, dateOfMonth);
        return athenianDate;
    }

    private AthenianDate(int year, AthenianMonth month, int dateOfMonth) {
        this.year = year;
        this.month = month;
        this.dateOfMonth = dateOfMonth;
        this.decade = (dateOfMonth <= 10) ? Decade.START : (dateOfMonth <= 20) ? Decade.MIDDLE : Decade.END;
    }

    @Override
    public String toString() {
        return getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        String decadeName = decade.getDisplayName();
        String yearText = Integer.toString(year);
        int idx = (month.days == 29) && (dateOfMonth >= 21) ? dateOfMonth : dateOfMonth - 1;
        String dayOfMonthText = Day.values()[idx].greekName;
        String language = (locale == null) ? null : locale.getLanguage();

        if (textStyle == TextStyle.FULL) {
            if ("en".equals(language)) {
                return getEnDisplayName();
            } else if ("fr".equals(language)) {
                return getFrDisplayName();
            } else {
                return MessageFormat.format("{0} {1} {2}, {3}", dayOfMonthText, decadeName, month, yearText);
            }
        } else {
            return MessageFormat.format("{0}/{1}/{2}", yearText, month, dateOfMonth);
        }
    }

    private String getEnDisplayName() {
        String dayOfMonthText = Integer.toString(dateOfMonth);
        String yearText = Integer.toString(year);
        return MessageFormat.format("day {0} of {1}, year {2}", dayOfMonthText, month, yearText);
    }

    private String getFrDisplayName() {
        String dayOfMonthText = (dateOfMonth == 1) ? "1er" : dateOfMonth + "e";
        String yearText = Integer.toString(year);
        return MessageFormat.format("{0} jour du mois de {1}, de l`an {2}", dayOfMonthText, month, yearText);
    }

    /**
     * Trouve la première nouvelle lune après le solstice d'été d'une année donnée.
     */
    private static LocalDate findNewMoonAfterSolstice(int year) {
        LocalDate solstice = LocalDate.of(year, Month.JUNE, 21);
        LocalDate newMoon = solstice.plusDays(1); // Départ fictif

        while (!isNewMoon(newMoon)) {
            newMoon = newMoon.plusDays(1);
        }
        return newMoon;
    }

    /**
     * Vérifie si une date donnée correspond approximativement à une nouvelle lune.
     * (Version simplifiée avec un cycle lunaire moyen de 29,53 jours)
     */
    private static boolean isNewMoon(LocalDate date) {
        LocalDate knownNewMoon = LocalDate.of(2000, Month.JANUARY, 6); // Référence
        long daysSince = ChronoUnit.DAYS.between(knownNewMoon, date);
        return (daysSince % 29 == 0);
    }

    public static void main(String[] args) {
        for (int i=1; i <= 365; i++) {
            LocalDate date = LocalDate.ofYearDay(2025, i);
            AthenianDate athenianDate = AthenianDate.ofJulian(date);
            Console.println("{0} -> {1}", date, athenianDate);
        }

        for (int i=1; i <= 365; i++) {
            LocalDate date = LocalDate.ofYearDay(2026, i);
            AthenianDate athenianDate = AthenianDate.ofJulian(date);
            Console.println("{0} -> {1}", date, athenianDate);
        }
    }
}
