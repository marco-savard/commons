package com.marcosavard.webapp.model;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PojoModel {
    private String fileName;
    private String fileSize;
    private StringWriter sw;
    private PrintWriter pw;


    public PojoModel(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize + " B";
        sw = new StringWriter();
        pw = new PrintWriter(sw);
    }

    public void printLine(String line) {
        pw.println(line);
    }

    public void close() {
        pw.close();
    }

    public String getFileName() {
        return fileName;
    }

    public String getModelAsString() {
        return sw.toString();
    }
}
