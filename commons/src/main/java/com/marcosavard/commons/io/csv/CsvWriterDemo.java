package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.io.FileSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CsvWriterDemo {

    public static void main(String[] args) {
        try {
            //create file
            String filename = "CsvWriterDemo.csv";
            File outputFile = new File(FileSystem.getUserDocumentFolder(), filename);
            Writer writer = new FileWriter(outputFile);
            CsvWriter csvWriter = new CsvWriter(writer);

            //write data
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Name", "Class", "Marks" });
            data.add(new String[] { "Aman", "10", "620" });
            data.add(new String[] { "Suraj", "10", "630" });
            csvWriter.writeAll(data);

            //close file
            csvWriter.close();
            System.out.println("File generated : " + outputFile.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}