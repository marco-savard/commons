package com.marcosavard.commons.quiz.fr.religion;

import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SaintsLongReader {
    private final BufferedReader bufferedReader;

    public SaintsLongReader() {
        ResourceReader resourceReader = new ResourceReader(SaintsLongReader.class, "saints.long.txt", StandardCharsets.UTF_8);
        bufferedReader = new BufferedReader(resourceReader);
    }

    public List<SaintDuJour> readAll() throws IOException {
        List<SaintDuJour> saintsDuJour = new ArrayList<>();
        String line, previousLine = null;

        do {
            line = bufferedReader.readLine();

            if (line != null && ! line.isBlank() && ! line.startsWith("#")) {
                saintsDuJour.add(createSaint(line));
            }
        } while (line != null);

        return saintsDuJour;
    }

    private SaintDuJour createSaint(String line) {
        int idx1 = line.lastIndexOf("(");
        int idx2 = line.lastIndexOf(")");
        String name = line.substring(0, idx1-1).trim();
        String text = line.substring(idx1+1, idx2) + " 2000";
        SaintDuJour saintDuJour;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
            LocalDate date = LocalDate.parse(text, formatter);
            saintDuJour = new SaintDuJour(name, null, date.getMonth(), date.getDayOfMonth());
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }

        return saintDuJour;
    }
}
