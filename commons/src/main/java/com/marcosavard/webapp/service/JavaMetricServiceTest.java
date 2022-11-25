package com.marcosavard.webapp.service;

import com.marcosavard.commons.App;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.webapp.model.FileData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class JavaMetricServiceTest {
    private static final String FOLDER_NAME = "C:/Users/Marco/IdeaProjects/commons/commons/src/main/java";
    private static final File sourceFolder = new File(FOLDER_NAME);

    public static void main(String[] args) {
        try {
            JavaMetricService service = new JavaMetricService();
            Package pack = JavaMetricService.class.getPackage();
            List<File> sourceFiles = FileSystem.getSourceFiles(sourceFolder, pack);
            File sourceFile = sourceFiles.get(1);
            Reader reader = new FileReader(sourceFile);
            String filename = sourceFile.getName();
            long fileSize = sourceFile.length();
            FileData fileData = new FileData(filename, fileSize);
            service.process(reader, fileData);
            String nbImport = fileData.getNbImports();
            String genericity = fileData.getGenericity();
            System.out.println(filename);
            System.out.println(nbImport);
            System.out.println(genericity);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
