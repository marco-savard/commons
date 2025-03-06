package com.marcosavard.library.poi.word;

import com.marcosavard.commons.debug.Console;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WordMergerDemo {

    public static void main(String[] args) {
        //settings
        LocalDate date = LocalDate.now();

        //set output file
        String basename = "merged-" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
        String outputFilePath = basename + ".docx";
        File outputFile = new File(outputFilePath);
        File inputFile1 = new File("sunEvents.docx");
        File inputFile2 = new File("moonEvents.docx");
        File inputFile3 = new File("jeux-du-jour-2025-02-24.docx");

        try (OutputStream output = new FileOutputStream(outputFile)) {
            WordMergerSimple merger = new WordMergerSimple(output);
            merger.add( new FileInputStream(inputFile1));
            merger.add( new FileInputStream(inputFile2));
            merger.add( new FileInputStream(inputFile3));
            merger.merge();
            merger.close();

            Console.println("Success. Output file: {0}", outputFilePath);
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }

    }

}
