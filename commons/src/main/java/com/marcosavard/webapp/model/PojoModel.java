package com.marcosavard.webapp.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class PojoModel {
    private String sourceFile, generatedFile;
    private String fileSize;
    private StringWriter sw;
    private PrintWriter pw;
    private Map<String, String> pojoByClassName = new HashMap<>();

    public PojoModel(String sourceFile, long fileSize) {
        this.sourceFile = sourceFile;
        int idx = sourceFile.lastIndexOf('.');
        generatedFile = sourceFile.substring(0, idx) + ".zip";

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

    public String getSourceFile() {
        return sourceFile;
    }

    public String getGeneratedFile() {
        return generatedFile;
    }

    public String getModelAsString() {
        return sw.toString();
    }

    public void storePojos(Map<String, String> pojoByClassName) {
        this.pojoByClassName.clear();
        this.pojoByClassName.putAll(pojoByClassName);
    }

    public Map<String, String> getPojos() {
        return pojoByClassName;
    }


}
