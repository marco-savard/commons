package com.marcosavard.commons.quiz.fr.religion;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SaintDuJourReader {
    private final BufferedReader bufferedReader;

    public SaintDuJourReader() {
        ResourceReader resourceReader = new ResourceReader(SaintDuJourReader.class, "saintDuJour.txt", StandardCharsets.UTF_8);
        bufferedReader = new BufferedReader(resourceReader);
    }

    public List<SaintDuJour> readAll() throws IOException {
        List<SaintDuJour> saintsDuJour = new ArrayList<>();
        String line, previousLine = null;
        String[] saintDate = null;

        do {
            line = bufferedReader.readLine();

            if (line != null && ! line.isBlank()) {
                int idx = line.indexOf("- Fêté");

                if (idx != -1) {
                    saintDate = parseSaintAndDate(line, idx);
                    previousLine = line;
                } else {
                    saintsDuJour.add(createSaint(saintDate, line));
                }
            }

        } while (line != null);

        return saintsDuJour;
    }

    private String[] parseSaintAndDate(String line, int idx) {
        String[] saintDate = new String[2];
        saintDate[0] = line.substring(0, idx-1).trim();
        int idx2 = line.indexOf(' ', idx+2);
        saintDate[1] = line.substring(idx2+1).replace("le ", "").trim();
        return saintDate;
    }

    private SaintDuJour createSaint(String[] saintDate, String line) {
        SaintDuJour saintDuJour;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
            String text = saintDate[1] + " 2000";
            LocalDate date = LocalDate.parse(text, formatter);
            saintDuJour = new SaintDuJour(saintDate[0], line, date.getMonth(), date.getDayOfMonth());
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }

        return saintDuJour;
    }
}
