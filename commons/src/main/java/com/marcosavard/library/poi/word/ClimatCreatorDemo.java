package com.marcosavard.library.poi.word;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.ca.climat.NormalReader;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ClimatCreatorDemo {

    public static void main(String[] args) {
        //setting
        Locale display = Locale.FRENCH;
        LocalDate date = LocalDate.now(); //LocalDate.of(2025, Month.MAY, 27);
        NormalReader normalReader = new NormalReader();

        //set input, output file
        String inputFilePath = "climate.template.docx";
        String outputFilePath = "climat.docx";
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        try (InputStream input = new FileInputStream(inputFile)) {
            try (OutputStream output = new FileOutputStream(outputFile)) {
                WordStringReplacer replacer = new WordStringReplacer(input, output);
                fillCurrentDate(replacer, date, display);
                fillNormalsInTable(replacer, date, normalReader);
                fillNormalsOnMap(replacer, date, normalReader);
                fillExtreme(replacer, date, normalReader, display);
                replacer.close();
                Console.println("String replacement completed. Output file: {0}", outputFilePath);
            }

        } catch (IOException | XmlException e) {
            throw new RuntimeException(e);
        }
    }

    private static void fillCurrentDate(WordStringReplacer replacer, LocalDate date, Locale display) {
        //format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'du' eeee d MMMM yyyy", display);
        replacer.replaceStrings("currentDate", date.format(formatter));
    }

    private static void fillNormalsInTable(WordStringReplacer replacer, LocalDate date, NormalReader normalReader) throws IOException {
        replacer.replaceStrings("minmad", toMinText(normalReader, NormalReader.Station.ILES_MAD, date));
        replacer.replaceStrings("maxmad", toMaxText(normalReader, NormalReader.Station.ILES_MAD, date));
        replacer.replaceStrings("minsi", toMinText(normalReader, NormalReader.Station.SEPT_ILES, date));
        replacer.replaceStrings("maxsi", toMaxText(normalReader, NormalReader.Station.SEPT_ILES, date));
        replacer.replaceStrings("minbc", toMinText(normalReader, NormalReader.Station.BAIE_COMEAU, date));
        replacer.replaceStrings("maxbc", toMaxText(normalReader, NormalReader.Station.BAIE_COMEAU, date));

        replacer.replaceStrings("mingas", toMinText(normalReader, NormalReader.Station.GASPE, date));
        replacer.replaceStrings("maxgas", toMaxText(normalReader, NormalReader.Station.GASPE, date));
        replacer.replaceStrings("minrim", toMinText(normalReader, NormalReader.Station.RIMOUSKI, date));
        replacer.replaceStrings("maxrim", toMaxText(normalReader, NormalReader.Station.RIMOUSKI, date));
        replacer.replaceStrings("minsag", toMinText(normalReader, NormalReader.Station.BAGOTVILLE, date));
        replacer.replaceStrings("maxsag", toMaxText(normalReader, NormalReader.Station.BAGOTVILLE, date));
        replacer.replaceStrings("minrob", toMinText(normalReader, NormalReader.Station.ROBERVAL, date));
        replacer.replaceStrings("maxrob", toMaxText(normalReader, NormalReader.Station.ROBERVAL, date));

        replacer.replaceStrings("minqc", toMinText(normalReader, NormalReader.Station.STE_FOY, date));
        replacer.replaceStrings("maxqc", toMaxText(normalReader, NormalReader.Station.STE_FOY, date));
        replacer.replaceStrings("minstg", toMinText(normalReader, NormalReader.Station.ST_GEORGES, date));
        replacer.replaceStrings("maxstg", toMaxText(normalReader, NormalReader.Station.ST_GEORGES, date));
        replacer.replaceStrings("mintr", toMinText(normalReader, NormalReader.Station.TROIS_RIVIERES, date));
        replacer.replaceStrings("maxtr", toMaxText(normalReader, NormalReader.Station.TROIS_RIVIERES, date));
        replacer.replaceStrings("mindru", toMinText(normalReader, NormalReader.Station.DRUMMONDVILLE, date));
        replacer.replaceStrings("maxdru", toMaxText(normalReader, NormalReader.Station.DRUMMONDVILLE, date));
        replacer.replaceStrings("minshb", toMinText(normalReader, NormalReader.Station.SHERBROOKE, date));
        replacer.replaceStrings("maxshb", toMaxText(normalReader, NormalReader.Station.SHERBROOKE, date));

        replacer.replaceStrings("minmtl", toMinText(normalReader, NormalReader.Station.MTL, date));
        replacer.replaceStrings("maxmtl", toMaxText(normalReader, NormalReader.Station.MTL, date));
        replacer.replaceStrings("mingat", toMinText(normalReader, NormalReader.Station.OTTAWA, date));
        replacer.replaceStrings("maxgat", toMaxText(normalReader, NormalReader.Station.OTTAWA, date));
        replacer.replaceStrings("minvld", toMinText(normalReader, NormalReader.Station.VAL_DOR, date));
        replacer.replaceStrings("maxvld", toMaxText(normalReader, NormalReader.Station.VAL_DOR, date));
        replacer.replaceStrings("minrou", toMinText(normalReader, NormalReader.Station.ROUYN, date));
        replacer.replaceStrings("maxrou", toMaxText(normalReader, NormalReader.Station.ROUYN, date));
    }

    private static void fillNormalsOnMap(WordStringReplacer replacer, LocalDate date, NormalReader normalReader) throws XmlException, IOException {
        replacer.replaceStrings("maprim", toText(normalReader, NormalReader.Station.RIMOUSKI, date), "w:t");
        replacer.replaceStrings("mapsag", toText(normalReader, NormalReader.Station.BAGOTVILLE, date), "w:t");
        replacer.replaceStrings("mapqc", toText(normalReader, NormalReader.Station.STE_FOY, date), "w:t");
        replacer.replaceStrings("mapmtl", toText(normalReader, NormalReader.Station.MTL, date), "w:t");
        replacer.replaceStrings("mapgat", toText(normalReader, NormalReader.Station.OTTAWA, date), "w:t");
    }

    private static void fillExtreme(WordStringReplacer replacer, LocalDate date, NormalReader normalReader, Locale display) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMMM yyyy", display);
        NumberFormat nf = NumberFormat.getInstance(display);
        NormalReader.NormalEvent min = normalReader.findExtremeMinimums(date, 1).get(0);
        NormalReader.NormalEvent max = normalReader.findExtremeMaximums(date, 1).get(0);

        String pat = "Le {0}, il a fait {1} °C à {2}.";
        String historicMin = MessageFormat.format(pat, toDate(min, fmt), toString(min.getValue(), nf), min.getStationName());
        String historicMax = MessageFormat.format(pat, toDate(max, fmt), toString(max.getValue(), nf), max.getStationName());

        replacer.replaceStrings("historicMin", historicMin);
        replacer.replaceStrings("historicMax", historicMax);
    }

    private static String toDate(NormalReader.NormalEvent min, DateTimeFormatter formatter) {
        return min.getDate().format(formatter);
    }

    private static String toString(double value, NumberFormat nf) {
        return nf.format(value);
    }


    private static String toMinText(NormalReader normalReader, NormalReader.Station station, LocalDate date) throws IOException {
        return Double.toString(normalReader.getDailyMinimum(station, date));
    }

    private static String toMaxText(NormalReader normalReader, NormalReader.Station station, LocalDate date) throws IOException {
        return Double.toString(normalReader.getDailyMaximum(station, date));
    }

    private static String toText(NormalReader normalReader, NormalReader.Station station, LocalDate date) throws IOException {
        double min = normalReader.getDailyMinimum(station, date);
        double max = normalReader.getDailyMaximum(station, date);
        return Double.toString(min) + ", " + Double.toString(max);
    }

}
