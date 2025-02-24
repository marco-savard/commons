package com.marcosavard.library.poi.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class WordStringReplacerDemo {

    public static void main(String[] args) {
        //setting
        String inputFilePath = "sunEvents.template.docx";
        String outputFilePath = "sunEvents.docx";
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        try (InputStream input = new FileInputStream(inputFile)) {
            try (OutputStream output = new FileOutputStream(outputFile)) {
                WordStringReplacer replacer = new WordStringReplacer(input, output);
                replacer.replaceStrings("{00:01}", "07:24");
                replacer.replaceStrings("{00:02}", "16:50");
                replacer.replaceStrings("{00:03}", "7 heures 30 minutes");
                replacer.close();
                System.out.println("String replacement completed. Output file: " + outputFilePath);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
