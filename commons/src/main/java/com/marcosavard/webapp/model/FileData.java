package com.marcosavard.webapp.model;

import java.util.Map;

public class FileData {
    private String fileName;
    private String fileSize;

    private String nbImports;

    private String genericity;

    public FileData(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize + " B";
    }

    public String getFileName() {return fileName;}

    public String getFileSize() {return fileSize;}

    public String getNbImports() {return nbImports;}

    public String getGenericity() {return genericity;}

    public void setNbImports(int nbImports) {
        this.nbImports = Integer.toString(nbImports);
    }

    public void setGenericity(double percent) {
        this.genericity = percent + " %";
    }
}
