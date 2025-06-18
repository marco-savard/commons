package com.marcosavard.docmodule;

import com.marcosavard.common.astro.sun.SunPosition;
import com.marcosavard.common.debug.Console;
import com.marcosavard.common.geog.GeoLocation;
import com.marcosavard.docmodule.template.SunEventTemplateReader;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class SunEventCreator {
    private static final String[] yesterdayTags = new String[] {"gainOrLossSinceYesterday", "minutesSinceYesterday"};
    private static final String[] lastWeekTags = new String[] {"gainOrLossSinceLastWeek", "minutesSinceLastWeek"};

    private final SunEventTemplateReader templateReader = new SunEventTemplateReader();
    private final ZoneId zoneId = ZoneId.of("America/Montreal");
    private final LocalDate date;
    private final Locale display;
    private final SunPosition sunPosition;

    public SunEventCreator(LocalDate date, Locale display) {
        this.date = date;
        this.display = display;
        ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneOffset.UTC);
        sunPosition = SunPosition.at(moment);
    }

    public void create(File outputFile) {
        GeoLocation quebec = GeoLocation.of(46.8131, -71.2075);
        GeoLocation gaspe = GeoLocation.of(48.8316, -64.4869);
        GeoLocation rimouski = GeoLocation.of(48.4390, -68.5350);
        GeoLocation montreal = GeoLocation.of(45.5019, -73.56745);
        GeoLocation ottawa = GeoLocation.of(45.4201, -75.7003);
        
        try (InputStream input = templateReader.getInput()) {
            try (OutputStream output = new FileOutputStream(outputFile)) {
                WordStringReplacer replacer = new WordStringReplacer(input, output);
                printCurrentDate(replacer, date, display);
                printSunEvents(replacer, sunPosition, quebec, "0");
                printSunEvents(replacer, sunPosition, gaspe, "1");
                printSunEvents(replacer, sunPosition, rimouski, "2");
                printSunEvents(replacer, sunPosition, montreal, "3");
                printSunEvents(replacer, sunPosition, ottawa, "4");

                printDayLength(replacer, sunPosition, quebec);
                printDayLengthGainLoss(replacer, sunPosition, quebec, date.minusDays(1), yesterdayTags);
                printDayLengthGainLoss(replacer, sunPosition, quebec, date.minusDays(7), lastWeekTags);
                replacer.close();
                Console.println("String replacement completed. Output file: {0}", outputFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printCurrentDate(WordStringReplacer replacer, LocalDate date, Locale display) {
        //format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Le' eeee d MMMM yyyy", display);
        replacer.replaceStrings("-currentDate-", date.format(formatter));
    }

    private void printSunEvents(WordStringReplacer replacer, SunPosition position, GeoLocation location, String suffix) {
        //compute sun events
        ZonedDateTime[] sunriseSunset = position.findSunriseSunsetAt(location);

        //compute local times
        LocalDateTime localSunrise = LocalDateTime.ofInstant(sunriseSunset[0].toInstant(), zoneId);
        LocalDateTime localNoon = LocalDateTime.ofInstant(sunriseSunset[1].toInstant(), zoneId);
        LocalDateTime localSunset = LocalDateTime.ofInstant(sunriseSunset[2].toInstant(), zoneId);

        //format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        replacer.replaceStrings("sunrise"+suffix, localSunrise.format(formatter));
        replacer.replaceStrings("noon"+suffix, localNoon.format(formatter));
        replacer.replaceStrings("sunset"+suffix, localSunset.format(formatter));
    }

    private void printDayLength(WordStringReplacer replacer, SunPosition position, GeoLocation location) {
        ZonedDateTime[] sunriseSunset = position.findSunriseSunsetAt(location);

        //compute local times
        LocalDateTime localSunrise = LocalDateTime.ofInstant(sunriseSunset[0].toInstant(), zoneId);
        LocalDateTime localSunset = LocalDateTime.ofInstant(sunriseSunset[2].toInstant(), zoneId);

        int hours = (int) ChronoUnit.HOURS.between(localSunrise, localSunset);
        int minutes = (int)ChronoUnit.MINUTES.between(localSunrise, localSunset);
        replacer.replaceStrings("dayLengthHours", Integer.toString(hours));
        replacer.replaceStrings("dayLengthMinutes", Integer.toString(minutes % 60));
    }

    private void printDayLengthGainLoss(WordStringReplacer replacer, SunPosition sunPosition, GeoLocation location, LocalDate otherDate, String[] tags) {
        ZonedDateTime[] sunriseSunset = sunPosition.findSunriseSunsetAt(location);

        //compute times
        ZonedDateTime moment = ZonedDateTime.of(otherDate, LocalTime.MIDNIGHT, ZoneOffset.UTC);
        SunPosition otherPosition = SunPosition.at(moment);
        ZonedDateTime[] otherSunEvents = otherPosition.findSunriseSunsetAt(location);

        //compute local times
        LocalDateTime localSunrise = LocalDateTime.ofInstant(sunriseSunset[0].toInstant(), zoneId);
        LocalDateTime localSunset = LocalDateTime.ofInstant(sunriseSunset[2].toInstant(), zoneId);
        int minutes = (int)ChronoUnit.MINUTES.between(localSunrise, localSunset);

        localSunrise = LocalDateTime.ofInstant(otherSunEvents[0].toInstant(), zoneId);
        localSunset = LocalDateTime.ofInstant(otherSunEvents[2].toInstant(), zoneId);
        int otherMinutes = (int)ChronoUnit.MINUTES.between(localSunrise, localSunset);

        int differente = Math.abs(minutes - otherMinutes);
        String gainOrLoss = Math.signum(minutes - otherMinutes) > 0 ? "gagné" : "perdu";
        replacer.replaceStrings(tags[0], gainOrLoss);
        replacer.replaceStrings(tags[1], Integer.toString(differente));
    }

}
