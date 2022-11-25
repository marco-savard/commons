package com.marcosavard.webapp.model;

import java.util.Map;

public class FileData {
    private String fileName;
    private String fileSize;

    private String nbImports;

    private String genericity;

    public FileData(Map<String, String> fileProperties) {
        fileName = fileProperties.get("fileName");
        fileSize = fileProperties.get("fileSize");
        nbImports = fileProperties.get("nbImports");
        genericity = fileProperties.get("genericity");
    }

    public String getFileName() {return fileName;}

    public String getFileSize() {return fileSize;}

    public String getNbImports() {return nbImports;}

    public String getGenericity() {return genericity;}
}
