package com.marcosavard.library.poi.word;

import com.marcosavard.commons.astro.moon.MoonPosition;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.library.poi.word.template.MoonEventTemplateReader;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        MoonPosition.Phase phase = position.getPhase();

        replacer.replaceStrings("symb", Character.toString(phase.getCodePoint()));
        replacer.replaceStrings("moonPhase0", phase.getDisplayName(display));
        replacer.replaceStrings("moonAge", Integer.toString(moonAge));
        replacer.replaceStrings("moonIllumination", Integer.toString(illumination));
    }

    private void printNextMoonPhases(WordStringReplacer replacer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Le' eeee d MMMM", display);

        //compute info
        ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneOffset.UTC);
        MoonPosition position = MoonPosition.at(moment);
        int moonAge = (int)position.getMoonAge() * 360;

        for (int i=1; i<=4; i++) {
            MoonPosition.Phase nextPhase = MoonPosition.Phase.findPhaseAt(moonAge);
            ZonedDateTime phaseDate = MoonPosition.findNextMoonAge(date, moonAge);

            replacer.replaceStrings("phase"+i+"Date", phaseDate.format(formatter));
            replacer.replaceStrings("s"+i, Character.toString(nextPhase.getCodePoint()));
            replacer.replaceStrings("moonPhase"+i, nextPhase.getDisplayName(display));
            moonAge = (moonAge + 45) % 360;
        }
    }

}
