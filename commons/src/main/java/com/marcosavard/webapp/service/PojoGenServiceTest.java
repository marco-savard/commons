package com.marcosavard.webapp.service;

import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class PojoGenServiceTest {

    public static void main(String[] args) {
        try {
            PojoGenService service = new PojoGenService();
            File outputFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
            File sourceFile = getSourceFile(PurchaseOrderModel.class);
            Reader reader = new FileReader(sourceFile);
            service.process(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static File getSourceFile(Class claz) {
        List<File> sourceFiles = getSourceFiles(claz);
        String filename = claz.getSimpleName() + ".java";
        File sourceFile =
                sourceFiles.stream().filter(f -> f.getName().equals(filename)).findFirst().orElse(null);
        return sourceFile;
    }

    private static List<File> getSourceFiles(Class claz) {
        File sourceFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
        Package pack = claz.getPackage();
        List<File> sourceFiles = FileSystem.getSourceFiles(sourceFolder, pack);
        sourceFiles.addAll(sourceFiles);
        return sourceFiles;
    }

}
