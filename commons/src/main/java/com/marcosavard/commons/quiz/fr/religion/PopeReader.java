package com.marcosavard.commons.quiz.fr.religion;

import com.marcosavard.commons.io.reader.CsvReader;
import com.marcosavard.commons.text.format.TextNumberFormat;
import com.marcosavard.commons.util.collection.NullSafeList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public class PopeReader {
    private CsvReader csvReader;

    public PopeReader() throws IOException {
        csvReader = CsvReader
                .of(PopeReader.class, "papes.txt", StandardCharsets.UTF_8)
                .withHeader(1, ';')
                .withSeparator(';');
        csvReader.readHeaders();
    }

    public List<Pope> readAll() throws IOException {
        List<Pope> allPopes = new NullSafeList<>();

        List<String[]> rows = csvReader.readAll();

        for (String[] row : rows) {
            allPopes.add(readPope(row));
        }

        return allPopes;
    }

    private Pope readPope(String[] row) {
        Pope pope = null;
        row[0] = row[0].replace('\t', ' ');
        int idx = row[0].indexOf(' ');
        String name = row[0].substring(idx).trim();

        row[1] =  row[1].replace("...", " ");
        row[1] =  row[1].replace("\t", " ");
        row[1] = row[1].trim().replaceAll(" +", " ");
        String[] datesTxt = row[1].split(" ");
        LocalDate[] dates = new LocalDate[2];
        dates[0] = readDate(datesTxt[0]);
        dates[1] = datesTxt.length == 2 ? readDate(datesTxt[1]) : null;

        String orignalName = (row.length <= 3) ? null : row[2].trim();
        String birthPlace = (row.length <= 2) ? null : (row.length == 3) ? row[2].trim() : row[3].trim();

        if (dates[0] != null) {
            pope = new Pope(name, dates[0], dates[1], orignalName, birthPlace);
        }

        return pope;
    }

    private LocalDate readDate(String dateTxt) {
        String[] parts = dateTxt.split("\\.");
        LocalDate date = null;
        NumberFormat numberFormat = TextNumberFormat.getRomanNumeralInstance();

        if (parts.length == 3) {
            try {
                int year = Integer.parseInt(parts[2]);
                long month = (long)numberFormat.parse(parts[1]);
                String[] days = parts[0].split(",");
                int day = Integer.parseInt(days[0]);
                date = LocalDate.of(year, (int)month, day);
            } catch (NumberFormatException | ParseException ex) {
                //ignore
            }
        }

        return date;
    }
}
