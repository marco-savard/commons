package com.marcosavard.library.poi.word;

import com.marcosavard.commons.astro.moon.MoonPosition;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.library.poi.word.template.MoonEventTemplateReader;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MoonEventCreator {
    private final MoonEventTemplateReader templateReader = new MoonEventTemplateReader();
    private final LocalDate date;
    private final Locale display;

    public MoonEventCreator(LocalDate date, Locale display) {
        this.date = date;
        this.display = display;
    }

    public void create(File outputFile) {
        try (InputStream input = templateReader.getInput()) {
            try (OutputStream output = new FileOutputStream(outputFile)) {
                WordStringReplacer replacer = new WordStringReplacer(input, output);
                printCurrentDate(replacer);
                printMoonInfo(replacer);
                printNextMoonPhases(replacer);
                replacer.close();
                Console.println("String replacement completed. Output file: {0}", outputFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printCurrentDate(WordStringReplacer replacer) {
        //format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'du' eeee d MMMM yyyy", display);
        replacer.replaceStrings("currentDate", date.format(formatter));
    }

    private void printMoonInfo(WordStringReplacer replacer) {
        //compute info
        ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneOffset.UTC);
        MoonPosition position = MoonPosition.at(moment);
        int moonAge = (int)position.getMoonAgeDays();
        int illumination = (int)( 100 * position.getMoonIllumination());
        long distance = (long)position.getMoonDistance();
        double diameter = position.getMoonAngularDiameter();
        double percent = 100.0 * distance / MoonPosition.MOON_APOGEE;
        MoonPosition.Phase phase = position.getPhase();

        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(display);
        String distanceStr = df.format(distance);
        String percentStr = Double.toString(Math.round(percent*10) / 10.0);
        String diameterStr = Double.toString(Math.round(diameter*1000) / 1000.0);

        replacer.replaceStrings("symb", Character.toString(phase.getCodePoint()));
        replacer.replaceStrings("moonPhase0", phase.getDisplayName(display));
        replacer.replaceStrings("moonAge", Integer.toString(moonAge));
        replacer.replaceStrings("moonIllumination", Integer.toString(illumination));
        replacer.replaceStrings("moonDistance", distanceStr);
        replacer.replaceStrings("moonDistPct", percentStr);
        replacer.replaceStrings("moonDiameter", diameterStr);
    }

    private void printNextMoonPhases(WordStringReplacer replacer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Le' eeee d MMMM", display);

        //compute moon phases
        LocalDate newMoon = MoonPosition.findNextNewMoon(date).toLocalDate();
        LocalDate firstQuarter = MoonPosition.findNextFirstQuarter(date).toLocalDate();
        LocalDate fullMoon = MoonPosition.findNextFullMoon(date).toLocalDate();
        LocalDate lastQuarter = MoonPosition.findNextLastQuarter(date).toLocalDate();

        List<MoonEvent> events = new ArrayList<>();
        events.add(new MoonEvent(newMoon, MoonPosition.Phase.NEW));
        events.add(new MoonEvent(firstQuarter, MoonPosition.Phase.FIRST_QUARTER));
        events.add(new MoonEvent(fullMoon, MoonPosition.Phase.FULL));
        events.add(new MoonEvent(lastQuarter, MoonPosition.Phase.THIRD_QUARTER));
        events = events.stream().sorted(Comparator.comparing(e -> e.date)).toList();
        int i=1;

        for (MoonEvent event : events) {
            replacer.replaceStrings("phase"+i+"Date", event.date.format(formatter));
            replacer.replaceStrings("s"+i, Character.toString(event.phase.getCodePoint()));
            replacer.replaceStrings("moonPhase"+i, event.phase.getDisplayName(display));
            i++;
        }
    }

    private static class MoonEvent {
        private LocalDate date;
        private MoonPosition.Phase phase;

        public MoonEvent(LocalDate date, MoonPosition.Phase phase) {
            this.date = date;
            this.phase = phase;
        }
    }

}
